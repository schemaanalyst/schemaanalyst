package org.schemaanalyst.mutation;

import java.util.List;
import java.util.ArrayList;
import org.schemaanalyst.util.StringUtils;

public class DBMonsterSQLExecutionReport extends SQLExecutionReport {

    /**
     * The record of the SELECT statements for an existing schema that is only
     * used for the DBMonster mutation analysis process
     */
    private List<SQLSelectRecord> selectStatements;

    public DBMonsterSQLExecutionReport() {
        super();
        selectStatements = new ArrayList<>();
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

    @Override
    public String toString() {
        return "DBMonsterSQLExecutionReport{"
                + "createStatements=\n" + StringUtils.implode(createTableStatements,"\n") + "\n"
                + "insertStatements=\n" + StringUtils.implode(insertStatements,"\n") + "\n"
                + "selectStatements=\n" + StringUtils.implode(selectStatements,"\n") + "\n}";
    }
}