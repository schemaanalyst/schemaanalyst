package org.schemaanalyst.datageneration;

import org.schemaanalyst.representation.Constraint;

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
	
	public String getDescription() {
		if (constraint == null) {
			return "Satisfying all constraints";
		} else {
			return "Negating \"" + constraint + "\" on table \"" + constraint.getTable() + "\"";
		}
	}	
}
