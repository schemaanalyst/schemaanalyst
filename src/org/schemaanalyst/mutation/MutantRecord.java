package org.schemaanalyst.mutation;

/**
 * This class represents the two-tuple (SQL Statement, Return Code). Used
 * directly inside of the SchemaAnalyst mutation analysis process.
 */
public class MutantRecord extends SQLInsertRecord {

    /**
     * The boolean flag indicating whether this statement killed the MUTANT
     * schema
     */
    private boolean killed;

    public MutantRecord() {
        super();
        killed = false;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }
    
    public boolean isKilled() {
        return killed;
    }

    public String toString() {
        return "(" + super.toString() + ", Killed? " + killed + ")";
    }
}