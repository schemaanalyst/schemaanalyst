package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Represents a check constraint applied to a table in a database schema.
 *
 * @author Phil McMinn
 *
 */
public class CheckConstraint extends Constraint {
	
    private static final long serialVersionUID = 1112035994865637833L;

    private Expression expression;

    /**
     * Constructor.
     * @param expression The expression associated with the CHECK constraint.
     */
    public CheckConstraint(Table table, Expression expression) {
        this(null, table, expression);
    }    
    
    /**
     * Constructor.
     * @param name An identifying name for the constraint (can be null).
     * @param expression The expression associated with the CHECK constraint.
     */
    public CheckConstraint(String name, Table table, Expression expression) {
        super(name, table);
        this.expression = expression;
    }

    /**
     * Returns the expression denoting this CHECK constraint.
     * @return The expression denoting this CHECK constraint.
     */
    public Expression getExpression() {
        return expression;
    }
    
    /**
     * Set the expression in this CHECK constraint. 
     * @param expression The Expression for this CHECK constraint.
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Remaps the column of the constraint to that of the counterpart in
     * another table.
     * @param table The table to remap the constraint's column to.
     */
    public void remap(Table table) {
        super.remap(table);
        expression.remap(table);
    }    
    
    /**
     * Allows instances of ConstraintVisitor to visit this constraint.
     * @param visitor The visitor that wishes to visit the constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Duplicates the CHECK constraint and its expression.
     * @return A duplicate of this constraint.
     */
    @Override
    public CheckConstraint duplicate() {
    	return new CheckConstraint(
    	        getName(), 
    	        table,
    	        expression.duplicate());
    }
    
 	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((expression == null) ? 0 : expression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CheckConstraint other = (CheckConstraint) obj;
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
    @Override
    public String toString() {
        return "CHECK[" + expression + "]";
    }
}
