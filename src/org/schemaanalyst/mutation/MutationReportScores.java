package org.schemaanalyst.mutation;

import java.util.LinkedHashMap;

public class MutationReportScores {

    LinkedHashMap<String, MutationReportScore> scores;

    public MutationReportScores() {
        scores = new LinkedHashMap<>();
    }

    public void add(MutationReportScore report) {
        scores.put(report.getName(), report);
    }

    public MutationReportScore get(String name) {
        return scores.get(name);
    }

    @Override
    public String toString() {
        return scores.toString();
    }
}