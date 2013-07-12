package org.schemaanalyst.mutation;

/**
 * This class represents the two-tuple (SQL Statement, Return Code). Used
 * directly inside of the SchemaAnalyst mutation analysis process.
 */
public class MutantRecord extends SQLInsertRecord implements MutantRecordImpl {

    /**
     * The boolean flag indicating whether this statement killed the MUTANT
     * schema
     */
    private boolean killedMutant;

    public MutantRecord() {
        super();
        killedMutant = false;
    }

    public void killedMutant() {
        killedMutant = true;
    }

    public void sparedMutant() {
        killedMutant = false;
    }

    public boolean didKillMutant() {
        return killedMutant;
    }

    public String toString() {
        return "(" + super.toString() + ", Killed? " + killedMutant + ")";
    }
}