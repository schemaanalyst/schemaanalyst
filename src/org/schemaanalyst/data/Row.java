package org.schemaanalyst.data;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Row implements Serializable {

    private static final long serialVersionUID = 6959607990184236243L;

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

    public Row(Cell... cells) {
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

    public Cell getCell(int index) {
        return cells.get(index);
    }

    public boolean hasColumn(Column column) {
        for (Cell cell : cells) {
            if (column.equals(cell.getColumn())) {
                return true;
            }
        }
        return false;
    }

    public void copyValues(Row source) {
        for (Cell sourceCell : source.getCells()) {
            Column column = sourceCell.getColumn();
            Cell targetCell = getCell(column);
            if (targetCell == null) {
                throw new DataException("Cannot copy cell value as column " + column + " does not exist in target");
            }
            Value sourceValue = sourceCell.getValue();
            Value duplicatedValue = (sourceValue == null) ? null : sourceValue.duplicate();
            targetCell.setValue(duplicatedValue);
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
