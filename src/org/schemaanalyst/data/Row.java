package org.schemaanalyst.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.util.Duplicable;

public class Row implements Duplicable<Row>{
	
	protected Table table;
	protected List<Cell> cells;
	
	protected Row() {
		table = null;
		cells = new ArrayList<Cell>();
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
	
	public Table getTable() {
		return table;
	}

	public List<Cell> getCells() {
		return Collections.unmodifiableList(cells);
	}
	
	public Cell getCell(Column column) {
		for (Cell cell : cells) {
			if (column.equals(cell.getColumn())) {
				return cell;
			}
		}
		
		throw new DataException("Unknown column \""+column+"\"");
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
	
	public Boolean valuesEqual3VL(Row other) {
		if (cells.size() != other.cells.size()) {
			return Boolean.FALSE;
		}
		
		Iterator<Cell> thisIterator = this.cells.iterator();
		Iterator<Cell> otherIterator = other.cells.iterator();
		
		Boolean rowsEqual = Boolean.TRUE;

		while (thisIterator.hasNext() && otherIterator.hasNext()) {
			Cell thisRowCell = thisIterator.next();
			Cell otherRowCell = otherIterator.next();
						
			Boolean valuesEqual = thisRowCell.valuesEqual3VL(otherRowCell);
			
			if (Boolean.FALSE.equals(valuesEqual)) {
				rowsEqual = Boolean.FALSE;
			} 
			
			// if there's a null cell the overall result
			// is decided as unknown -- i.e. null
			if (valuesEqual == null) {
				return null;
			}
		}
		
		return rowsEqual;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(table);
		s.append(": ");
		boolean first = true;
		for (Cell cell : cells) {
			if (first) {
				first = false;
			} else {
				s.append(", ");
			}			
			s.append(cell.toString());
		}	
		return s.toString();
	}
}
