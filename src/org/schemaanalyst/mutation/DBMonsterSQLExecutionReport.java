package org.schemaanalyst.mutation;

import java.util.List;
import java.util.ArrayList;

public class DBMonsterSQLExecutionReport extends SQLExecutionReport {

    /**
     * The record of the SELECT statements for an existing schema that is only
     * used for the DBMonster mutation analysis process
     */
    private List<SQLSelectRecord> selectStatements;

    public DBMonsterSQLExecutionReport() {
        super();
        selectStatements = new ArrayList<SQLSelectRecord>();
    }

    /**
     * Add a SELECT statement to the list
     */
    public void addSelectStatement(SQLSelectRecord statement) {
        selectStatements.add(statement);
    }

    /**
     * Return the list of SELECT statements
     */
    public List<SQLSelectRecord> getSelectStatements() {
        return selectStatements;
    }

    /**
     * Textual representatoin of the SQLExecutionReport
     */
    public String toString() {
        return "CREATE TABLES: \n" + MutationUtilities.convertListToString(createTableStatements) + "\n"
                + "INSERT STATEMENTS: \n" + MutationUtilities.convertListToString(insertStatements)
                + "SELECT STATEMENTS: \n" + MutationUtilities.convertListToString(selectStatements);
    }
}