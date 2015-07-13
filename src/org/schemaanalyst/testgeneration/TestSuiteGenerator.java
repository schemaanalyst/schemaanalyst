package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuiteGenerator {

    private final static Logger LOGGER = Logger.getLogger(TestSuiteGenerator.class.getName());

    private Schema schema;
    private TestRequirements testRequirements;
    private ValueFactory valueFactory;
    private DataGenerator dataGenerator;
    private HashMap<Table, Data> initialTableData;
    private TestSuite testSuite;
    private TestSuiteGenerationReport testSuiteGenerationReport;

    public TestSuiteGenerator(Schema schema,
                              TestRequirements testRequirements,
                              ValueFactory valueFactory,
                              DataGenerator dataGenerator) {
        this.schema = schema;
        this.testRequirements = testRequirements;
        this.valueFactory = valueFactory;
        this.dataGenerator = dataGenerator;

        initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        LOGGER.fine("Generating test suite for " + schema);

        testSuite = new TestSuite();
        testSuiteGenerationReport = new TestSuiteGenerationReport();
        generateInitialTableData();
        generateTestCases();
        return testSuite;
    }

    public TestSuiteGenerationReport getTestSuiteGenerationReport() {
        return testSuiteGenerationReport;
    }

    protected void generateInitialTableData() {
        for (Table table : schema.getTablesInOrder()) {

            ComposedPredicate acceptancePredicate = PredicateGenerator.generatePredicate(schema.getConstraints(table));

            // add not null predicates
            List<Column> notNullColumns = table.getColumns();

            /*
            // NOTE: selecting individual columns like this will cause AUCC test requirements to fail.

            List<Column> notNullColumns = new ArrayList<>();
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            if (primaryKeyConstraint != null) {
                // TODO: some check as to whether it's been added already ...
                notNullColumns.addAll(primaryKeyConstraint.getColumns());
            }
            for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
                notNullColumns.addAll(uniqueConstraint.getColumns());
            }
            for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
                notNullColumns.addAll(foreignKeyConstraint.getColumns());
            }
            */
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(acceptancePredicate);
            PredicateGenerator.addNullPredicates(predicate, table, notNullColumns, false);

            LOGGER.fine("\nGENERATING INITIAL TABLE DATA FOR " + table);
            LOGGER.fine("--- Predicate is " + predicate);

            // add referenced tables to the state
            Data state = new Data();
            boolean haveLinkedData = addInitialTableDataToState(state, table);
            if (haveLinkedData) {
                // generate the row
                Data data = new Data();
                data.addRow(table, valueFactory);
                DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
                if (dataGenerationReport.isSuccess()) {
                    LOGGER.fine("--- Success, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
                    LOGGER.fine("--- Data is: \n" + data);
                    initialTableData.put(table, data);
                } else {
                    LOGGER.fine("--- Failed");
                }

                testSuiteGenerationReport.addInitialTableDataResult(
                        table, new DataGenerationResult(data, state, dataGenerationReport));
            } else {
                // there was no linked data generated to add to the state, so generated of this row failed by default
                testSuiteGenerationReport.addInitialTableDataResult(
                        table, null);
            }
        }
    }

    protected void generateTestCases() {
        for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {

            Predicate predicate = testRequirement.getPredicate();
            Table table = getTestRequirementTable(testRequirement);

            LOGGER.fine("\nGENERATING TEST CASE");
            for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
                LOGGER.fine(testRequirementDescriptor.toString());
            }
            LOGGER.fine("--- Predicate is " + predicate);

            Data state = new Data();
            Data data = new Data();
            predicate = addAdditionalRows(state, data, predicate, table, testRequirement.getRequiresComparisonRow());

            if (predicate != null) {
                data.addRow(table, valueFactory);

                LOGGER.fine("--- Pre-reduced predicate is " + predicate);
                predicate = predicate.reduce();
                LOGGER.fine("--- Reduced predicate is " + predicate);

                DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
                if (dataGenerationReport.isSuccess()) {
                    TestCase testCase = new TestCase(testRequirement, data, state);
                    testSuite.addTestCase(testCase);

                    LOGGER.fine("--- SUCCESS, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
                    LOGGER.fine("--- Data is \n" + data);
                } else {
                    LOGGER.fine("--- FAILED");
                }

                testSuiteGenerationReport.addTestRequirementResult(
                        testRequirement, new DataGenerationResult(data, state, dataGenerationReport));
            } else  {
                testSuiteGenerationReport.addTestRequirementResult(
                        testRequirement, null);
            }
        }
    }

    protected Table getTestRequirementTable(TestRequirement testRequirement) {
        Set<Table> tables = testRequirement.getTables();
        if (tables.size() != 1) {
            throw new TestGenerationException("Test requirement  should have predicates involving exactly one table, has " + tables.size() + ". Test requirement is: \n" + testRequirement);
        }
        return tables.iterator().next();
    }

    protected Predicate addAdditionalRows(Data state, Data data, Predicate predicate, Table table, boolean requiresComparisonRow) {
        LOGGER.fine("--- adding additional rows");

        boolean haveLinkedData = addInitialTableDataToState(state, table);
        if (!haveLinkedData) {
            return null;
        }

        if (requiresComparisonRow) { // if (getRequiresComparisonRow(predicate)) {
            Data comparisonRow = initialTableData.get(table);
            if (comparisonRow == null) {
                LOGGER.fine("--- could not add comparison row, data generation FAILED");
                return null;
            }

            LOGGER.fine("--- added comparison row");
            state.appendData(comparisonRow);

            predicate = addLinkedTableRowsToData(data, predicate, table);
        }
        return predicate;
    }

    protected boolean addInitialTableDataToState(Data state, Table table) {
        LOGGER.fine("--- adding initial data to state for linked tables");

        // add rows for tables linked via foreign keys to the state
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {

            // a row should always have been previously-generated
            // for a linked table
            if (!linkedTable.equals(table)) {
                Data initialData = initialTableData.get(linkedTable);

                // cannot generate data in this instance
                if (initialData == null) {
                    return false;
                }
                state.appendData(initialData);
            }
        }
        return true;
    }

    protected Predicate addLinkedTableRowsToData(Data data, Predicate predicate, Table table) {

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {

            Table refTable = foreignKeyConstraint.getReferenceTable();
            if (!refTable.equals(table)) {

                boolean refColsUnique = areRefColsUnique(predicate, foreignKeyConstraint);

                if (refColsUnique) {
                    LOGGER.fine("--- foreign key columns are unique in " + table);

                    // append the predicate with the acceptance predicate of the original
                    AndPredicate newPredicate = new AndPredicate();
                    newPredicate.addPredicate(predicate);
                    newPredicate.addPredicate(PredicateGenerator.generatePredicate(schema.getConstraints(refTable)));
                    predicate = newPredicate;

                    LOGGER.fine("--- new predicate is " + predicate);

                    LOGGER.fine("--- adding foreign key row for " + refTable);
                    predicate = addLinkedTableRowsToData(data, predicate, refTable);
                    data.addRow(refTable, valueFactory);
                }
            }
        }

        return predicate;
    }

    protected boolean areRefColsUnique(Predicate predicate, ForeignKeyConstraint foreignKeyConstraint) {
        //Table table = foreignKeyConstraint.getTable();
        final List<Column> uniqueColumns = new ArrayList<>();

        /*
        if (schema.hasPrimaryKeyConstraint(table)) {
            uniqueColumns.addAll(schema.getPrimaryKeyConstraint(table).getColumns());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            uniqueColumns.addAll(uniqueConstraint.getColumns());
        }
        */

        predicate.accept(new PredicateAdaptor() {
            @Override
            public void visit(MatchPredicate predicate) {
                if (predicate.tableIsRefTable()) {
                    uniqueColumns.addAll(predicate.getColumns());
                }
            }
        });

        for (Column column : foreignKeyConstraint.getColumns()) {
            if (!uniqueColumns.contains(column)) {
                return false;
            }
        }

        return true;
    }
}
