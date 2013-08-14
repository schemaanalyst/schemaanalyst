package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Column;

/**
 * Represents a NOT NULL integrity constraint.
 *
 * @author Phil McMinn
 *
 */
public class NotNullConstraint extends Constraint {

    private static final long serialVersionUID = 26638174760035819L;
    private Column column;

    /**
     * Constructor.
     *
     * @param table The table containing the column on which the NOT NULL
     * constraint holds.
     * @param column The column that is designated to be NOT NULL.
     */
    public NotNullConstraint(Column column) {
        this(null, column);
    }    
    
    /**
     * Constructor.
     *
     * @param name A name for the constraint (optional - can be null).
     * @param table The table containing the column on which the NOT NULL
     * constraint holds.
     * @param column The column that is designated to be NOT NULL.
     */
    public NotNullConstraint(String name, Column column) {
        super(name);
        this.column = column;
    }

    /**
     * Gets the column which is designated as NOT NULL.
     *
     * @return The column that is the subject of the NOT NULL constraint.
     */
    public Column getColumn() {
        return column;
    }

    /**
     * Method for accepting visitors of type IntegrityConstraintVisitor.
     *
     * @param visitor The IntegrityConstraintVisitor instance visiting this
     * constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Generates a shallow copy of the constraint (the column is not
     * deep-copied)
     */
    @Override
    public NotNullConstraint duplicate() {
    	return new NotNullConstraint(name, column);
    }



    /**
     * Returns an informative string about the foreign key instance.
     *
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "NOT NULL(" + column + ")";
    }
}
