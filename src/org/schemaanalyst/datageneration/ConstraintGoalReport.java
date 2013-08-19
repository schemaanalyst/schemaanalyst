package org.schemaanalyst.datageneration;

import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class ConstraintGoalReport extends GoalReport {

    protected Schema schema;
    protected Table table;
    protected Constraint constraint;

    public ConstraintGoalReport(Schema schema, Table table) {
        this(schema, table, null);
    }

    public ConstraintGoalReport(Schema schema, Table table, Constraint constraint) {
        super(schema);
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
