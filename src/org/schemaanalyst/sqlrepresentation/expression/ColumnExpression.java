package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
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
    
    public String toString() {
        return column.toString();
    }
}
