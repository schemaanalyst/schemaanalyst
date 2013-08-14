package deprecated.mutation;

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

    @Override
    public String toString() {
        return "MutantRecord{" + "killed=" + killed + '}';
    }
}