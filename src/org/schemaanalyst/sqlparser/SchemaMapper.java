package org.schemaanalyst.sqlparser;

import static org.schemaanalyst.sqlparser.QuoteStripper.stripQuotes;

import java.util.logging.Logger;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TAlterTableOptionList;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.stmt.TAlterTableStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;

public class SchemaMapper {

    protected Schema schema;
    protected Logger logger;
    protected ConstraintMapper constraintMapper;
    protected DataTypeMapper dataTypeMapper;
    protected ExpressionMapper expressionMapper;

    public SchemaMapper(Logger logger) {
        this.logger = logger;
        expressionMapper = new ExpressionMapper(logger);
        dataTypeMapper = new DataTypeMapper(logger);
        constraintMapper = new ConstraintMapper(logger, expressionMapper);
    }

    public Schema getSchema(String name, TStatementList list) {
        schema = new Schema(name);

        for (int i = 0; i < list.size(); i++) {
            analyseStatement(list.get(i));
        }

        return schema;
    }

    protected void analyseStatement(TCustomSqlStatement node) {

        switch (node.sqlstatementtype) {
            case sstcreatetable:
                mapTable((TCreateTableSqlStatement) node);
                break;
            case sstaltertable:
                analyseAlterTableStatement((TAlterTableStatement) node);
                break;
            default:
                // only CREATE TABLE and ALTER TABLE are handled
                logger.warning("Ignored statmeent " + " \"" + node + "\" on line " + node.getLineNo());
        }
    }

    protected void mapTable(TCreateTableSqlStatement node) {
        // create a Table object
        String tableName = stripQuotes(node.getTableName());
        Table table = schema.createTable(tableName);

        // log this event
        logger.info("Parsing table \"" + tableName + "\" one line " + node.getLineNo());

        // parse columns
        TColumnDefinitionList columnList = node.getColumnList();
        for (int i = 0; i < columnList.size(); i++) {
            mapColumn(table, columnList.getColumn(i));
        }

        // analyse table constraints
        constraintMapper.analyseConstraintList(table, null, node.getTableConstraints());
    }

    protected void mapColumn(Table table, TColumnDefinition node) {
        // get the column name		
        String columnName = stripQuotes(node.getColumnName());

        // log this event
        logger.info("Parsing column \"" + columnName + "\" one line " + node.getLineNo());

        // get data type and add column to table
        DataType type = dataTypeMapper.getDataType(node.getDatatype(), node);
        Column column = table.addColumn(columnName, type);

        // parse any inline column constraints
        constraintMapper.analyseConstraintList(table, column, node.getConstraints());
    }

    protected void analyseAlterTableStatement(TAlterTableStatement node) {
        logger.warning("Parsing alter table statement \"" + node + "\", which has an incomplete GSP implementation at line: " + node.getLineNo());

        String tableName = stripQuotes(node.getTableName());
        Table table = schema.getTable(tableName);

        TAlterTableOptionList optionList = node.getAlterTableOptionList();
        if (optionList == null) {
            logger.severe("Option list for alter table statement for \"" + table + "\" is null -- giving up at line: " + node.getLineNo());
        } else {
            for (int i = 0; i < optionList.size(); i++) {
                analyseAlterTableOption(table, optionList.getAlterTableOption(i));
            }
        }
    }

    protected void analyseAlterTableOption(Table currentTable, TAlterTableOption node) {
        logger.warning("Parsing alter table option statement, which has a buggy/incomplete implementation on line " + node.getLineNo());

        switch (node.getOptionType()) {
            case AddConstraint:
                constraintMapper.analyseConstraintList(currentTable, null, node.getConstraintList());
                break;
            case AddConstraintPK:
                constraintMapper.setPrimaryKeyConstraint(
                        currentTable, null,
                        node.getConstraintName(),
                        node.getColumnNameList());
                break;
            case AddConstraintUnique:
                constraintMapper.addUniqueConstraint(
                        currentTable, null,
                        node.getConstraintName(),
                        node.getColumnNameList());
                break;
            default:
                throw new UnsupportedSQLException(node);
        }
    }
}
