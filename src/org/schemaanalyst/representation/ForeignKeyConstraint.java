package org.schemaanalyst.representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents foreign key constraints.
 * @author Phil McMinn
 *
 */

public class ForeignKeyConstraint extends MultiColumnConstraint {

	private static final long serialVersionUID = 373214484205495500L;
	
	protected Table referenceTable;
	protected List<Column> referenceColumns;	
	
	/**
	 * Constructor.
	 * @param table The table containing the foreign key(s).
	 * @param name A name for the constraint (can be null).
	 * @param referenceTable The table containing the columns that the foreign keys reference.
	 * @param columns The columns involved.  The first <tt>n</tt> columns at indexes <tt>0..n-1</tt> should be from <tt>table</tt> and 
	 * the second <tt>n</tt> columns their pairs in <tt>referenceTable</tt> in order from indexes <tt>n..(n*2)-1.</tt> 
	 */
	protected ForeignKeyConstraint(Table table, String name, Table referenceTable, Column... columns) {
		super(table, name);
		this.referenceTable = referenceTable;
		this.referenceColumns = new ArrayList<Column>();			
		
		int midPoint = columns.length / 2;
		for (int i=0; i < columns.length; i++) {
			Column column = columns[i];
			
			if (i < midPoint) {				
				if (!table.hasColumn(column)) {
					throw new SchemaException("Column \"" + column + "\" does not exist in table \"" + table + "\"");					
				}
				this.columns.add(column);
			} else {
				if (!referenceTable.hasColumn(column)) {
					throw new SchemaException("Column \"" + column + "\" does not exist in table \"" + referenceTable + "\"");					
				}
				this.referenceColumns.add(column);
			}			
		}
		
		if (this.columns.size() != this.referenceColumns.size()) {
			throw new SchemaException("Mismatched number of foreign key columns and reference columns");
		}
	}
	
	/**
	 * Adds a foreign key column / reference column pair to the foreign key constraint.
	 * @param column The foreign key column to add from the instance's table.
	 * @param referenceColumn The referencing column to add from the reference table.
	 */
	public void addColumnPair(Column column, Column referenceColumn) {
		if (!table.hasColumn(column)) {
			throw new SchemaException("Column \"" + column + "\" does not exist in table \"" + table + "\"");
		}
		if (!referenceTable.hasColumn(column)) {
			throw new SchemaException("Column \"" + column + "\" does not exist in table \"" + referenceTable + "\"");					
		}
		columns.add(column);
		referenceColumns.add(referenceColumn);
	}	

	/** 
	 * Removes a column from the foreign key, and its pairing reference column in the reference table.  Only the 
	 * column from the foreign key table needs to be specified, however.
	 * @param column The column of the foreign key table. 
	 */
	public void removeColumnPair(Column column) {
		int index = -1;
		for (int i=0; i < columns.size(); i++) {
			if (column.equals(columns.get(i))) {
				index = i;
			}
		}
		if (index != -1) {
			columns.remove(index);
			referenceColumns.remove(index);
		}
	}	
	
	/**
	 * Gets the reference columns of the foreign key.
	 * @return A list containing the reference columns of the foreign key.
	 */
	public List<Column> getReferenceColumns() {
		return Collections.unmodifiableList(referenceColumns);
	}	
		
	/**
	 * Gets the reference table of the foreign key.
	 * @return The reference table instance.
	 */
	public Table getReferenceTable() {
		return referenceTable;
	}

	/**
	 * Method for accepting visitors of type IntegrityConstraintVisitor.
	 * @param visitor The IntegrityConstraintVisitor instance visiting this constraint. 
	 */
	public void accept(ConstraintVisitor visitor) {
		visitor.visit(this);
	}	
	
	/**
	 * Copies the foreign key to another table.  The table must have the same column names as this instance's table.
	 * If the table is from another schema, the schema must have another table with the same name as the reference table
	 * with the same reference column names. 
	 * @param targetTable The table to copy the foreign key to.
	 * @return The instance of the ForeignKey copy created.
	 */
	public ForeignKeyConstraint copyTo(Table targetTable) {
		// map the reference table to that of the schema of the new table		
		Table targetReferenceTable = targetTable.getSchema().getTable(this.referenceTable.getName());
		if (targetReferenceTable == null) {
			throw new SchemaException("Cannot copy ForeignKey to table \"" + targetTable + "\" " + 
					  				  "as its schema does not have the reference table \"" + referenceTable + "\"");

		}		
		
		// create the copy
		ForeignKeyConstraint copy = new ForeignKeyConstraint(targetTable, name, targetReferenceTable);

		// copy columns, but mapped to those of the new table
		for (Column column : this.columns) {
			Column targetTableColumn = targetTable.getColumn(column.getName());
			
			if (targetTableColumn == null) {
				throw new SchemaException("Cannot copy ForeignKey to table \"" + targetTable + "\" " +
										  "as it does not have the column \"" + column + "\"");
			}
			
			copy.columns.add(targetTableColumn);
		}		
		
		
		// copy reference columns, but mapped to those of the new reference table
		for (Column column : this.referenceColumns) {
			Column targetReferenceTableColumn = copy.referenceTable.getColumn(column.getName()); 
			
			if (targetReferenceTableColumn == null) {
				throw new SchemaException("Cannot copy ForeignKey to table \"" + targetTable + "\" " + 
										  "as its reference table \"" + copy.referenceTable + "\" " +
										  "does not hve the column \"" + targetReferenceTableColumn + "\"");
			}
			
			copy.referenceColumns.add(targetReferenceTableColumn);
		}
		
		targetTable.addForeignKeyConstraint(copy);	
		
		return copy;
	}

	/**
	 *  Checks whether this ForeignKey instance is equal to another object.
	 *  The comparison compares columns and reference table only and ignores the constraint's name.
	 *  @param obj The object to compare this instance with.
	 *  @return True if the other object is a ForeignKey object with the same columns and reference table, else false.
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
		
		ForeignKeyConstraint other = (ForeignKeyConstraint) obj;
		
		if (columns == null) {
			if (other.columns != null) {
				return false;
			}
		} else if (!columns.equals(other.columns)) {
			return false;
		}		
		
		if (referenceTable == null) {
			if (other.referenceTable != null) {
				return false;
			}
		} else if (!referenceTable.equals(other.referenceTable)) {
			return false;
		}
		
		if (referenceColumns == null) {
			if (other.referenceColumns != null) {
				return false;
			}
		} else if (!referenceColumns.equals(other.referenceColumns)) {
			return false;
		}		
		
		return true;
	}
	
	/**
	 * Returns an informative string about the foreign key instance.
	 * @return An informative string.
	 */
	public String toString() {
		return "FOREIGN KEY" + columns.toString();
	}		
}
