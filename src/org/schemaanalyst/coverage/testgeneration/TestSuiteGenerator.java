package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.predicate.ConstraintPredicateGenerator;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

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
    private Criterion criterion;
    private ValueFactory valueFactory;
    private TestCaseGenerationAlgorithm testCaseGenerator;
    private HashMap<Table, Data> initialTableData;
    private boolean reuseTestCases;

    private TestSuite testSuite;
    private List<TestCase> failedTestCases;

    public TestSuiteGenerator(Schema schema,
                              Criterion criterion,
                              ValueFactory valueFactory,
                              TestCaseGenerationAlgorithm testCaseGenerator,
                              boolean reuseTestCases) {
        this.schema = schema;
        this.criterion = criterion;
        this.valueFactory = valueFactory;
        this.testCaseGenerator = testCaseGenerator;
        this.reuseTestCases = reuseTestCases;

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
            if (testCase.satisfiesOriginalPredicate()) {
                initialTableData.put(table, testCase.getData());
            } else {
                // TODO
                // not sure what to do here, throwing an exception for now...
                System.out.println(testCase);
                System.out.println(testCase.getInfo("info"));
                throw new RuntimeException("Cannot generate initial table data for " + table);
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

                boolean haveTestCase = false;
                if (reuseTestCases) {

                    LOGGER.fine("Checking if can re-use a test case");
                    TestCase testCase = testCaseGenerator.testCaseThatSatisfiesPredicate(predicate, testSuite);
                    if (testCase != null) {
                        LOGGER.fine("Reused test case " + testCase);
                        testCase.addPredicate(predicate);
                        haveTestCase = true;
                    }
                }

                if (!haveTestCase) {
                    TestCase testCase = generateTestCase(table, predicate);
                    if (testCase.satisfiesOriginalPredicate()) {
                        LOGGER.fine("Successfully generated test case");
                        testSuite.addTestCase(testCase);
                    } else {
                        LOGGER.fine("Failed to generate test case");
                        failedTestCases.add(testCase);
                    }
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
        TestCase testCase = testCaseGenerator.generateTestCase(data, state, predicate);
        LOGGER.fine("Generated test case: \n" + testCase);

        return testCase;
    }
}
