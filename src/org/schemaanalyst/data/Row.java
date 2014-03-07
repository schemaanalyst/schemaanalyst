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

    public Row(Table table, ValueFactory valueFactory) {
        this.table = table;
        this.cells = new ArrayList<>();
        for (Column column : table.getColumns()) {
            Cell cell = new Cell(column, valueFactory);
            this.cells.add(cell);
        }
    }

    public Row(Table table, List<Cell> cells) {
        this.table = table;
        this.cells = new ArrayList<>();
        for (Cell cell : cells) {
            this.cells.add(cell);
        }
    }

    public Row(List<Cell> cells) {
        this.cells = new ArrayList<>();
        for (Cell cell : cells) {
            this.cells.add(cell);
        }
    }


    public int getNumCells() {
        return cells.size();
    }

    public Row reduceRow(List<Column> columns) {
        return new Row(table, getCells(columns));
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
        List<Cell> duplicateCells = new ArrayList<>();
        for (Cell cell : this.cells) {
            duplicateCells.add(cell.duplicate());
        }
        return new Row(table, duplicateCells);
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
