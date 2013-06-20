package org.schemaanalyst.representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract superclass for integrity constrains that potentially involve multiple columns (PrimaryKey, ForeignKey and Unique).
 * @author Phil McMinn
 *
 */

public abstract class MultiColumnConstraint extends Constraint {

	private static final long serialVersionUID = 173815859465417000L;
	
	protected List<Column> columns;

	/**
	 * Constructor.
	 * @param table The table on which the integrity constraint holds.
	 * @param name A name for the constraint (optional - can be null).
	 * @param columns The columns involved in the integrity constraint.
	 */
	protected MultiColumnConstraint(Table table, String name, Column... columns) {
		super(table, name);
		this.columns = new ArrayList<Column>();
		
		for (Column column : columns) {
			if (!table.hasColumn(column)) {
				throw new SchemaConstructionException("Column \""+column+"\" does not exist in table \""+table+"\"");
			}
			this.columns.add(column);
		}
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
	 * @return True if more than one column involved in this constraint, else false.
	 */
	public boolean isMultiColumn() {
		return columns.size() > 1;
	}
}
