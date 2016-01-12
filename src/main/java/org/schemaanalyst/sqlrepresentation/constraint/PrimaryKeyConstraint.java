package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Represents the PRIMARY KEY of a table.
 *
 * @author Phil McMinn
 *
 */
public class PrimaryKeyConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = -3568002542239034859L;

    /**
     * Constructor.
     * @param table The table on which the PRIMARY KEY is defined.
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(Table table, Column... columns) {
        super(null, table, columns);
    }    
    
    /**
     * Constructor.
     * @param name A name for the primary key constraint (optional - can be
     * null).
     * @param table The table on which the PRIMARY KEY is defined. 
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(String name, Table table, Column... columns) {
        super(name, table, columns);
    }
    
    /**
     * Constructor.
     * @param table The table on which the PRIMARY KEY is defined.
     * @param columns The columns involved in the primary key.
     */
    public PrimaryKeyConstraint(Table table, List<Column> columns) {
        super(null, table, columns);
    }    
    
    /**
     * Constructor.
     * @param name A name for the PRIMARY KEY constraint (optional - can be
     * null).
     * @param table The table on which the PRIMARY KEY is defined. 
     * @param columns The columns involved in the PRIMARY KEY.
     */
    public PrimaryKeyConstraint(String name, Table table, List<Column> columns) {
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
    public PrimaryKeyConstraint duplicate() {
        return new PrimaryKeyConstraint(getName(), table, columns);
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
