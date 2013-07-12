package org.schemaanalyst.mutation;

/**
 * This class represents the two-tuple (SQL Statement, Return Code)
 */
public class SQLSelectRecord extends SQLExecutionRecord {

    public String toString() {
        return "(SELECT: " + statement + ", " + returnCode + ")";
    }
}