package org.schemaanalyst.mutation;

import java.util.List;
import java.util.ArrayList;

public class SQLExecutionReport {

    /** The record of the CREATE TABLEs for the original schema */
    protected List<SQLExecutionRecord> createTableStatements;

    /** The record of the INSERT statements for an existing schema */
    protected List<SQLInsertRecord> insertStatements;

    public SQLExecutionReport() {
	createTableStatements = new ArrayList<SQLExecutionRecord>();
	insertStatements = new ArrayList<SQLInsertRecord>();
    }

    /** Add a CREATE TABLE statement to the list */
    public void addCreateTableStatement(SQLExecutionRecord statement) {
	createTableStatements.add(statement);
    }

    /** Return the list of CREATE TABLE statements */
    public List<SQLExecutionRecord> getCreateTableStatements() {
	return createTableStatements;
    }

    /** Add an INSERT statement to the list */
    public void addInsertStatement(SQLInsertRecord statement) {
	insertStatements.add(statement);
    }

    /** Return the list of INSERT statements */
    public List<SQLInsertRecord> getInsertStatements() {
	return insertStatements;
    }

    /** Textual representatoin of the SQLExecutionReport */
    public String toString() {
	return "CREATE TABLES: \n" + MutationUtilities.convertListToString(createTableStatements) + "\n" +
	    "INSERT STATEMENTS: \n" + MutationUtilities.convertListToString(insertStatements);
    }

}