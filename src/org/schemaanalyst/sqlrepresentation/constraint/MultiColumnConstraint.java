package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
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
     * @param table The table on which the integrity constraint holds.
     * @param columns The columns involved in the integrity constraint.
     */
    public MultiColumnConstraint(String name, Column... columns) {
        this(name, Arrays.asList(columns));
    }    
    
    /**
     * Constructor.
     * @param name A name for the constraint (optional - can be null).
     * @param table The table on which the integrity constraint holds.
     * @param columns The columns involved in the integrity constraint.
     */
    public MultiColumnConstraint(String name, List<Column> columns) {
        super(name);
        setColumns(columns);
    }

    /**
     * Sets the columns involved in the integrity constraint.
     * @param columns The columns involved in the integrity constraint.
     */
    public void setColumns(List<Column> columns) {
        if (columns.size() < 1) {
            throw new SQLRepresentationException("Constraints must be defined over one or more columns");
        }
        
        ListIterator<Column> outerListIterator = columns.listIterator();
        while (outerListIterator.hasNext()) {
            Column first = outerListIterator.next();
            
            ListIterator<Column> innerListIterator = 
                    columns.listIterator(outerListIterator.nextIndex());
            while (innerListIterator.hasNext()) {
                Column second = innerListIterator.next();
                
                if (first.getName().equalsIgnoreCase(second.getName())) {
                    throw new SQLRepresentationException(
                            "Cannot involve a column with the same name (\"" 
                                    + first.getName() + "\") twice in the constraint");
                }
            }
        }
        
        this.columns = new ArrayList<>(columns);
    }
    
    /**
     * Remaps the columns of the constraint to that of another table.
     * @param table The table to remap the columns to.
     */
    @Override
    public void remap(Table table) {
        setColumns(remapColumns(columns, table));
    }
    
    /**
     * Gets the columns involved in the integrity constraint.
     * @return A list of the columns involved in the integrity constraint.
     */
    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
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

    /**
     * Generates a hash code for this instance.
     * @return The hash code.
     */    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
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
		MultiColumnConstraint other = (MultiColumnConstraint) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		return true;
	}
}
