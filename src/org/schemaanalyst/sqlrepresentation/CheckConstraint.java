package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.checkcondition.CheckCondition;
import org.schemaanalyst.sqlrepresentation.expression.Expression;


/**
 * Represents a check constraint applied to a table in a database schema.
 * @author Phil McMinn
 *
 */
public class CheckConstraint extends Constraint {

	private static final long serialVersionUID = 1112035994865637833L;

	protected CheckCondition checkCondition;
	protected Expression expression;
	
	/**
	 * Constructor.
	 * @param table The table on which the check constraint should hold.
	 * @param name An identifying name for the constraint (can be null).
	 * @param checkCondition The condition associated with the check constraint.
	 */
	protected CheckConstraint(Table table, String name, CheckCondition checkCondition) {
		super(table, name);
		this.checkCondition = checkCondition;
	}
	
	/**
	 * Constructor.
	 * @param table The table on which the check constraint should hold.
	 * @param name An identifying name for the constraint (can be null).
	 * @param checkCondition The condition associated with the check constraint.
	 */
	protected CheckConstraint(Table table, String name, Expression expression) {
		super(table, name);
		this.expression = expression;
	}	
	
	/**
	 * Returns the condition associated with this check constraint.
	 * @return The condition associated with this check constraint.
	 */
	public CheckCondition getCheckCondition() {
		return checkCondition;
	}
	
	/**
	 * Returns the expression denoting this check constraint.
	 * @return The expression denoting this check constraint.
	 */
	public Expression getExpression() {
		return expression;
	}	
	
	/**
	 * Allows instances of IntegrityConstraintVisitor to visit this constraint.
	 * @param visitor The visitor that wishes to visit the constraint.
	 */
	
	public void accept(ConstraintVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Copies the check predicate to a different table.  
	 * (NOTE/TODO: the predicate is not currently remapped to the
	 * new table's equivalent columns).
	 * @param targetTable The table to copy the constraint to/
	 * @return The copied Check object.
	 */
	public CheckConstraint copyTo(Table targetTable) {
		// TODO -- remap checkCondition to the new table ...		
		CheckConstraint copy = new CheckConstraint(targetTable, this.name, this.checkCondition);
		targetTable.addCheckConstraint(copy);
		return copy;
	}
	
	/**
	 *  Checks whether this Check instance is equal to another object.
	 *  The comparison compares predicates only and ignores the constraint's name.
	 *  @param obj The object to compare this instance with.
	 *  @return True if the other object is a Check object with the same predicate, else false.
	 */	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		CheckConstraint other = (CheckConstraint) obj;
		
		if (checkCondition == null) {
			if (other.checkCondition != null)
				return false;
		} else if (!checkCondition.equals(other.checkCondition))
			return false;
		
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;		
		
		return true;
	}
	
	/**
	 * Returns an informative string regarding the constraint.
	 * @return An informative string regarding the constraint.
	 */
	public String toString() {
		return "CHECK["+checkCondition.toString()+"]";
	}	
}
