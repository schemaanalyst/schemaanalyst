package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.InsertStatementException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

public class FullSchemataDeletingTestCaseExecutor extends DeletingTestCaseExecutor {

    private final String schemataPrefix;

    public FullSchemataDeletingTestCaseExecutor(Schema schema, DBMS dbms, DatabaseInteractor databaseInteractor, String schemataPrefix) {
        super(schema, dbms, databaseInteractor);
        this.schemataPrefix = schemataPrefix;
    }

    @Override
    public void executeInserts(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : tables) {
            for (Table stateTable : stateTables) {
                if (table.getIdentifier().toString().replace(schemataPrefix, "").equals(stateTable.getIdentifier().toString())) {
                    List<Row> rows = data.getRows(stateTable);
                    for (Row row : rows) {
                        String statement = sqlWriter.writeInsertStatement(row).replaceAll("INSERT INTO \"", "INSERT INTO \"" + schemataPrefix);
                        Integer result = databaseInteractor.executeUpdate(statement);
                        if (result != 1) {
                            throw new InsertStatementException("Failed, result was: " + result, statement);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void executeInsertsInTransaction(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : tables) {
            for (Table stateTable : stateTables) {
                if (table.getIdentifier().toString().replace(schemataPrefix, "").equals(stateTable.getIdentifier().toString())) {
                    List<Row> rows = data.getRows(stateTable);
                    List<String> statements = new ArrayList<>();
                    for (Row row : rows) {
                        String statement = sqlWriter.writeInsertStatement(row).replaceAll("INSERT INTO \"", "INSERT INTO \"" + schemataPrefix);
                        statements.add(statement);
                    }
                    Integer result = databaseInteractor.executeUpdatesAsTransaction(statements);
                    if (result != 1) {
                        throw new InsertStatementException("Failed, result was: " + result, "(Executed in transaction)");
                    }
                }
            }
        }
    }

}
