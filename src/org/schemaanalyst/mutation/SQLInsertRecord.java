package org.schemaanalyst.mutation;

/**
 * This class represents the two-tuple (SQL Statement, Return Code)
 */
public class SQLInsertRecord extends SQLExecutionRecord {

    /**
     * Whether this SQL statement is trying to satisfy or negate constraints
     */
    private boolean isSatisfying = false;

    public SQLInsertRecord() {
        super();
    }

    public SQLInsertRecord(String statement, Integer returnCode) {
        super(statement, returnCode);
    }

    public void tryToSatisfy() {
        isSatisfying = true;
    }

    public void tryToNegate() {
        isSatisfying = false;
    }

    public boolean isTryingToSatisfy() {
        return isSatisfying;
    }

    public boolean isTryingToNegate() {
        return !isSatisfying;
    }

    @Override
    public String toString() {
        return "SQLInsertRecord{" + "isSatisfying=" + isSatisfying + '}';
    }
}