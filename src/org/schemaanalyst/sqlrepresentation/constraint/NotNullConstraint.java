package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.Collections;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Represents a NOT NULL integrity constraint.
 * @author Phil McMinn
 */
public class NotNullConstraint extends Constraint {

    private static final long serialVersionUID = 26638174760035819L;
    private Column column;

    /**
     * Constructor.
     * @param column The column that is designated to be NOT NULL.
     */
    public NotNullConstraint(Column column) {
        this(null, column);
    }    
    
    /**
     * Constructor.
     * @param name A name for the constraint (optional - can be null).
     * @param column The column that is designated to be NOT NULL.
     */
    public NotNullConstraint(String name, Column column) {
        super(name);
        this.column = column;
    }

    /**
     * Gets the column which is designated as NOT NULL.
     * @return The column that is the subject of the NOT NULL constraint.
     */
    public Column getColumn() {
        return column;
    }
    
    /**
     * Sets the column to be designated NOT NULL.
     * @param column The column that is the subject of the NOT NULL constraint.
     */
    public void setColumn(Column column) {
        this.column = column;
    }    

    /**
     * Method for accepting visitors of type ConstraintVisitor.
     * @param visitor The ConstraintVisitor instance visiting this
     * constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Remaps the column of the constraint to that of the counterpart in
     * another table.
     * @param table The table to remap the constraint's column to.
     */
    public void remap(Table table) {
        setColumn(remapColumns(Collections.singletonList(column), table).get(0));
    }
    
    /**
     * Generates a shallow copy of the constraint (the column is not
     * deep-copied)
     * @param A shallow copy of the constraint.
     */
    @Override
    public NotNullConstraint duplicate() {
    	return new NotNullConstraint(name, column);
    }

    /**
     * Generates a hash code for this instance.
     * @return The hash code.
     */      
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        return result;
    }

    /**
     * Checks if this instance is equal to another.
     * @param obj Another object.
     * @return True if the objects are equal, else false.
     */ 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotNullConstraint other = (NotNullConstraint) obj;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        return true;
    }

    /**
     * Returns an informative string about the foreign key instance.
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "NOT NULL(" + column + ")";
    }
}
