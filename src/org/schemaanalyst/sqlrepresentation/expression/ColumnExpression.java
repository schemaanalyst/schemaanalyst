package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;

public class ColumnExpression extends ExpressionLeaf {

    private Table table;
    private Column column;

    public ColumnExpression(Column column) {
        this(null, column);
    }
    
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
        // only remap if the table names are the same, the intention
        // is that this method is used following a duplication to
        // sort out references.
        if (table.getName().equalsIgnoreCase(this.table.getName())) {
            // change the table
            this.table = table;
            
            // change the column
            Column counterpartColumn = table.getColumn(column.getName());
            if (!column.equals(counterpartColumn)) {
                throw new SQLRepresentationException(
                        "Cannot remap column \"" + column 
                        + "\" - an identical column does not exist in table \""
                        + table + "\"");                
            }            
            column = counterpartColumn;
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
        // Avoid a full table comparison due to the possibility of an infinite recursion    
        } else if (!table.getName().equalsIgnoreCase(other.table.getName()))
            return false;
        return true;
    }

    public String toString() {
        return column.toString();
    }
}
