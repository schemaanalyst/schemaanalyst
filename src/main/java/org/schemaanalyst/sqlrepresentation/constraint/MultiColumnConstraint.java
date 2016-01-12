package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract superclass for integrity constrains that potentially involve
 * multiple columns (PrimaryKey, ForeignKey and Unique).
 *
 * @author Phil McMinn
 *
 */
public abstract class MultiColumnConstraint extends Constraint {

    private static final long serialVersionUID = 173815859465417000L;
    protected List<Column> columns;

    /**
     * Constructor.
     * @param name A name for the constraint (optional - can be null).
     * @param table The table that is the centre of the integrity constraint. 
     * @param columns The columns involved in the integrity constraint.
     */
    public MultiColumnConstraint(String name, Table table, Column... columns) {
        this(name, table, Arrays.asList(columns));
    }    
    
    /**
     * Constructor.
     * @param name A name for the constraint (optional - can be null).
     * @param table The table that is the centre of the integrity constraint. 
     * @param columns The columns involved in the integrity constraint.
     */
    public MultiColumnConstraint(String name, Table table, List<Column> columns) {
        super(name, table);
        setColumns(columns);
    }

    /**
     * Remaps the columns of the constraint to that of another table.
     * @param table The table to remap the columns to.
     */
    @Override
    public void remap(Table table) {
        super.remap(table);
        this.columns = remapColumns(table, columns);
    }    
    
    /**
     * Sets the columns involved in the integrity constraint.
     * @param columns The columns involved in the integrity constraint.
     */
    public void setColumns(List<Column> columns) {
        this.columns = new ArrayList<>();
        for (Column column : columns) {
            if (!table.hasColumn(column)) {
                throw new SQLRepresentationException(
                        "No such column \"" + column + 
                        "\" in table " + table);                  
            }            
            if (this.columns.contains(column)) {
                throw new SQLRepresentationException(
                        "Column \"" + column + 
                        "\" already defined for this constraint");
            }
            this.columns.add(column);
        }
    }
       
    /**
     * Gets the columns involved in the integrity constraint.
     * @return A list of the columns involved in the integrity constraint.
     */
    public List<Column> getColumns() {
        return new ArrayList<>(columns);
    }

    /**
     * Checks if a column is involved in the integrity constraint or not.
     * @param column The column whose status is to be checked.
     * @return True if the column is involved, else false.
     */
    public boolean involvesColumn(Column column) {
        return columns.contains(column);
    }

    /**
     * Gets the number of columns involved in the integrity constraint.
     * @return The number of columns involved in the integrity constraint.
     */
    public int getNumColumns() {
        return columns.size();
    }

    /**
     * Returns true if more than one column involved in this constraint.
     * @return True if more than one column involved in this constraint, else
     * false.
     */
    public boolean hasMultipleColumns() {
        return columns.size() > 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MultiColumnConstraint other = (MultiColumnConstraint) obj;
        if (columns == null) {
            if (other.columns != null) {
                return false;
            }
        } else if (!columns.equals(other.columns)) {
            return false;
        }
        return true;
    }
}
