package org.schemaanalyst.representation;

public class UniqueConstraint extends MultiColumnConstraint {

	private static final long serialVersionUID = -3188129142878269469L;
	
	/**
	 * Constructor.
	 * @param table The table of this unique constraint.
	 * @param name A name for the unique constraint (optional - can be null).
	 * @param columns The columns involved in the unique constraint.
	 */	
	protected UniqueConstraint(Table table, String name, Column... columns) {
		super(table, name, columns);
	}
	
	/**
	 * Adds a column to the unique constraint.  The column must be a column of the primary key's table.
	 * @param column The column to add.
	 */
	public void addColumn(Column column) {
		if (!table.hasColumn(column)) {
			throw new SchemaConstructionException("Column \""+column+"\" does not exist in table \""+table+"\"");
		}		
		columns.add(column);
	}	

	/**
	 * Removes a column from the unique constraint.
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
	 * Copies the unique constraint to another table, which must have columns of the same name.
	 * @param targetTable The table to copy the unique constraint to.
	 * @return The copy of the Unique instance created as a result of calling the method.
	 */	
	public UniqueConstraint copyTo(Table targetTable) {
		UniqueConstraint copy = new UniqueConstraint(targetTable, this.name);

		// copy columns, but mapped to those of the new table
		for (Column column : this.columns) {
			Column tableColumn = targetTable.getColumn(column.getName()); 
			
			if (tableColumn == null) {
				throw new SchemaConstructionException("Cannot copy ForeignKey to table " + targetTable + 
						  				  " as it does not hve the column " + column);				
			}			
			copy.columns.add(tableColumn);
		}		
		
		targetTable.addUniqueConstraint(copy);
		return copy;
	}
	
	/**
	 *  Checks whether this Unique instance is equal to another object.
	 *  The comparison compares columns only and ignores the constraint's name.
	 *  @param obj The object to compare this instance with
	 *  @return True if the other object is a Unique object with the same columns, else false.
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
		
		UniqueConstraint other = (UniqueConstraint) obj;
		
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
		return "UNIQUE" + columns.toString();
	}	
}


