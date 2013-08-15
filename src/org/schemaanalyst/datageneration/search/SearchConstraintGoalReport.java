package org.schemaanalyst.datageneration.search;

import org.schemaanalyst.datageneration.ConstraintGoalReport;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class SearchConstraintGoalReport extends ConstraintGoalReport {

    protected int numEvaluations, numRestarts;
    protected ObjectiveValue bestObjectiveValue;

    public SearchConstraintGoalReport(Table table, Constraint constraint) {
        super(table, constraint);
        numEvaluations = 0;
        numRestarts = 0;
    }

    public int getNumEvaluations() {
        return numEvaluations;
    }

    public void setNumEvaluations(int numEvaluations) {
        this.numEvaluations = numEvaluations;
    }

    public int getNumRestarts() {
        return numRestarts;
    }

    public void setNumRestarts(int numRestarts) {
        this.numRestarts = numRestarts;
    }

    public void setBestObjectiveValue(ObjectiveValue bestObjectiveValue) {
        this.bestObjectiveValue = bestObjectiveValue;
    }

    public ObjectiveValue getBestObjectiveValue() {
        return bestObjectiveValue;
    }

    @Override
    public void appendToStringBuilder(StringBuilder sb) {
        super.appendToStringBuilder(sb);

        sb.append("-- * Number of objective function evaluations: ");
        sb.append(getNumEvaluations());
        sb.append("\n");

        sb.append("-- * Number of restarts: ");
        sb.append(getNumRestarts());
        sb.append("\n");

        if (!wasSuccess()) {
            sb.append("/* Objective value:\n");
            getBestObjectiveValue().appendToStringBuilder(sb, " ");
            sb.append("*/ \n");
        }
    }
}
