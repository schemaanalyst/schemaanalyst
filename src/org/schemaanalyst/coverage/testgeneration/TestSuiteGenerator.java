package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.coverage.search.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
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
    private Search<Row> search;
    private HashMap<Table, Row> initialRows;

    private TestSuite testSuite;

    public TestSuiteGenerator(Schema schema,
                              Criterion criterion,
                              DBMS dbms,
                              Search<Row> search) {
        this.schema = schema;
        this.criterion = criterion;
        this.dbms = dbms;
        this.search = search;
        this.initialRows = new HashMap<>();
    }

    public TestSuite generate() {
        testSuite = new TestSuite();
        generateValidTestCases();
        generateOtherTestCases();
        return testSuite;
    }

    private void generateValidTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            Requirements requirements = criterion.generateValidDataRequirements(schema, table);
            for (Predicate predicate : requirements.getRequirements()) {
                TestCase testCase = generateTestCase(table, predicate);
                initialRows.put(table, testCase.getRow());
                testSuite.addTestCase(testCase);
            }
        }
    }

    private void generateOtherTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            Requirements requirements = criterion.generateOtherRequirements(schema, table);
            for (Predicate predicate : requirements.getRequirements()) {
                TestCase testCase = generateTestCase(table, predicate);
                testSuite.addTestCase(testCase);
            }
        }
    }

    private TestCase generateTestCase(Table table, Predicate predicate) {
        Row row = new Row(table, dbms.getValueFactory());
        Data state = constructState(table, predicate);

        search.setObjectiveFunction(new PredicateObjectiveFunction(predicate, state));
        search.initialize();
        search.search(row);

        return new TestCase(row, state, predicate);
    }

    private Data constructState(Table table, Predicate predicate) {
        Data state = new Data();

        // add rows for tables linked via foreign keys
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {
            // a row should always have been previously-generated for a
            // linked table
            Row row = initialRows.get(linkedTable);
            state.addRow(linkedTable, row);
        }

        // add 'comparison' row for this table, if it is needed
        boolean requiresComparisonRow = false;
        for (Clause clause : predicate.getClauses()) {
            if (clause.requiresComparisonRow()) {
                requiresComparisonRow = true;
            }
        }
        if (requiresComparisonRow) {
            Row row = initialRows.get(table);
            state.addRow(table, row);
        }

        return state;
    }
}
