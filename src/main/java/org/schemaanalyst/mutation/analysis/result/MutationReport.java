/*
 */

package org.schemaanalyst.mutation.analysis.result;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chris J. Wright
 */
public class MutationReport {
    SQLExecutionReport originalReport;
    List<MutantReport> mutantReports;

    public MutationReport(SQLExecutionReport originalReport) {
        this.originalReport = originalReport;
        mutantReports = new ArrayList<>();
    }
    
    public void addMutantReport(MutantReport report) {
        mutantReports.add(report);
    }
    
}
