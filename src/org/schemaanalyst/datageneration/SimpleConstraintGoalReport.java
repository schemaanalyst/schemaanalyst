package org.schemaanalyst.datageneration;

import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SimpleConstraintGoalReport extends GoalReport {

    protected Constraint constraint;
    protected boolean isSatisfied;

    public SimpleConstraintGoalReport(Schema schema, Constraint constraint, boolean isSatisfied) {
        super(schema);
        this.constraint = constraint;
        this.isSatisfied = isSatisfied;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    @Override
    public String getDescription() {
        return constraint + " satisfied: " + isSatisfied;
    }
}
