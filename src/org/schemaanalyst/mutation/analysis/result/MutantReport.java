/*
 */

package org.schemaanalyst.mutation.analysis.result;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chris J. Wright
 */
public class MutantReport extends SQLExecutionReport {

    private MutantStatus status = MutantStatus.UNKNOWN;
    private String mutantDescription;
    private String changedTable;

    public MutantReport(String mutantDescription, String changedTable) {
        this.mutantDescription = mutantDescription;
        this.changedTable = changedTable;
    }
    
    public void setMutantStatus(MutantStatus status) {
        this.status = status;
    }
    
    public enum MutantStatus {
        ALIVE, KILLED, QUASI, UNKNOWN;
    }
}
