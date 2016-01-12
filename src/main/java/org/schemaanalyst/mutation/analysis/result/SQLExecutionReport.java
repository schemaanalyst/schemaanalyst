package org.schemaanalyst.mutation.analysis.result;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An SQLExecutionReport records the results of executing multiple CREATE TABLE 
 * and INSERT statements, as {@link SQLExecutionRecord}s and 
 * {@link SQLInsertRecord}s respectively.
 * </p>
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

    /**
     * Default constructor.
     */
    public SQLExecutionReport() {
        createTableStatements = new ArrayList<>();
        insertStatements = new ArrayList<>();
    }

    /**
     * Add the result of a CREATE TABLE statement to the report.
     * 
     * @param statement The result of the CREATE TABLE statement
     */
    public void addCreateTableStatement(SQLExecutionRecord statement) {
        createTableStatements.add(statement);
    }

    /**
     * Get the list of results of CREATE TABLE statements.
     * 
     * @return The results
     */
    public List<SQLExecutionRecord> getCreateTableStatements() {
        return createTableStatements;
    }

    /**
     * Ad the result of an INSERT statement to the report.
     * 
     * @param statement The result of the INSERT statement
     */
    public void addInsertStatement(SQLInsertRecord statement) {
        insertStatements.add(statement);
    }

    /**
     * Get the list of results of INSERT statements.
     * 
     * @return The results
     */
    public List<SQLInsertRecord> getInsertStatements() {
        return insertStatements;
    }

    @Override
    public String toString() {
        return "SQLExecutionReport{"
                + "createStatements=\n" + StringUtils.join(createTableStatements,"\n") + "\n"
                + "insertStatements=\n" + StringUtils.join(insertStatements,"\n") + "\n}";
    }
}