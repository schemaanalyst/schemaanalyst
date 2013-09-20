package deprecated.report;

import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class ConstraintGoalReport extends GoalReport {

    protected Constraint constraint;

    public ConstraintGoalReport() {
        constraint = null;
    }

    public ConstraintGoalReport(Constraint constraint) {
        this.constraint = constraint;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    @Override
    public String getDescription() {
        if (constraint == null) {
            return "Satisfying all constraints";
        } else {
            return "Negating \"" + constraint + "\" on table \"" + constraint.getTable() + "\"";
        }
    }
}
