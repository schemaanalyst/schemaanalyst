package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.util.Duplicable;

/**
 * A class that represents data belonging to a series of tables.  
 * @author Phil McMinn
 *
 */

public class Data implements Duplicable<Data> {

	protected Map<Table, List<Row>> data;
	
	/**
	 * Constructor.
	 * @param valueFactory A ValueFactory instance that corresponds to the database type. 
	 */	
	public Data() {
		data = new LinkedHashMap<Table, List<Row>>();
	}
	
	/**
	 * Creates a row of data values for a table.  The data values are initialized to defaults 
	 * as dictated by the ValueFactory. 
	 * @param table The table for which to create the row for.
	 * @return The Row instance created for the table.
	 */
	public Row addRow(Table table, ValueFactory valueFactory) {
		return addRow(table, new Row(table, valueFactory));
	}
	
	/**
	 * Adds a row of data values to a table.
	 * @param table The table to add the row to.
	 * @param row The row to be added.
	 * @return The row that was added.
	 */
	public Row addRow(Table table, Row row) {
		List<Row> tableData = data.get(table);
		
		if (tableData == null) {
			tableData = new ArrayList<Row>();
			data.put(table, tableData);
		}
		
		tableData.add(row);
		return row;
	}	
	
	/**
	 * Creates a number of rows of data values for a table.  The data values are initialized 
	 * to defaults as dictated by the ValueFactory. 
	 * @param table The table for which to create the row for.
	 * @param i The number of rows to add for the table.
	 * @return The Row instance created for the table.
	 */
	public List<Row> addRows(Table table, int numRows, ValueFactory valueFactory) {
		List<Row> rows = new ArrayList<>();
		for (int i=0; i < numRows; i++) {
			Row row = addRow(table, valueFactory);
			rows.add(row);
		}
		return rows;
	}	
	
	/**
	 * Creates a number of rows of data values for different tables.  The data values 
	 * are initialized to defaults as dictated by the ValueFactory. 
	 * @param table The table for which to create the row for.
	 * @param i The number of rows to add for the table.
	 * @return The Row instance created for the table.
	 */
	public Map<Table, List<Row>> addRows(List<Table> tables, int numRows, ValueFactory valueFactory) {
		Map<Table, List<Row>> tableRows = new LinkedHashMap<>();
		for (Table table : tables) {
			List<Row> rows = addRows(table, numRows, valueFactory);
			tableRows.put(table, rows);
		}
		return tableRows;
	}	
	
	/**
	 * Adds a list of rows to a given table.
	 * @param table The table to add the rows to.
	 * @param rows The rows to be added.
	 * @return The rows added.
	 */
	public List<Row> addRows(Table table, List<Row> rows) {
		for (Row row : rows) {
			addRow(table, row);
		}
		return rows;
	}
	
	/**
	 * Gets the tables involved in this data instance.
	 * @return A list of tables involved in this data instance.
	 */
	public List<Table> getTables() {
		List<Table> tables = new ArrayList<Table>();
		tables.addAll(data.keySet());
		return tables;
	}
	
	/**
	 * Returns all the rows for a table in this data instance.
	 * @param table The table to get rows for.
	 * @return The list of rows for the table.
	 */
	
	public List<Row> getRows(Table table) {
		List<Row> rows = new ArrayList<Row>();
		if (data.containsKey(table)) {
			rows.addAll(data.get(table));
		} 
		return rows;
	}
	
	/**
	 * Returns the number of rows for a table in this data instance.
	 * @param table The table whose number of rows are sought.
	 * @return The number of rows for <tt>table</tt>.
	 */
	public int getNumRows(Table table) {
		if (data.containsKey(table)) {
			return data.get(table).size();
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets a specific data cell (a column value for a particular row of a table).
	 * @param table The table containing the cell.
	 * @param column The column whose value is sought.
	 * @param rowNo The row number of the table.
	 * @return The requested cell, or null if the specified cell does not exist.
	 */
	public Cell getCell(Table table, Column column, int rowNo) {
		List<Row> rows = data.get(table);
		
		if (rows.size() > rowNo) {
			Row row = rows.get(rowNo);
			return row.getCell(column);
		}
		
		return null;
	}
	
	/**
	 * Returns all the cells in this data instance, regardless of table, column etc.
	 * @return A list of all the cells in this data instance.
	 */
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		for (Table table : getTables()) {
			for (Row row : getRows(table)){
				cells.addAll(row.getCells());
			}
		}
		return cells;
	}
	
	/**
	 * Returns all the cells in the data instance for a specific column.
	 * @param column The column whose cells are sought.
	 * @return A list of cells for the column.
	 */	
	public List<Cell> getCells(Column column) {
		List<Cell> cells = new ArrayList<Cell>();
		List<Row> rows = getRows(column.getTable());
		
		if (rows != null) {
			for (Row row : rows){
				cells.add(row.getCell(column));
			}
		}

		return cells;
	}
	
	/**
	 * Returns a "vertical slice" of a table -- a list of rows where each row 
	 * contains a subset of cells for specifically-required columns.
	 * @param columns The columns for which cell values are sought.  The columns 
	 * must be from the same table, otherwise a <tt>DataAccessException</tt> is thrown.
	 * @return A list of rows containing only cells for each specified column.
	 */	
	public List<Row> getRows(List<Column> columns) {
		Table table = null;
		
		// Get the table, checking that all the columns are from the same table.
		for (Column column : columns) {
			Table columnTable = column.getTable();
			if (table == null) {
				table = columnTable;
			} else if (!table.equals(columnTable)) {
				throw new DataException(
						"Cannot invoke getRows() with columns from multiple tables (\""
						+ table + "\" and \"" + columnTable + "\")");
			}
		}
		
		// Construct the "vertical slice"
		List<Row> rows = new ArrayList<Row>();
		if (columns.size() > 0) {
			List<Row> tableRows = getRows(table);
			if (tableRows != null) {
				for (Row row : tableRows){
					List<Cell> cells = new ArrayList<Cell>();
					for (Column column : columns) {
						cells.add(row.getCell(column));
					}
					rows.add(new Row(cells));
				}
			}
		}
		
		return rows;		
	}	
	
	/**
	 * Please use getRows(List<Column> columns) instead.
	 */	
	public List<List<Cell>> getCells(List<Column> columns) {
		Table table = null;
		
		// Get the table, checking that all the columns are from the same table.
		for (Column column : columns) {
			Table columnTable = column.getTable();
			if (table == null) {
				table = columnTable;
			} else if (!table.equals(columnTable)) {
				throw new DataException("Cannot invoke getValues() with columns from multiple tables (\""
												+ table + "\" and \"" + columnTable + "\")");
			}
		}
		
		// Construct the "slice"
		List<List<Cell>> cells = new ArrayList<List<Cell>>();
		if (columns.size() > 0) {
			List<Row> rows = getRows(table);
			if (rows != null) {
				for (Row row : getRows(table)){
					List<Cell> rowValues = new ArrayList<Cell>();
					for (Column column : columns) {
						rowValues.add(row.getCell(column));
					}
					cells.add(rowValues);
				}
			}
		}
		
		return cells;		
	}

	
	/**
	 * Copies this data instance.
	 */
	public Data duplicate() {
		Data duplicate = new Data();
		
		// copy the data
		for (Table table : getTables()) {
			List<Row> duplicateRows = new ArrayList<Row>();
			
			List<Row> rows = getRows(table);
			for (Row row : rows) {
				duplicateRows.add(row.duplicate());
			}
			
			duplicate.data.put(table, duplicateRows);
		}
		
		return duplicate;
	}

	
	/**
	 * Returns a string representation of this data instance.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		Set<Table> tables = data.keySet();
		
		boolean first = true;
		
		if (tables != null) {
			for (Table table : tables) {
				List<Row> rows = data.get(table);
				for (Row row : rows) {
					if (first) {
						first = false;
					} else {
						s.append("\n");						
					}
					
					s.append(row.toString());
				}
			}
		}
		return s.toString();
	}
}
