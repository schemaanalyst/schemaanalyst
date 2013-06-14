package org.schemaanalyst.representation;

import java.io.Serializable;

import org.schemaanalyst.representation.datatype.DataType;
import org.schemaanalyst.representation.expression.Operand;
import org.schemaanalyst.representation.expression.OperandVisitor;

/**
 * Represents a table column in a database schema.  Table columns are constructed by calling 
 * the <tt>createColumn</tt> method of Table.
 * @author Phil McMinn
 *
 */
public class Column implements Serializable, Operand {

	private static final long serialVersionUID = -2680046452756410766L;
	
	protected Table table;		
	protected String name;
	protected DataType type;
	
	/**
	 * Constructor.
	 * @param table The table to which this column belongs.
	 * @param name The name of the column.
	 * @param type The type of the column (see the types sub-package).
	 */
	protected Column(Table table, String name, DataType type) {
		this.table = table;
		this.name = name;
		this.type = type;
	}	
	
	/** 
	 * Returns the name of the column.
	 * @return The name of the column.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the column's table.
	 * @return The table of the column.
	 */
	public Table getTable() {
		return table;
	}
	
	/**
	 * Returns the type of the column (for example VarChar).
	 * @return The type of the column.
	 */
	public DataType getType() {
		return type;
	}
	
	/**
	 * Changes the type of the column.
	 * @param type The new type of the column.
	 */
	public void setType(DataType type) {
		this.type = type;
	}
	
	/**
	 * Sets the column as having a not null constraint.
	 */
	public void setNotNull() {
		table.addNotNullConstraint(this);
	}
		
	/**
	 * Returns whether or not the column has a not null constraint on it.
	 * @return True if the column has a not null constraint on it, else false.
	 */
	public boolean isNotNull() {
		return table.isNotNull(this);
	}
	
	/**
	 * Sets the column as being unique.
	 */
	public void setUnique() {
		table.addUniqueConstraint(this);
	}
	
	/**
	 * Sets the column as being the primary key of its table.
	 */
	public void setPrimaryKey() {
		table.setPrimaryKeyConstraint(this);
	}
	
	/**
	 * Sets the column as being the subject of a primary key.
	 * @param referenceTable The table which the foreign key references (that is, the table that contains the reference column).
	 * @param referenceColumn The reference column with which the primary key is paired with in another table (the reference table).
	 */
	public void setForeignKey(Table referenceTable, Column referenceColumn) {
		table.addForeignKeyConstraint(referenceTable, this, referenceColumn);
	}
		
	/**
	 * Accepts an operand visitor on this column.
	 * @param operandVisitor The operand visitor to accept.
	 */
	public void accept(OperandVisitor operandVisitor) {
		operandVisitor.visit(this);
	}		
	
	/**
	 * Checks whether the current instance is equal to another object.
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
		
		Column other = (Column) obj;
		return this.getFullyQualifiedName().equals(other.getFullyQualifiedName());
	}		
	
	/**
	 * Gets the fully qualified name of the column.  The form is "<tt>schema_name.table_name.column_name</tt>".
	 * @return The fully qualified name.
	 */
	public String getFullyQualifiedName() {
		return table.getFullyQualifiedName() + "." + name;
	}	
	
	/**
	 * Returns the name of the column.
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Copies the column to another table.  Please note that attributes are not deep-copied yet.
	 * @param table The table to copy the column to/
	 * @return The copied column.
	 */
	// Note that type and attributes are only shallow copied
	public Column copyTo(Table table) {
		return table.addColumn(this.name, this.type);
	}
}
