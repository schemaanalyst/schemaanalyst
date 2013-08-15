package org.schemaanalyst.datageneration;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class ConstraintGoalReport extends GoalReport {

    protected Table table;
    protected Constraint constraint;

    public ConstraintGoalReport(Table table) {
        this(table, null);
    }

    public ConstraintGoalReport(Table table, Constraint constraint) {
        this.table = table;
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
            return "Negating \"" + constraint + "\" on table \"" + table + "\"";
        }
    }
}
