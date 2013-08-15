package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;

/**
 * Represents the primary key of a table.
 *
 * @author Phil McMinn
 *
 */
public class PrimaryKeyConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = -3568002542239034859L;

    /**
     * Constructor.
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(Column... columns) {
        super(null, columns);
    }    
    
    /**
     * Constructor.
     * @param name A name for the primary key constraint (optional - can be
     * null).
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(String name, Column... columns) {
        super(name, columns);
    }
    
    /**
     * Constructor.
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(List<Column> columns) {
        super(null, columns);
    }    
    
    /**
     * Constructor.
     * @param name A name for the primary key constraint (optional - can be
     * null).
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(String name, List<Column> columns) {
        super(name, columns);
    }

    /**
     * Method for accepting visitors of type IntegrityConstraintVisitor.
     * @param visitor The IntegrityConstraintVisitor instance visiting this
     * constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Makes a shallow copy of the current instance.
     * @return A shallow copy of the current instance. 
     */
    @Override
    public PrimaryKeyConstraint duplicate() {
        return new PrimaryKeyConstraint(name, new ArrayList<>(columns));
    }
    
    /**
     * Returns an informative string about the foreign key instance.
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "PRIMARY KEY" + columns.toString();
    }
}
