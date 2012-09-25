package org.schemaanalyst.datageneration;

import org.schemaanalyst.schema.Constraint;

public class SimpleConstraintGoalReport extends GoalReport {

	protected Constraint constraint;
	protected boolean isSatisfied;

	public SimpleConstraintGoalReport(Constraint constraint, boolean isSatisfied) {
		this.constraint = constraint;
		this.isSatisfied = isSatisfied;
	}
	
	public Constraint getConstraint() {
		return constraint;
	}
	
	public boolean isSatisfied() {
		return isSatisfied;
	}
	
	public String getDescription() {
		return constraint + " satisfied: "+isSatisfied;
	}
}
