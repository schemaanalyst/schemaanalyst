package org.schemaanalyst.mutation;

/** This class represents the two-tuple (SQL Statement, Return Code) */
public class SQLExecutionRecord {

    /** The SQL statement that was executed */
    protected String statement;

    /** The return code of the SQL statement that was executed */
    protected int returnCode;

    /** The default value of the return code */
    private static int DEFAULT_RETURN_CODE = 0;

    /** The default value of the statement*/
    private static String DEFAULT_STATEMENT = "none";
    
    public SQLExecutionRecord() {
	returnCode = DEFAULT_RETURN_CODE;
	statement = DEFAULT_STATEMENT;
    }

    public void setStatement(String statement) {
	this.statement = statement;
    }

    public void setReturnCode(int returnCode) {
	this.returnCode = returnCode;
    }

    public String getStatement() {
	return statement;
    }

    public int getReturnCode() {
	return returnCode;
    }

    public String toString() {
	return "(" + statement + ", " + returnCode + ")";
    }

}