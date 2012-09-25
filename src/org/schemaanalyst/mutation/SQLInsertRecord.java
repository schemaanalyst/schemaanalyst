package org.schemaanalyst.mutation;

/** This class represents the two-tuple (SQL Statement, Return Code) */
public class SQLInsertRecord extends SQLExecutionRecord {

    /** Whether this SQL statement is trying to satisfy or negate constraints */
    private boolean isSatisfying;

    public SQLInsertRecord() {
	super();
	isSatisfying = false;
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

    public String toString() {
	return "(INSERT: " + statement + ", " + returnCode + ", Satisfy? " + isSatisfying + " Negate? " + !isSatisfying + ")";
    }

}