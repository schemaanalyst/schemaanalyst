package org.schemaanalyst.datageneration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoverageReport {

    protected static final int PERCENTAGE_DECIMAL_PLACES = 5;
    protected String description;
    protected List<GoalReport> goalReports;

    public CoverageReport(String description) {
        this.description = description;
        goalReports = new ArrayList<>();
    }

    public List<GoalReport> getGoalReports() {
        return Collections.unmodifiableList(goalReports);
    }

    public List<GoalReport> getSuccessfulGoalReports() {
        List<GoalReport> successfulGoalReports = new ArrayList<>();
        for (GoalReport goalReport : goalReports) {
            if (goalReport.wasSuccess()) {
                successfulGoalReports.add(goalReport);
            }
        }
        return successfulGoalReports;
    }

    public void addGoalReport(GoalReport report) {
        goalReports.add(report);
    }

    public long getTimeToGenerate() {
        long time = 0;
        for (GoalReport goalReport : goalReports) {
            time += goalReport.getTimeToGenerate();
        }
        return time;
    }

    public BigDecimal getCoveragePercentage() {
        int numGoals = getNumGoals();
        if (numGoals == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal covered = new BigDecimal(getTotalCovered());
        BigDecimal total = new BigDecimal(numGoals);

        BigDecimal percentage =
                covered.multiply(new BigDecimal(100))
                .divide(total,
                PERCENTAGE_DECIMAL_PLACES,
                BigDecimal.ROUND_HALF_UP);

        return percentage;
    }

    public int getTotalCovered() {
        int total = 0;
        for (GoalReport goalReport : goalReports) {
            if (goalReport.wasSuccess()) {
                total++;
            }
        }
        return total;
    }

    public int getNumGoals() {
        return goalReports.size();
    }

    protected void appendDescriptionToStringBuilder(StringBuilder sb) {
        sb.append("/*");
        for (int i = 0; i < description.length() + 3; i++) {
            sb.append("*");
        }
        sb.append("\n * ");
        sb.append(description);
        sb.append(" *\n ");
        for (int i = 0; i < description.length() + 3; i++) {
            sb.append("*");
        }
        sb.append("*/\n");
    }

    protected void appendStatisticsToStringBuilder(StringBuilder sb) {
        sb.append("-- Coverage: ");
        sb.append(getTotalCovered());
        sb.append("/");
        sb.append(getNumGoals());
        sb.append(" (");
        sb.append(getCoveragePercentage());
        sb.append("%) \n");

        sb.append("-- Time to generate: ");
        sb.append(getTimeToGenerate());
        sb.append("ms \n");
    }

    protected void appendGoalReportsToStringBuilder(StringBuilder sb) {
        for (GoalReport goalReport : goalReports) {
            sb.append("\n");
            goalReport.appendToStringBuilder(sb);
        }
    }

    public void appendToStringBuilder(StringBuilder sb) {
        appendDescriptionToStringBuilder(sb);
        appendStatisticsToStringBuilder(sb);
        appendGoalReportsToStringBuilder(sb);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendToStringBuilder(sb);
        return sb.toString();
    }
}
