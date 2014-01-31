package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
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
public class DataGenerator {

    private Schema schema;
    private Criterion criterion;
    private DBMS dbms;
    private Search<Row> search;
    private HashMap<Table, Row> initialRows;

    private TestSuite testSuite;

    public DataGenerator(Schema schema,
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

                System.out.println(testCase);
            }
        }
    }

    private void generateOtherTestCases() {
        for (Table table : schema.getTablesInOrder()) {
            Requirements reqs = criterion.generateOtherRequirements(schema, table);
            //System.out.println(reqs);
            //System.out.println(reqs.size() + 2);
        }
    }

    private TestCase generateTestCase(Table table, Predicate predicate) {
        Row row = new Row(table, dbms.getValueFactory());
        Data state = constructState(table);

        search.setObjectiveFunction(new PredicateObjectiveFunction(predicate, state));
        search.initialize();
        search.search(row);

        return new TestCase(row, predicate);
    }

    private Data constructState(Table table) {
        Data state = new Data();
        List<Table> linkedTables = schema.getConnectedTables(table);

        for (Table linkedTable : linkedTables) {
            Row row = initialRows.get(linkedTable);
            state.addRow(linkedTable, row);
        }

        return state;
    }
}
