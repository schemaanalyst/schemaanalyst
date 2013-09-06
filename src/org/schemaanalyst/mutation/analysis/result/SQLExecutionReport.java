package org.schemaanalyst.mutation.analysis.result;

import java.util.List;
import java.util.ArrayList;

import deprecated.utils.StringUtils;

/**
 * Contains the results of executing CREATE TABLE and INSERT statements
 */
public class SQLExecutionReport {

    /**
     * The record of the CREATE TABLEs for the original schema
     */
    protected List<SQLExecutionRecord> createTableStatements;
    /**
     * The record of the INSERT statements for an existing schema
     */
    protected List<SQLInsertRecord> insertStatements;

    public SQLExecutionReport() {
        createTableStatements = new ArrayList<>();
        insertStatements = new ArrayList<>();
    }

    /**
     * Add a CREATE TABLE statement to the list
     */
    public void addCreateTableStatement(SQLExecutionRecord statement) {
        createTableStatements.add(statement);
    }

    /**
     * Return the list of CREATE TABLE statements
     */
    public List<SQLExecutionRecord> getCreateTableStatements() {
        return createTableStatements;
    }

    /**
     * Add an INSERT statement to the list
     */
    public void addInsertStatement(SQLInsertRecord statement) {
        insertStatements.add(statement);
    }

    /**
     * Return the list of INSERT statements
     */
    public List<SQLInsertRecord> getInsertStatements() {
        return insertStatements;
    }

    @Override
    public String toString() {
        return "SQLExecutionReport{"
                + "createStatements=\n" + StringUtils.implode(createTableStatements,"\n") + "\n"
                + "insertStatements=\n" + StringUtils.implode(insertStatements,"\n") + "\n}";
    }
}