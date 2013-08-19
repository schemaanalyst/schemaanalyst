package org.schemaanalyst.sqlrepresentation.constraint;

import java.io.Serializable;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.collection.IdentifiableEntity;
import org.schemaanalyst.util.collection.IdentifiableEntitySet;

/**
 * Abstract super class of all integrity constraints.
 * 
 * @author Phil McMinn
 * 
 */
public abstract class Constraint extends IdentifiableEntity implements
        Duplicable<Constraint>, Serializable {

    private static final long serialVersionUID = -1552612200017351725L;

    protected Table table;

    /**
     * Constructor.
     * 
     * @param name
     *            An identifying name for the constraint (optional - can be
     *            null).
     * @param table
     *            The table that is the centre of the integrity constraint.
     */
    public Constraint(String name, Table table) {
        setName(name);
        this.table = table;
    }

    /**
     * Gets the table of the constraint.
     * 
     * @return The table of the constriant.
     */
    public Table getTable() {
        return table;
    }    
    
    /**
     * Remaps the columns of the constraint to that of another table.
     * 
     * @param table
     *            The table to remap the columns to.
     */
    public void remap(Table table) {
        this.table = table;
    }

    /**
     * Duplicates the constraint.
     */
    public abstract Constraint duplicate();

    /**
     * Allows instances of the class to be visited by a ConstraintVisitor.
     * 
     * @param visitor
     *            The visitor instance.
     */
    public abstract void accept(ConstraintVisitor visitor);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }

    @Override
    /**
     * The <tt>equals</tt> method for constraints ignores names assigned,
     * taking into account other attributes only.
     *  
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Constraint other = (Constraint) obj;
        if (table == null) {
            if (other.table != null)
                return false;
        } else if (!table.equals(other.table))
            return false;
        return true;
    }

    protected static IdentifiableEntitySet<Column> remapColumns(
            Table table, IdentifiableEntitySet<Column> columns) {
        IdentifiableEntitySet<Column> remappedColumns = new IdentifiableEntitySet<>();
        for (Column column : columns) {
            remappedColumns.add(remapColumn(table, column));
        }
        return remappedColumns;
    }

    protected static Column remapColumn(Table table, Column column) {
        Column remappedColumn = table.getColumn(column.getName());
        if (!column.equals(remappedColumn)) {
            throw new SQLRepresentationException("Cannot remap column \""
                    + column
                    + "\" - an identical column does not exist in table \""
                    + table + "\"");
        }
        return remappedColumn;
    }
}
