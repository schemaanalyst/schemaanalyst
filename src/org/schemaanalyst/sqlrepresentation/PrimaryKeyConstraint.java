package org.schemaanalyst.sqlrepresentation;

/**
 * Represents the primary key of a table.
 * @author Phil McMinn
 *
 */

public class PrimaryKeyConstraint extends MultiColumnConstraint {

	private static final long serialVersionUID = -3568002542239034859L;

	/**
	 * Constructor.
	 * @param table The table of this primary key.
	 * @param name A name for the primary key constraint (optional - can be null).
	 * @param columns The columns involved in the primary key.
	 */
	protected PrimaryKeyConstraint(Table table, String name, Column... columns) {
		super(table, name, columns);
	}	
		
	/**
	 * Adds a column to the primary key.  The column must be a column of the primary key's table.
	 * @param column The column to add.
	 */
	public void addColumn(Column column) {
		if (!table.hasColumn(column)) {
			throw new SchemaConstructionException("Column \""+column+"\" does not exist in table \""+table+"\"");
		}		
		columns.add(column);
	}	

	/**
	 * Removes a column from the primary key.
	 * @param column The column to remove.
	 */
	public void removeColumn(Column column) {
		columns.remove(column);
	}	
	
	/**
	 * Method for accepting visitors of type IntegrityConstraintVisitor.
	 * @param visitor The IntegrityConstraintVisitor instance visiting this constraint. 
	 */	
	public void accept(ConstraintVisitor visitor) {
		visitor.visit(this);
	}	
	
	/**
	 * Copies the primary key to another table, which must have columns of the same name.
	 * @param targetTable The table to copy the primary key to.
	 * @return The copy of the PrimaryKey instance created as a result of calling the method.
	 */
	public PrimaryKeyConstraint copyTo(Table targetTable) {
		PrimaryKeyConstraint copy = new PrimaryKeyConstraint(targetTable, this.name);
		
		// copy columns, but mapped to those of the new table
		for (Column column : this.columns) {
			Column targetTableColumn = targetTable.getColumn(column.getName()); 
			
			if (targetTableColumn == null) {
				throw new SchemaConstructionException("Cannot copy PrimaryKey to table \"" + targetTable + 
						  				  "\" as it does not have the column \"" + column + "\"");				
			}
			
			copy.columns.add(targetTableColumn);
		}
		
		targetTable.setPrimaryKeyConstraint(copy);
		return copy;
	}
	
	/**
	 *  Checks whether this PrimaryKey instance is equal to another object.
	 *  The comparison compares columns only and ignores the constraint's name.
	 *  @param obj The object to compare this instance with.
	 *  @return True if the other object is a PrimaryKey object with the same columns, else false.
	 */		
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
		
		PrimaryKeyConstraint other = (PrimaryKeyConstraint) obj;
		
		if (columns == null) {
			if (other.columns != null) {
				return false;
			}
		} else if (!columns.equals(other.columns)) {
			return false;
		}			
		
		return true;
	}	
	
	/**
	 * Returns an informative string about the foreign key instance.
	 * @return An informative string.
	 */	
	public String toString() {
		return "PRIMARY KEY" + columns.toString();
	}	
}
