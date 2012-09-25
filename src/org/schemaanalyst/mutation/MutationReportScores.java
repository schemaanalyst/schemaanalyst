package org.schemaanalyst.mutation;

import java.util.LinkedHashMap;

import org.schemaanalyst.mutation.MutationReportScore;

public class MutationReportScores {
    
    LinkedHashMap<String, MutationReportScore> scores;

    public MutationReportScores() {
	scores = new LinkedHashMap<String, MutationReportScore>();
    }

    public void add(MutationReportScore report) {
	scores.put(report.getName(), report);
    }

    public MutationReportScore get(String name) {
	return scores.get(name);
    }

    public String toString() {
	return scores.toString();
    }
}