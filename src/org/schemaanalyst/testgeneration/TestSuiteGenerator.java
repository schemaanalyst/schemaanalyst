package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<TestCase> failedTestCases;

    public TestSuiteGenerator(Schema schema,
                              TestRequirements testRequirements,
                              ValueFactory valueFactory,
                              DataGenerator testCaseGenerator) {
        this.schema = schema;
        this.testRequirements = testRequirements;
        this.valueFactory = valueFactory;
        this.dataGenerator = testCaseGenerator;

        initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        LOGGER.fine("Generating test suite for " + schema);
        testSuite = new TestSuite();
        failedTestCases = new ArrayList<>();

        generateInitialTableData();
        // generateTestCases();
        return testSuite;
    }

    public List<TestCase> getFailedTestCases() {
        return new ArrayList<>(failedTestCases);
    }

    private void generateInitialTableData() {
        for (Table table : schema.getTablesInOrder()) {
            // Do we need to add not nulls to each column?
            Predicate predicate = generateInitialTablePredicate(table);

            LOGGER.fine("Generating initial table data for " + table);
            LOGGER.fine("Predicate is " + predicate);

            Data state = createStateForPredicate(predicate);
            if (state == null) {
                // we failed to create the state object, so essentially
                // generating a row of data for this table already failed
                continue;
            }

            Data data = createDataForPredicate(predicate);

            DataGenerationReport report = dataGenerator.generateData(data, state, predicate);
            if (report.isSuccess()) {
                initialTableData.put(table, data);
            }
        }
    }

    private Predicate generateInitialTablePredicate(Table table) {
        ComposedPredicate predicate = PredicateGenerator.generateAcceptancePredicate(schema, table);
        // TODO -- could just make this for UNIQUES and FKs so as in line with paper
        PredicateGenerator.addNullPredicates(predicate, table, table.getColumns(), true);
        return predicate;
    }

    private Data createDataForPredicate(Predicate predicate) {
        Table table = predicate.getTable();
        Data data = new Data();
        data.addRow(table, valueFactory);
        return data;
    }

    private Data createStateForPredicate(Predicate predicate) {
        Table table = predicate.getTable();
        Data state = new Data();

        // add rows for tables linked via foreign keys to the state
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {

            // a row should always have been previously-generated
            // for a linked table
            if (!linkedTable.equals(table)) {
                Data initialData = initialTableData.get(linkedTable);

                // cannot generate data in this instance
                if (initialData == null) {
                    return null;
                }
                state.appendData(initialData);
            }
        }
        return state;
    }

    // this code needs to go in data and state creation
        /*
        // check if a 'comparison' row is required for this table
        // boolean requiresComparisonRow = false;
        for (Clause clause : predicate.getClauses()) {
            if (clause.requiresComparisonRow()) {
                requiresComparisonRow = true;
            }
        }
        if (requiresComparisonRow) {
            // add the comparison row to the state
            Data initialData = initialTableData.get(table);

            // cannot generate a test case in this instance
            if (initialData == null) {
                return null;
            }

            state.appendData(initialData);

            // add foreign key rows to the data
            // TODO: this only needs to take place under special circumstances as written
            // in the paper
            for (Table linkedTable : linkedTables) {
                if (!linkedTable.equals(table)) {
                    data.addRow(linkedTable, valueFactory);
                    predicate.addClauses(generateInitialTablePredicate(schema, linkedTable));
                }
            }
        }

    /*
    private void generateTestCases() {
        for (Table table : schema.getTablesInReverseOrder()) {
            Requirements requirements = criterion.generateRequirements(schema, table);
            for (Predicate predicate : requirements.getPredicates()) {

                LOGGER.fine("Generating data for " + predicate);

                TestCase testCase = generateTestCase(table, predicate);
                if (testCase != null) {
                    if (testCase.satisfiesOriginalPredicate()) {
                        LOGGER.fine("Successfully generated test case");
                        testSuite.addTestCase(testCase);
                    } else {
                        LOGGER.fine("Failed to generate test case");
                        failedTestCases.add(testCase);
                    }
                } else {
                    LOGGER.fine("Could not generate test case, as a dependent row was not previously generated");
                }
            }
        }
    }

    private TestCase generateTestCase(Table table, Predicate predicate) {
        Data data = new Data();
        Data state = new Data();

        // add rows for tables linked via foreign keys to the state
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {
            // a row should always have been previously-generated
            // for a linked table
            if (!linkedTable.equals(table)) {
                Data initialData = initialTableData.get(linkedTable);
                // cannot generate a test case in this instance
                if (initialData == null) {
                    return null;
                }
                state.appendData(initialData);
            }
        }

        // check if a 'comparison' row is required for this table
        boolean requiresComparisonRow = false;
        for (Clause clause : predicate.getClauses()) {
            if (clause.requiresComparisonRow()) {
                requiresComparisonRow = true;
            }
        }
        if (requiresComparisonRow) {
            // add the comparison row to the state
            Data initialData = initialTableData.get(table);

            // cannot generate a test case in this instance
            if (initialData == null) {
                return null;
            }

            state.appendData(initialData);

            // add foreign key rows to the data
            for (Table linkedTable : linkedTables) {
                if (!linkedTable.equals(table)) {
                    data.addRow(linkedTable, valueFactory);
                    predicate.addClauses(generateInitialTablePredicate(schema, linkedTable));
                }
            }
        }

        // add the row
        data.addRow(table, valueFactory);

        LOGGER.fine("Generating test case");
        LOGGER.fine("State is " + state);
        LOGGER.fine("Data is " + data);

        // generate the test case
        DataGenerationReport report = dataGenerator.generateData(data, state, predicate);
        TestCase testCase = new TestCase(data, state, predicate, report);

        LOGGER.fine("Generated test case: \n" + testCase);

        return testCase;
    }
    */
}
