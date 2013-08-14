package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TConstraintList;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TObjectNameList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.StringUtils;

import static org.schemaanalyst.sqlparser.QuoteStripper.stripQuotes;

public class ConstraintMapper {
    
    private final static Logger LOGGER = Logger.getLogger(ConstraintMapper.class.getName());
    
    protected ExpressionMapper expressionMapper;

    public ConstraintMapper(ExpressionMapper expressionMapper) {
        this.expressionMapper = expressionMapper;        
    }

    public void analyseConstraintList(Table currentTable, Column currentColumn, TConstraintList node) {
        if (node != null) {
            for (int i = 0; i < node.size(); i++) {
                mapConstraint(currentTable, currentColumn, node.getConstraint(i));
            }
        }
    }

    public void mapConstraint(Table currentTable, Column currentColumn, TConstraint node) {

        switch (node.getConstraint_type()) {
            case check:
                addCheckConstraint(
                        currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getCheckCondition());
                break;
            case foreign_key:
            case reference:
                addForeignKeyConstraint(
                        currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList(),
                        node.getReferencedObject(),
                        node.getReferencedColumnList());
                break;
            case notnull:
                addNotNullConstraint(
                        currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList());
                break;
            case primary_key:
                setPrimaryKeyConstraint(
                        currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList());
                break;
            case unique:
                addUniqueConstraint(
                        currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList());
                break;
            case fake_auto_increment:
                LOGGER.warning("Ignoring AUTO_INCREMENT -- \"" + node + "\" " + node.getLineNo());
                break;
            case default_value:
                LOGGER.warning("Ignoring DEFAULT -- \"" + node + "\" " + node.getLineNo());
                break;
            case key:
                LOGGER.warning("Ignoring KEY -- \"" + node + "\" " + node.getLineNo());
                break;
            default:
                throw new UnsupportedSQLException(node);
        }
    }

    protected void addCheckConstraint(
            Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TExpression expressionNode) {

        String constraintName = stripQuotes(constraintNameObject);
        Expression expression = expressionMapper.getExpression(currentTable, expressionNode);
        currentTable.addCheckConstraint(constraintName, expression);
    }

    protected void addForeignKeyConstraint(
            Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList,
            TObjectName referenceTableNameObject, TObjectNameList referenceColumnNameObjectList) {
    	
    	String constraintName = stripQuotes(constraintNameObject);
        String referenceTableName = stripQuotes(referenceTableNameObject);
        Table referenceTable = currentTable.getSchema().getTable(referenceTableName);
        
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        List<Column> referenceColumns = mapColumns(referenceTable, null, referenceColumnNameObjectList);

        if (referenceColumns.size() == 0) {
            // no reference columns we have to map those of the primary key in the reference table by default
        	referenceColumns = referenceTable.getPrimaryKeyConstraint().getColumns();
        }

        LOGGER.log(Level.INFO, "Attempting to create FOREIGN KEY on {0} ({1}) to {2} ({3})", 
        		new Object[]{currentTable, StringUtils.implode(columns, ", "), 
        					 referenceTable, StringUtils.implode(referenceColumns, ", ")});
        
        currentTable.addForeignKeyConstraint(constraintName, columns, referenceTable, referenceColumns);
        
        LOGGER.log(Level.INFO, "-- success");
    }

    protected void addNotNullConstraint(
            Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        Column[] columns = mapColumns(currentTable, currentColumn, columnNameObjectList).toArray(new Column[0]);
        currentTable.addNotNullConstraint(constraintName, columns[0]);
    }

    protected void setPrimaryKeyConstraint(
            Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        currentTable.setPrimaryKeyConstraint(constraintName, columns);
    }

    protected void addUniqueConstraint(
            Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        currentTable.addUniqueConstraint(constraintName, columns);
    }

    protected List<Column> mapColumns(Table currentTable, Column currentColumn, TObjectNameList columnNameObjectList) {
        List<Column> columns = new ArrayList<>();

        if (currentColumn != null) {
            columns.add(currentColumn);
        } else if (columnNameObjectList != null) {
            for (int i = 0; i < columnNameObjectList.size(); i++) {
                String columnName = stripQuotes(columnNameObjectList.getObjectName(i));
                Column column = currentTable.getColumn(columnName);
                columns.add(column);
            }
        }

        return columns;
    }
}
