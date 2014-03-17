package _deprecated.datageneration;

import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class ConstraintGoal {

	private Constraint constraint;
	private boolean satisfy;

	public ConstraintGoal(Constraint constraint, boolean satisfy) {
		this.constraint = constraint;
		this.satisfy = satisfy;
	}

	public Constraint getConstraint() {
		return constraint;
	}
	
	public boolean getSatisfy() {
		return satisfy;
	}
	
	public String toString() {
		return (satisfy ? "Satisfy" : "Negate") + " "
				+ (constraint == null ? "all constraints" : constraint);
	}

}
