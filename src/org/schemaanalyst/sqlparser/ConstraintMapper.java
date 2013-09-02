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
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.StringUtils;

import static org.schemaanalyst.sqlparser.QuoteStripper.stripQuotes;

class ConstraintMapper {
    
    private final static Logger LOGGER = Logger.getLogger(ConstraintMapper.class.getName());
    
    private ExpressionMapper expressionMapper;

    ConstraintMapper(ExpressionMapper expressionMapper) {
        this.expressionMapper = expressionMapper;        
    }

    void analyseConstraintList(
            Schema schema, Table currentTable, Column currentColumn, TConstraintList node) {
        if (node != null) {
            for (int i = 0; i < node.size(); i++) {
                mapConstraint(schema, currentTable, currentColumn, node.getConstraint(i));
            }
        }
    }

    void mapConstraint(
            Schema schema, Table currentTable, Column currentColumn, TConstraint node) {

        switch (node.getConstraint_type()) {
            case check:
                addCheckConstraint(
                        schema, currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getCheckCondition());
                break;
            case foreign_key:
            case reference:
                addForeignKeyConstraint(
                        schema, currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList(),
                        node.getReferencedObject(),
                        node.getReferencedColumnList());
                break;
            case notnull:
                addNotNullConstraint(
                        schema, currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList());
                break;
            case primary_key:
                setPrimaryKeyConstraint(
                        schema, currentTable, currentColumn,
                        node.getConstraintName(),
                        node.getColumnList());
                break;
            case unique:
                addUniqueConstraint(
                        schema, currentTable, currentColumn,
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

    private void addCheckConstraint(
            Schema schema, Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TExpression expressionNode) {

        String constraintName = stripQuotes(constraintNameObject);
        Expression expression = expressionMapper.getExpression(currentTable, expressionNode);
        schema.createCheckConstraint(constraintName, currentTable, expression);
    }

    private void addForeignKeyConstraint(
            Schema schema, Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList,
            TObjectName referenceTableNameObject, TObjectNameList referenceColumnNameObjectList) {
    	
    	String constraintName = stripQuotes(constraintNameObject);
        String referenceTableName = stripQuotes(referenceTableNameObject);
        
        LOGGER.log(Level.INFO, "Attempting to create FOREIGN KEY on {0} referencing table {1}", 
                new Object[]{currentTable, referenceTableName});
        
        Table referenceTable = schema.getTable(referenceTableName);
        if (referenceTable == null) {
            LOGGER.log(Level.SEVERE, "Could not find table {0} in schema", 
                    new Object[]{referenceTableName});
            
            throw new SQLParseException("Unable to find reference table \"" 
                    + referenceTableName + "\" in schema");
        }
        
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        List<Column> referenceColumns = mapColumns(referenceTable, null, referenceColumnNameObjectList);

        if (referenceColumns.isEmpty()) {
            // no reference columns we have to map those of the primary key in the reference table by default
            referenceColumns = schema.getPrimaryKeyConstraint(referenceTable).getColumns();
        }

        LOGGER.log(Level.INFO, "-- columns in source table {0} are {1}", 
        		new Object[]{currentTable, StringUtils.implode(columns)});
        
        LOGGER.log(Level.INFO, "-- columns in reference table {0} are {1}", 
                new Object[]{referenceTable, StringUtils.implode(referenceColumns)});
                
        schema.createForeignKeyConstraint(
                constraintName, currentTable, columns, referenceTable, referenceColumns);
        
        LOGGER.log(Level.INFO, "-- success");
    }

    private void addNotNullConstraint(
            Schema schema, Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        Column[] columns = mapColumns(currentTable, currentColumn, columnNameObjectList).toArray(new Column[0]);
        schema.createNotNullConstraint(constraintName, currentTable, columns[0]);
    }

    private void setPrimaryKeyConstraint(
            Schema schema, Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        schema.createPrimaryKeyConstraint(constraintName, currentTable, columns);
    }

    private void addUniqueConstraint(
            Schema schema, Table currentTable, Column currentColumn,
            TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {

        String constraintName = stripQuotes(constraintNameObject);
        List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);
        schema.createUniqueConstraint(constraintName, currentTable, columns);
    }

    private List<Column> mapColumns(Table currentTable, Column currentColumn, TObjectNameList columnNameObjectList) {
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
