package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.util.collection.IdentifiableEntity;
import org.schemaanalyst.util.collection.IdentifiableEntitySet;

import java.util.List;

/**
 * Represents a database table.
 * 
 * @author Phil McMinn
 */
public class Table extends IdentifiableEntity {

	private static final long serialVersionUID = 781185006248617033L;
	private IdentifiableEntitySet<Column> columns;

	/**
	 * Constructs a Table.
	 * 
	 * @param name
	 *            The name of the schema.
	 */
	public Table(String name) {
		if (name == null) {
			throw new SQLRepresentationException("Table names cannot be null");
		}
		setName(name);
		columns = new IdentifiableEntitySet<>();
	}

	/**
	 * Sets the name of the table. If the table belongs to a schema, and there
	 * is a name-clash with another table, an {@link SQLRepresentationException}
	 * will be thrown.
	 * 
	 * @param name
	 *            The name of the table.
	 */
	public void setName(String name) {
		super.setName(name);
		if (!getName().equals(name)) {
			throw new SQLRepresentationException("Cannot rename table to \""
					+ name
					+ "\" as another table in the schema has the same name");
		}
	}

	/**
	 * Creates a column and adds it to the table
	 * 
	 * @param name
	 *            The name of the column.
	 * @param dataType
	 *            The data type of the column.
	 */
	public Column createColumn(String name, DataType dataType) {
		Column column = new Column(name, dataType);
		addColumn(column);
		return column;
	}

	/**
	 * Adds a column to the table
	 * 
	 * @param column
	 *            The column to be added.
	 */
	public void addColumn(Column column) {
		if (!columns.add(column)) {
			throw new SQLRepresentationException("Table \"" + getName()
					+ "\" already has a column named \"" + column + "\"");
		}
	}

	/**
	 * Gets a reference to one of the table's columns by its name (ignoring
	 * case).
	 * 
	 * @param columnName
	 *            The name of the column.
	 * @return The column, or null if a column wasn't found for the name given.
	 */
	public Column getColumn(String columnName) {
		String caseInsensitiveName = columnName.toLowerCase();
		return columns.get(caseInsensitiveName);
	}

	/**
	 * Returns whether a column is present in a table or not
	 * 
	 * @param column
	 *            The column.
	 * @return True if the column is present in the table, else false.
	 */
	public boolean hasColumn(Column column) {
		return hasColumn(column.getName());
	}

	/**
	 * Returns whether a column is present in a table or not.
	 * 
	 * @param columnName
	 *            The column name.
	 * @return True if the column is present in the table, else false.
	 */
	public boolean hasColumn(String columnName) {
		return getColumn(columnName) != null;
	}

	/**
	 * Retrieves the list of columns associated with this table, in the order
	 * they were created.
	 * 
	 * @return A list of the table's columns.
	 */
	public List<Column> getColumns() {
		return columns.toList();
	}

	/**
	 * Returns a duplicated version of this table.
	 * 
	 * @return The duplicate.
	 */
	public Table duplicate() {
		Table duplicate = new Table(getName());

		// columns
		for (Column column : columns) {
			duplicate.addColumn(column.duplicate());
		}

		return duplicate;
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
		Table other = (Table) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		return true;
	}
}
