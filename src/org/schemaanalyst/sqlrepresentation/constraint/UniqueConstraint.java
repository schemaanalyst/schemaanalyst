package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;

public class UniqueConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = -3188129142878269469L;

    /**
     * Constructor.
     * @param columns The columns involved in the unique constraint.
     */
    public UniqueConstraint(Column... columns) {
        super(null, columns);
    }
    
    /**
     * Constructor.
     * @param name A name for the unique constraint (optional - can be null).
     * @param columns The columns involved in the unique constraint.
     */
    public UniqueConstraint(String name, Column... columns) {
        super(name, columns);
    }    
    
    /**
     * Constructor.
     * @param columns The columns involved in the unique constraint.
     */
    public UniqueConstraint(List<Column> columns) {
        super(null, columns);
    }
    
    /**
     * Constructor.
     * @param name A name for the unique constraint (optional - can be null).
     * @param columns The columns involved in the unique constraint.
     */
    public UniqueConstraint(String name, List<Column> columns) {
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
    public UniqueConstraint duplicate() {
        return new UniqueConstraint(name, new ArrayList<>(columns));
    }
    
    /**
     * Returns an informative string about the unique constraint instance.
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "UNIQUE" + columns.toString();
    }
}
