package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
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
     *
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
     *
     * @param columns The columns involved in the integrity constraint.
     */
    public void setColumns(List<Column> columns) {
        if (columns.size() < 1) {
            throw new SQLRepresentationException("Constraints must be defined over one or more columns");
        }
        this.columns = new ArrayList<>();
    }

    /**
     * Gets the columns involved in the integrity constraint.
     *
     * @return A list of the columns involved in the integrity constraint.
     */
    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Checks if a column is involved in the integrity constraint or not.
     *
     * @param column The column whose status is to be checked.
     * @return True if the column is involved, else false.
     */
    public boolean involvesColumn(Column column) {
        return columns.contains(column);
    }

    /**
     * Gets the number of columns involved in the integrity constraint.
     *
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
