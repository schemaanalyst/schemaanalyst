package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

public class Row {

    public static class Duplicator implements org.schemaanalyst.util.Duplicator<Row> {
        @Override
        public Row duplicate(Row row) {
            return row.duplicate();
        }
    }

    protected Table table;
    protected List<Cell> cells;

    protected Row() {
        table = null;
        cells = new ArrayList<>();
    }

    public Row(Table table, ValueFactory valueFactory) {
        this();

        this.table = table;
        for (Column column : table.getColumns()) {
            Cell cell = new Cell(column, valueFactory);
            cells.add(cell);
        }
    }

    public Row(List<Cell> cells) {
        this();

        for (Cell cell : cells) {
            this.cells.add(cell);
        }
    }
    
    public Row(Cell... cells) {
        this(Arrays.asList(cells));
    }

    public int getNumCells() {
        return cells.size();
    }

    public Row reduceRow(List<Column> columns) {
        return new Row(getCells(columns));
    }

    public Table getTable() {
        return table;
    }

    public List<Cell> getCells() {
        return new ArrayList<>(cells);
    }

    public List<Cell> getCells(List<Column> columns) {
        List<Cell> cells = new ArrayList<>();
        for (Column column : columns) {
            cells.add(getCell(column));
        }
        return cells;
    }

    public Cell getCell(Column column) {
        for (Cell cell : cells) {
            if (column.equals(cell.getColumn())) {
                return cell;
            }
        }

        throw new DataException("Unknown column \"" + column + "\"");
    }

    public boolean hasColumn(Column column) {
        for (Cell cell : cells) {
            if (column.equals(cell.getColumn())) {
                return true;
            }
        }
        return false;
    }

    public void copyValues(Row other) {
        Iterator<Cell> thisIterator = cells.iterator();
        Iterator<Cell> otherIterator = other.cells.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            Cell thisCell = thisIterator.next();
            Cell otherCell = otherIterator.next();

            Value value = otherCell.getValue().duplicate();
            thisCell.setValue(value);
        }
    }

    public Row duplicate() {
        Row duplicate = new Row();
        duplicate.table = this.table;
        for (Cell cell : this.cells) {
            duplicate.cells.add(cell.duplicate());
        }
        return duplicate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(table);
        sb.append(": ");
		sb.append(StringUtils.join(cells, ", "));
        return sb.toString();
    }
}
