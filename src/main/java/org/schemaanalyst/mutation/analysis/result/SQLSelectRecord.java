package org.schemaanalyst.mutation.analysis.result;

/**
 * <p> 
 * An SQLSelectRecord represents the tuple (SQL statement, Return code}, 
 * specialised for SELECT statements.
 * </p>
 */
public class SQLSelectRecord extends SQLExecutionRecord {

    @Override
    public String toString() {
        return "SQLInsertRecord{" + "statement=" + statement + ", returnCode=" + returnCode + '}';
    }
    
}