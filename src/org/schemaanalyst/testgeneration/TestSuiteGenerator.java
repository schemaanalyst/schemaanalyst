package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.logic.predicate.ConstraintPredicateGenerator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.Clause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;

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
    private CoverageCriterion criterion;
    private ValueFactory valueFactory;
    private DataGenerator dataGenerator;
    private HashMap<Table, Data> initialTableData;

    private TestSuite testSuite;
    private List<TestCase> failedTestCases;

    public TestSuiteGenerator(Schema schema,
                              CoverageCriterion criterion,
                              ValueFactory valueFactory,
                              DataGenerator testCaseGenerator) {
        this.schema = schema;
        this.criterion = criterion;
        this.valueFactory = valueFactory;
        this.dataGenerator = testCaseGenerator;

        initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        LOGGER.fine("Generating test suite for " + schema);
        testSuite = new TestSuite();
        failedTestCases = new ArrayList<>();

        generateInitialTableData();
        generateTestCases();
        return testSuite;
    }

    public List<TestCase> getFailedTestCases() {
        return new ArrayList<>(failedTestCases);
    }

    private void generateInitialTableData() {

        for (Table table : schema.getTablesInOrder()) {
            Predicate predicate = generateInitialTablePredicate(schema, table);

            LOGGER.fine("Generating initial table data for " + table);
            LOGGER.fine("Predicate is " + predicate);
            TestCase testCase = generateTestCase(table, predicate);
            if (testCase == null) {
                LOGGER.fine("Failed to generate");
            } else if (testCase.satisfiesOriginalPredicate()) {
                initialTableData.put(table, testCase.getData());
            }
        }
    }

    private Predicate generateInitialTablePredicate(Schema schema, Table table) {
        Predicate predicate = new ConstraintPredicateGenerator(schema, table).generate(
                "Test valid data for table " + table);

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            predicate.setColumnNullStatus(table, column, false);
        }

        return predicate;
    }

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
        TestCase testCase = new TestCase(data, state, predicate, report.getSuccess());

        LOGGER.fine("Generated test case: \n" + testCase);

        return testCase;
    }
}
