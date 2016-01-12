package org.schemaanalyst.data;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.io.Serializable;
import java.util.*;

/**
 * A class that represents data belonging to a series of tables.
 *
 * @author Phil McMinn
 *
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 2897215621399301020L;

    public static class Duplicator implements org.schemaanalyst.util.Duplicator<Data> {
        @Override
        public Data duplicate(Data data) {
            return data.duplicate();
        }
    }
    
    protected Map<Table, List<Row>> data;

    /**
     * Constructor.
     */
    public Data() {
        data = new LinkedHashMap<>();
    }

    /**
     * Appends the data from another data object to this
     * @param other Another data object whose data is to be appended to this instance.
     */
    public void appendData(Data other) {
        for (Table table : other.getTables()) {
            addRows(table, other.getRows(table));
        }
    }

    /**
     * Creates a row of data values for a table. The data values are initialized
     * to defaults as dictated by the ValueFactory.
     * @param table The table for which to create the row for.
     * @param valueFactory A ValueFactory instance appropriate for the current DBMS.
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
            tableData = new ArrayList<>();
            data.put(table, tableData);
        }

        tableData.add(row);
        return row;
    }

    /**
     * Creates a number of rows of data values for a table. The data values are
     * initialized to defaults as dictated by the ValueFactory.
     * @param table The table for which to create the row for.
     * @param numRows The number of rows to add for the table.
     * @param valueFactory A ValueFactory instance appropriate for the current DBMS.
     * @return The Row instance created for the table.
     */
    public List<Row> addRows(Table table, int numRows, ValueFactory valueFactory) {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            Row row = addRow(table, valueFactory);
            rows.add(row);
        }
        return rows;
    }

    /**
     * Creates a number of rows of data values for different tables. The data
     * values are initialized to defaults as dictated by the ValueFactory.
     * @param tables The tables for which to create rows for.
     * @param numRows The number of rows to add for the table.
     * @param valueFactory A ValueFactory instance appropriate for the current DBMS.     * 
     * @return The Row instance created for the table.
     */
    public Map<Table, List<Row>> addRows(
            List<Table> tables, int numRows, ValueFactory valueFactory) {
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
        List<Table> tables = new ArrayList<>();
        tables.addAll(data.keySet());
        return tables;
    }

    /**
     * Returns all the rows for a table in this data instance.
     * @param table The table to get rows for.
     * @return The list of rows for the table.
     */
    public List<Row> getRows(Table table) {
        List<Row> rows = new ArrayList<>();
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
     * Returns the total number of rows in this data instance,
     * for all tables.
     * @return The number of rows in this data instance.
     */
    public int getNumRows() {
        int numRows = 0;
        for (Table table : data.keySet()) {
            numRows += data.get(table).size();
        }
        return numRows;
    }

    /**
     * Gets a specific data cell (a column value for a particular row of a
     * table).
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
     * Returns all the cells in this data instance, regardless of table, column
     * etc.
     * @return A list of all the cells in this data instance.
     */
    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        for (Table table : getTables()) {
            for (Row row : getRows(table)) {
                cells.addAll(row.getCells());
            }
        }
        return cells;
    }

    /**
     * Returns all the cells in the data instance for a specific column.
     * @param table The table of the column whose cells are sought.
     * @param column The column whose cells are sought.
     * @return A list of cells for the column.
     */
    public List<Cell> getCells(Table table, Column column) {
        List<Cell> cells = new ArrayList<>();
        List<Row> rows = getRows(table);

        if (rows != null) {
            for (Row row : rows) {
                cells.add(row.getCell(column));
            }
        }

        return cells;
    }

    /**
     * Returns a "vertical slice" of a table -- a list of rows where each row
     * contains a subset of cells for specifically-required columns.
     * @param table The table of the columns for which cell values are sought.
     * @param columns The columns for which cell values are sought. 
     * @return A list of rows containing only cells for each specified column.
     */
    public List<Row> getRows(Table table, List<Column> columns) {
        List<Row> rows = new ArrayList<>();
        if (columns.size() > 0) {
            List<Row> tableRows = getRows(table);
            if (tableRows != null) {
                for (Row row : tableRows) {
                    List<Cell> cells = new ArrayList<>();
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
     * Returns a "vertical slice" of a table -- a list of rows where each row
     * contains a subset of cells for specifically-required columns.
     * @param table The table of the column for which cell values are sought.
     * @param columns The columns for which cell values are sought. 
     * @return A list of rows containing only cells for each specified column.
     */
    public List<Row> getRows(Table table, Column... columns) {
        return getRows(table, Arrays.asList(columns));
    }

    /**
     * Copies the values from the other data object into this
     */
    public void copyValues(Data source) {

        for (Table table : source.getTables()) {

            List<Row> targetRows = this.getRows(table);
            if (data == null) {
                throw new DataException("Cannot copy data values, as table " + table + " does not exist in target");
            }

            List<Row> sourceRows = source.getRows(table);
            if (targetRows.size() != sourceRows.size()) {
                throw new DataException("Cannot copy data values, as table " + table + " does not have the same number of rows in target");
            }

            Iterator<Row> sourceRowIterator = sourceRows.iterator();
            Iterator<Row> targetRowIterator = targetRows.iterator();

            while (sourceRowIterator.hasNext()) {
                Row sourceRow = sourceRowIterator.next();
                Row targetRow = targetRowIterator.next();

                targetRow.copyValues(sourceRow);
            }
        }
    }

    /**
     * Duplicates this data instance.
     */
    public Data duplicate() {
        Data duplicate = new Data();

        // copy the data
        for (Table table : getTables()) {
            List<Row> duplicateRows = new ArrayList<>();

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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Table> tables = data.keySet();

        if (tables != null) {
            boolean first = true;
            for (Table table : tables) {
                List<Row> rows = data.get(table);
                for (Row row : rows) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(System.lineSeparator());
                    }
                    sb.append("* " + row);
                }
            }
        }
        return sb.toString();
    }
}
