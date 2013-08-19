package org.schemaanalyst.datageneration;

import java.util.HashSet;
import java.util.Set;

import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SimpleConstraintCoverageReport extends ConstraintCoverageReport {

    protected Set<Constraint> satisfiedConstraints, negatedConstraints;
    protected int numAttempts;

    public SimpleConstraintCoverageReport(Schema schema) {
        super(schema);
        satisfiedConstraints = new HashSet<>();
        negatedConstraints = new HashSet<>();
    }

    public void addGoalReport(SimpleConstraintGoalReport report) {
        addGoalReport((GoalReport) report);

        Set<Constraint> toUse = report.isSatisfied() ? satisfiedConstraints : negatedConstraints;
        toUse.add(report.getConstraint());
    }

    @Override
    public int getTotalCovered() {
        return satisfiedConstraints.size() + negatedConstraints.size();
    }

    @Override
    public int getNumGoals() {
        return numConstraints * 2;
    }

    public void setNumAttempts(int numAttempts) {
        this.numAttempts = numAttempts;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public Set<Constraint> getSatisfiedConstraints() {
        return satisfiedConstraints;
    }

    public Set<Constraint> getNegatedConstraints() {
        return negatedConstraints;
    }
}
