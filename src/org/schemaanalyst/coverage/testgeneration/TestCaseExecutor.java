package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import parsedcasestudy.Flights;

import java.util.List;

/**
 * Created by phil on 03/02/2014.
 */
public class TestCaseExecutor {

    private Schema schema;
    private List<Table> tables;
    private DatabaseInteractor databaseInteractor;
    private SQLWriter sqlWriter;

    public TestCaseExecutor(Schema schema,
                            DBMS dbms,
                            DatabaseConfiguration databaseConfiguration,
                            LocationsConfiguration locationConfiguration) {

        this.schema = schema;

        tables = schema.getTablesInOrder();
        databaseInteractor = dbms.getDatabaseInteractor(schema.getName(), databaseConfiguration, locationConfiguration);
        sqlWriter = dbms.getSQLWriter();

        List<String> createStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createStatements) {
            Integer result = databaseInteractor.executeUpdate(statement);
            System.out.println(result);
            if (result != 0) {
                // throw exception
                System.out.println("could not execute " + statement);
            }
        }
    }

    public void execute(TestCase testCase) {
        System.out.println("HERE");
        Data state = testCase.getState();
        List<Table> stateTables = state.getTables();

        for (Table table : tables) {
            if (stateTables.contains(table)) {
                List<Row> rows = state.getRows(table);
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    Integer result = databaseInteractor.executeUpdate(statement);
                    System.out.println(statement);
                    System.out.println("Res: " + result);
                    if (result != 0) {
                        System.out.println("could not execute " + statement);
                    }
                }
            }
        }

        Data data = testCase.getData();
        List<Table> dataTables = data.getTables();
        System.out.println(dataTables);
        for (Table table : tables) {
            if (dataTables.contains(table)) {
                List<Row> rows = data.getRows(table);
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    Integer result = databaseInteractor.executeUpdate(statement);
                    System.out.println(statement);
                    System.out.println("Res: " + result);
                    if (result != 0) {
                        System.out.println("could not execute " + statement);
                    }
                }
            }
        }
    }

}
