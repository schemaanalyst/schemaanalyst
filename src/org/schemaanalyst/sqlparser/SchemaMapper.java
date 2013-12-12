package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EIndexType;
import static org.schemaanalyst.sqlparser.QuoteStripper.stripQuotes;

import java.util.logging.Logger;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TAlterTableOptionList;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TOrderByItemList;
import gudusoft.gsqlparser.stmt.TAlterTableStatement;
import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;

/**
 * <p>
 * Maps the representation used by The General SQL Parser to the representation
 * used by SchemaAnalyst.
 * </p>
 */
public class SchemaMapper {

    private final static Logger LOGGER = Logger.getLogger(SchemaMapper.class.getName());

    private Schema schema;

    private ConstraintMapper constraintMapper;
    private DataTypeMapper dataTypeMapper;
    private ExpressionMapper expressionMapper;

    public SchemaMapper() {
        expressionMapper = new ExpressionMapper();
        dataTypeMapper = new DataTypeMapper();
        constraintMapper = new ConstraintMapper(expressionMapper);
    }

    public Schema getSchema(String name, TStatementList list) {
        schema = new Schema(name);
        for (int i = 0; i < list.size(); i++) {
            analyseStatement(list.get(i));
        }
        return schema;
    }

    private void analyseStatement(TCustomSqlStatement node) {

        switch (node.sqlstatementtype) {
            case sstcreatetable:
                mapTable((TCreateTableSqlStatement) node);
                break;
            case sstaltertable:
                analyseAlterTableStatement((TAlterTableStatement) node);
                break;
            case sstcreateindex:
                analyseCreateIndexStatement((TCreateIndexSqlStatement) node);
                break;
            default:
                // only CREATE TABLE and ALTER TABLE are handled
                LOGGER.log(Level.WARNING, "Ignored statement \"{0}\" on line {1}", new Object[]{node, node.getLineNo()});
        }
    }

    private void mapTable(TCreateTableSqlStatement node) {
        // create a Table object
        String tableName = stripQuotes(node.getTableName());
        Table table = schema.createTable(tableName);

        // log this event
        LOGGER.log(Level.INFO, "Parsing table \"{0}\" at line {1}", new Object[]{tableName, node.getLineNo()});

        // parse columns
        TColumnDefinitionList columnList = node.getColumnList();
        for (int i = 0; i < columnList.size(); i++) {
            mapColumn(table, columnList.getColumn(i));
        }

        // analyse table constraints
        constraintMapper.analyseConstraintList(schema, table, null, node.getTableConstraints());
    }

    private void mapColumn(Table table, TColumnDefinition node) {
        // get the column name		
        String columnName = stripQuotes(node.getColumnName());

        // log this event
        LOGGER.log(Level.INFO, "Parsing column \"{0}\" on line {1}", new Object[]{columnName, node.getLineNo()});

        // get data type and add column to table
        DataType type = dataTypeMapper.getDataType(node.getDatatype(), node);
        Column column = table.createColumn(columnName, type);

        // parse any inline column constraints
        constraintMapper.analyseConstraintList(schema, table, column, node.getConstraints());
    }

    private void analyseAlterTableStatement(TAlterTableStatement node) {
        LOGGER.log(Level.INFO, "Parsing alter table statement \"{0}\" on line: {1}", new Object[]{node, node.getLineNo()});

        String tableName = stripQuotes(node.getTableName());
        Table table = schema.getTable(tableName);

        TAlterTableOptionList optionList = node.getAlterTableOptionList();
        if (optionList == null) {
            LOGGER.log(Level.SEVERE, "Option list for alter table statement for \"{0}\" is null -- giving up at line: {1}", new Object[]{table, node.getLineNo()});
        } else {
            for (int i = 0; i < optionList.size(); i++) {
                analyseAlterTableOption(table, optionList.getAlterTableOption(i));
            }
        }
    }

    private void analyseAlterTableOption(Table currentTable, TAlterTableOption node) {
        switch (node.getOptionType()) {
            case AddTableConstraint:
                constraintMapper.mapConstraint(schema, currentTable, null, node.getTableConstraint());
                break;
            default:
                throw new UnsupportedSQLException(node);
        }
    }

    private void analyseCreateIndexStatement(TCreateIndexSqlStatement node) {
        if (node.getIndexType().equals(EIndexType.itUnique)) {
            Table table = schema.getTable(stripQuotes(node.getTableName()));
            List<Column> columns = new ArrayList<>();
            TObjectName indexName = node.getIndexName();
            TOrderByItemList columnNameList = node.getColumnNameList();
            for (String columnName : columnNameList.toString().split(",")) {
                columns.add(table.getColumn(stripQuotes(columnName.trim())));
            }
            if (indexName == null) {
                schema.addUniqueConstraint(new UniqueConstraint(table, columns));
            } else {
                schema.addUniqueConstraint(new UniqueConstraint(stripQuotes(indexName.toString()), table, columns));
            }
        } else {
            LOGGER.log(Level.WARNING, "Ignored statement \"{0}\" on line {1}", new Object[]{node, node.getLineNo()});
        }
    }
}
