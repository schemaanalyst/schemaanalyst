package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;


public class UniqueConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = -3188129142878269469L;

    /**
     * Constructor.
     * @param table The table that is the centre of the UNIQUE constraint.
     * @param columns The columns involved in the UNIQUE constraint.
     */
    public UniqueConstraint(Table table, Column... columns) {
        super(null, table, columns);
    }
    
    /**
     * Constructor. 
     * @param name A name for the UNIQUE constraint (optional - can be null).
     * @param table The table that is the centre of the UNIQUE constraint. 
     * @param columns The columns involved in the UNIQUE constraint.
     */
    public UniqueConstraint(String name, Table table, Column... columns) {
        super(name, table, columns);
    }    
    
    /**
     * Constructor.
     * @param table The table that is the centre of the UNIQUE constraint. 
     * @param columns The columns involved in the UNIQUE constraint.
     */
    public UniqueConstraint(Table table, List<Column> columns) {
        super(null, table, columns);
    }
    
    /**
     * Constructor.
     * @param name A name for the UNIQUE constraint (optional - can be null).
     * @param table The table that is the centre of the UNIQUE constraint.
     * @param columns The columns involved in the UNIQUE constraint.
     */
    public UniqueConstraint(String name, Table table, List<Column> columns) {
        super(name, table, columns);
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
     * Makes a shallow copy of the current instance.
     * @return A shallow copy of the current instance. 
     */
    @Override
    public UniqueConstraint duplicate() {
        return new UniqueConstraint(getName(), table, columns);
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
