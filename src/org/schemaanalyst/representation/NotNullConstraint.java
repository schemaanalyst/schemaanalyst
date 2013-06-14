package org.schemaanalyst.representation;

/**
 * Represents a NOT NULL integrity constraint.
 * @author Phil McMinn
 *
 */
public class NotNullConstraint extends Constraint {

	private static final long serialVersionUID = 26638174760035819L;
	
	protected Column column;
	
	/**
	 * Constructor.
	 * @param table The table containing the column on which the NOT NULL constraint holds.
	 * @param name A name for the constraint (optional - can be null).
	 * @param column The column that is designated to be NOT NULL.
	 */
	protected NotNullConstraint(Table table, String name, Column column) {
		super(table, name);
		if (!table.hasColumn(column)) {
			throw new SchemaConstructionException("Column \""+column+"\" does not exist in table \""+table+"\"");
		}
		this.column = column;
	}	
		
	/**
	 * Gets the column which is designated as NOT NULL.
	 * @return The column that is the subject of the NOT NULL constraint.
	 */
	public Column getColumn() {
		return column;
	}	
	
	/**
	 * Method for accepting visitors of type IntegrityConstraintVisitor.
	 * @param visitor The IntegrityConstraintVisitor instance visiting this constraint. 
	 */	
	public void accept(ConstraintVisitor visitor) {
		visitor.visit(this);
	}	

	/**
	 * Copies the constraint to another table.
	 * @param targetTable The table to copy the constraint to (must have a column with the same name).
	 * @return The NotNull copy created in the specified table.
	 */	
	public NotNullConstraint copyTo(Table targetTable) {
		// remap the column to that of the new table
		Column targetTableColumn = targetTable.getColumn(column.getName());
		
		if (targetTableColumn == null) {
			throw new SchemaConstructionException("Cannot copy NotNull to table " + targetTable + 
					  				  " as it does not hve the column " + column);
		}
		
		NotNullConstraint copy = new NotNullConstraint(targetTable, this.name, targetTableColumn);
		targetTable.addNotNullConstraint(copy);
		return copy;
	}
	
	/**
	 *  Checks whether this NotNull instance is equal to another object.
	 *  The comparison compares the column only and ignores the constraint's name.
	 * 
	 *  @param obj The object to compare this instance with.
	 *  @return True if the other object is a NotNull object with the same column, else false.
	 */		
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		NotNullConstraint other = (NotNullConstraint) obj;
		if (column == null) {
			if (other.column != null) {
				return false;
			}
		} else if (!column.equals(other.column)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns an informative string about the foreign key instance.
	 * @return An informative string.
	 */	
	public String toString() {
		return "NOT NULL(" + column + ")";
	}	
}
