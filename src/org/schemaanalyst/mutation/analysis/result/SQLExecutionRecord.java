package org.schemaanalyst.mutation.analysis.result;

/**
 * <p>
 * An SQLExecutionRecord represents the tuple (SQL statement, Return code}.
 * </p>
 */
public class SQLExecutionRecord {

    /**
     * The SQL statement that was executed
     */
    protected String statement;
    /**
     * The return code of the SQL statement that was executed
     */
    protected int returnCode;
    /**
     * The default value of the return code
     */
    private static int DEFAULT_RETURN_CODE = 0;
    /**
     * The default value of the statement
     */
    private static String DEFAULT_STATEMENT = "none";

    /**
     * Constructor, using the default return code and default statement.
     */
    public SQLExecutionRecord() {
        returnCode = DEFAULT_RETURN_CODE;
        statement = DEFAULT_STATEMENT;
    }

    /**
     * Constructor, using the given return code and statement.
     * 
     * @param statement The statement
     * @param returnCode The return code
     */
    public SQLExecutionRecord(String statement, int returnCode) {
        this.statement = statement;
        this.returnCode = returnCode;
    }

    /**
     * Set the statement.
     * 
     * @param statement The statement
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * Set the return code.
     * 
     * @param returnCode The return code
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * Get the statement.
     * 
     * @return The statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Get the return code.
     * 
     * @return The return code
     */
    public int getReturnCode() {
        return returnCode;
    }

    @Override
    public String toString() {
        return "SQLExecutionRecord{" + "statement=" + statement + ", returnCode=" + returnCode + '}';
    }
}