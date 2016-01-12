package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

public class ColumnExpression extends ExpressionLeaf {

    private Table table;
    private Column column;
    
    public ColumnExpression(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    public Column getColumn() {
        return column;
    }
    
    public void setColumn(Column column) {
        this.column = column;
    }

    @Override
    public List<Column> getColumnsInvolved() {
        List<Column> columns = new ArrayList<>();
        columns.add(column);
        return columns;
    }    
    
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public ColumnExpression duplicate() {
        return new ColumnExpression(table, column);
    }
    
    @Override
    public void remap(Table table) {
        // only remap if the tables are equal, the intention
        // is that this method is used following duplication of
    	// the artefact in which the expression resides (to
        // sort out references) -- NOT to change the expression's
    	// table.
        if (table.equals(this.table)) {    	
	        this.table = table;	        
	        this.column = table.getColumn(column.getName());
        }
    }    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        result = prime * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnExpression other = (ColumnExpression) obj;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        if (table == null) {
            if (other.table != null)
                return false;
        } else if (!table.equals(other.table))
            return false;
        return true;
    }

    public String toString() {
        return column.toString();
    }
}
