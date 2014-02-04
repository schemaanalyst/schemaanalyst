package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.ConstraintPredicateGenerator;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.HashMap;
import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNotNull;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuiteGenerator {

    private Schema schema;
    private Criterion criterion;
    private DBMS dbms;
    private TestCaseGenerationAlgorithm testCaseGenerator;
    private HashMap<Table, Data> initialTableData;
    private boolean generateIndividualTestCases;

    private TestSuite testSuite;

    public TestSuiteGenerator(Schema schema,
                              Criterion criterion,
                              DBMS dbms,
                              TestCaseGenerationAlgorithm testCaseGenerator,
                              boolean generateIndividualTestCases) {
        this.schema = schema;
        this.criterion = criterion;
        this.dbms = dbms;
        this.testCaseGenerator = testCaseGenerator;
        this.generateIndividualTestCases = generateIndividualTestCases;

        initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        testSuite = new TestSuite();
        generateInitialTableData();
        generateTestCases();
        return testSuite;
    }

    private Predicate generateInitialTablePredicate(Schema schema, Table table) {

        Predicate predicate = new ConstraintPredicateGenerator(schema, table).generate(
                "Test valid data for table " + table);

        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            predicate.addClause(isNotNull(table, column));
        }

        return predicate;
    }

    private void generateInitialTableData() {
        for (Table table : schema.getTablesInOrder()) {
            Predicate predicate = generateInitialTablePredicate(schema, table);
            TestCase testCase = generateTestCase(table, predicate);
            initialTableData.put(table, testCase.getData());
        }
    }

    private void generateTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            List<Predicate> requirements = criterion.generateRequirements(schema, table);
            for (Predicate predicate : requirements) {

                boolean haveTestCase = false;
                if (!generateIndividualTestCases) {
                    TestCase testCase = testCaseGenerator.checkIfTestCaseExists(predicate, testSuite);
                    if (testCase != null) {
                        testCase.addPredicate(predicate);
                        haveTestCase = true;
                    }
                }

                if (!haveTestCase) {
                    TestCase testCase = generateTestCase(table, predicate);
                    testSuite.addTestCase(testCase);
                }
            }
        }
    }

    private TestCase generateTestCase(Table table, Predicate predicate) {
        Data data = new Data();
        Data state = new Data();
        ValueFactory valueFactory = dbms.getValueFactory();

        // add rows for tables linked via foreign keys to the state
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {
            // a row should always have been previously-generated
            // for a linked table
            Data initialData = initialTableData.get(linkedTable);
            state.appendData(initialData);
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
                data.addRow(linkedTable, valueFactory);
                predicate.addClauses(generateInitialTablePredicate(schema, linkedTable));
            }
        }

        // add the row
        data.addRow(table, valueFactory);

        // generate the test case
        return testCaseGenerator.generateTestCase(data, state, predicate);
    }
}
