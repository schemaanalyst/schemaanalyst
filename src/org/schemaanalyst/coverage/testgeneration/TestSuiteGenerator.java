package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.search.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.HashMap;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuiteGenerator {

    private Schema schema;
    private Criterion criterion;
    private DBMS dbms;
    private TestCaseGenerator testCaseGenerator;

    private HashMap<Table, Predicate> initialTablePredicates;
    private HashMap<Table, Data> initialTableData;

    private TestSuite testSuite;

    public TestSuiteGenerator(Schema schema,
                              Criterion criterion,
                              DBMS dbms,
                              TestCaseGenerator testCaseGenerator) {
        this.schema = schema;
        this.criterion = criterion;
        this.dbms = dbms;
        this.testCaseGenerator = testCaseGenerator;

        this.initialTablePredicates = new HashMap<>();
        this.initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        testSuite = new TestSuite();
        generateInitialTestCases();
        generateRemainingTestCases();
        return testSuite;
    }

    private void generateInitialTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            Predicate predicate = criterion.generateInitialTablePredicate(schema, table);
            initialTablePredicates.put(table, predicate);

            TestCase testCase = generateTestCase(table, predicate);
            initialTableData.put(table, testCase.getData());
            testSuite.addTestCase(testCase);
        }
    }

    private void generateRemainingTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            List<Predicate> requirements = criterion.generateRequirements(schema, table);
            for (Predicate predicate : requirements) {
                TestCase testCase = generateTestCase(table, predicate);
                testSuite.addTestCase(testCase);
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
                predicate.addClauses(initialTablePredicates.get(linkedTable));
            }
        }

        // add the row
        data.addRow(table, valueFactory);

        // generate the test case
        return testCaseGenerator.generate(data, state, predicate);
    }
}
