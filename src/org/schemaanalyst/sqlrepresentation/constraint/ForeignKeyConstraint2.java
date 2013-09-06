package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.util.Pair;

public class ForeignKeyConstraint2 extends Constraint {

	private Table table, referenceTable;
	private List<Column> columns, referenceColumns;
	
    /**
     * Constructor.
     * 
     * @param table
     *            The source table on which the FOREIGN KEY is defined.
     * @param name
     *            A name for the constraint (can be null).
     * @param columns
     *            Columns over which the foreign key is defined
     * @param referenceTable
     *            The table containing the columns that the FOREIGN KEY
     *            reference.
     * @param referenceColumns
     *            Columns in the reference table paired with each column in
     *            columns
     */
    public ForeignKeyConstraint2(String name, Table table,
            List<Column> columns, Table referenceTable,
            List<Column> referenceColumns) {
        super(name, table);
        this.referenceTable = referenceTable;
        setColumns(columns, referenceColumns);
    }
    
    public void setColumns(List<Column> columns, List<Column> referenceColumns) {
    	if (columns.size() != referenceColumns.size()) {
    		throw new SQLRepresentationException("...");
    	}
    	
    	this.columns = new ArrayList<>();
    	this.referenceColumns = new ArrayList<>();
    	Iterator<Column> columnsIterator = columns.iterator();
    	Iterator<Column> referenceColumnsIterator = referenceColumns.iterator();
    	
    	while (columnsIterator.hasNext() && referenceColumnsIterator.hasNext()) {
    		Column column = columnsIterator.next();
    		Column referenceColumn = referenceColumnsIterator.next();
    		
    		if (!table.hasColumn(column)) {
    			throw new SQLRepresentationException("...");
    		}
    		
    		if (!referenceTable.hasColumn(referenceColumn)) {
    			throw new SQLRepresentationException("...");
    		}
    		
    		if (hasColumnPair(column, referenceColumn)) {
    			throw new SQLRepresentationException("...");
    		}
    		
    		this.columns.add(column);
			this.referenceColumns.add(column);    		
    	}
    }

    public boolean hasColumnPair(Column column, Column referenceColumn) {
    	Iterator<Column> columnsIterator = columns.iterator();
    	Iterator<Column> referenceColumnsIterator = referenceColumns.iterator();
    	
    	while (columnsIterator.hasNext() && referenceColumnsIterator.hasNext()) {
    		Column currentColumn = columnsIterator.next();
    		Column currentReferenceColumn = referenceColumnsIterator.next();

    		if (column.equals(currentColumn) && referenceColumn.equals(currentReferenceColumn)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
	@Override
	public ForeignKeyConstraint2 duplicate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime
				* result
				+ ((referenceColumns == null) ? 0 : referenceColumns.hashCode());
		result = prime * result
				+ ((referenceTable == null) ? 0 : referenceTable.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
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
		ForeignKeyConstraint2 other = (ForeignKeyConstraint2) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (referenceColumns == null) {
			if (other.referenceColumns != null)
				return false;
		} else if (!referenceColumns.equals(other.referenceColumns))
			return false;
		if (referenceTable == null) {
			if (other.referenceTable != null)
				return false;
		} else if (!referenceTable.equals(other.referenceTable))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	@Override
	public void accept(ConstraintVisitor visitor) {
		// TODO Auto-generated method stub
		
	}	
	
}
