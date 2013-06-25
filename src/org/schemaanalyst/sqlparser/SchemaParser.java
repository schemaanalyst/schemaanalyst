package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TAlterTableOptionList;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TColumnDefinitionList;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TConstraintList;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.stmt.TAlterTableStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.schemaanalyst.sqlparser.QuoteStripper.stripQuotes;

public class SchemaParser {
	
	protected Schema schema;
	protected TGSqlParser sqlParser; 
	protected Logger logger;
	
	public SchemaParser(Database database, Logger logger) {
		this.logger = logger;
		sqlParser = new TGSqlParser(VendorResolver.resolve(database));		
	}
	
	public Schema parseSchema(String schemaName, File file) {
		sqlParser.setSqlfilename(file.getPath());
		return performParse(schemaName);		
	}

	public Schema parseSchema(String schemaName, String sql) {		
		sqlParser.sqltext = sql;
		return performParse(schemaName);
	}
			
	protected Schema performParse(String schemaName) {	
		schema = new Schema(schemaName);
		
		int result = sqlParser.parse();
		if (result != 0) {
			throw new SQLParseException(sqlParser.getErrormessage());
		}
		
        TStatementList list = sqlParser.sqlstatements;        
        for (int i=0; i < list.size(); i++) {        	
        	parseSQLStatement(list.get(i));
        }	
		
		return schema;
	}
	
	protected void parseSQLStatement(TCustomSqlStatement node) {

		switch (node.sqlstatementtype) {
        	case sstcreatetable:
        		parseCreateTableStatement((TCreateTableSqlStatement) node);
        		break;
        	case sstaltertable:
        		parseAlterTableStatement((TAlterTableStatement) node);
        		break;
        	default:
        		// only CREATE TABLE and ALTER TABLE are handled
        		logger.warning("Ignored statmeent " + " \"" + node + "\" on line " + node.getLineNo());        		
        }		
	}
	
	protected void parseCreateTableStatement(TCreateTableSqlStatement node) {
		// create a Table object
		String tableName = stripQuotes(node.getTableName());	  
    	Table table = schema.createTable(tableName);	
		
    	// log this event
    	logger.info("Parsing table \"" + tableName + "\" one line " + node.getLineNo());    	
    	
    	// parse columns
    	TColumnDefinitionList columnList = node.getColumnList();
        for (int i=0; i< columnList.size(); i++){
            parseColumn(table, columnList.getColumn(i));	
        }
        
        // analyse table constraints
        parseConstraintList(table, null, node.getTableConstraints());
	}

	protected void parseColumn(Table table, TColumnDefinition node) {
		// get the column name		
		String columnName = stripQuotes(node.getColumnName());	 	    	
		
    	// log this event
    	logger.info("Parsing column \"" + columnName + "\" one line " + node.getLineNo());   		
		
    	// get data type and add column to table
		DataType type = DataTypeMapper.map(node.getDatatype(), node);	    	
		Column column = table.addColumn(columnName, type);          	
			    	
		// parse any inline column constraints
		parseConstraintList(table, column, node.getConstraints());
	}
	
	protected void parseAlterTableStatement(TAlterTableStatement node) {
		logger.warning("Parsing alter table statement, which has a buggy/incomplete implementation on line " + node.getLineNo());    		
		
		String tableName = stripQuotes(node.getTableName());
		Table table = schema.getTable(tableName);
		
		TAlterTableOptionList optionList = node.getAlterTableOptionList();
		for (int i=0; i < optionList.size(); i++){
	    	parseAlterTableOption(table, optionList.getAlterTableOption(i));
		}
	}
	
	protected void parseAlterTableOption(Table currentTable, TAlterTableOption node) {
		logger.warning("Parsing alter table option statement, which has a buggy/incomplete implementation on line " + node.getLineNo());
		
        switch (node.getOptionType()){		
        	case AddConstraint:
        		parseConstraintList(currentTable, null, node.getConstraintList());
        		break;
        	case AddConstraintPK:
        		setPrimaryKeyConstraint(
        				currentTable, null, 
        				node.getConstraintName(),
        				node.getColumnNameList());
        		break;
        	case AddConstraintUnique:
        		addUniqueConstraint(
        				currentTable, null,
        				node.getConstraintName(),
        				node.getColumnNameList());
        		break;        		
        	default:
        		throw new UnsupportedFeatureException(node);
        }
	}
	
	protected void parseConstraintList(Table currentTable, Column currentColumn, TConstraintList node) {
		if (node != null) {
			for (int i=0; i < node.size(); i++) {
				parseConstraint(currentTable, currentColumn, node.getConstraint(i));
			}
		}
	}
	
	protected void parseConstraint(Table currentTable, Column currentColumn, TConstraint node) {
		
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
    		default:
    			throw new UnsupportedFeatureException(node);
    	}    	
	}

	protected void addCheckConstraint(
			Table currentTable, Column currentColumn,
			TObjectName constraintNameObject, TExpression expressionNode) {

		String constraintName = stripQuotes(constraintNameObject);
		Expression expression = ExpressionMapper.map(currentTable, expressionNode);		
		currentTable.addCheckConstraint(constraintName, expression);		
	}	
	
	protected void addForeignKeyConstraint(
			Table currentTable, Column currentColumn,
			TObjectName constraintNameObject, TObjectNameList columnNameObjectList, 
			TObjectName referenceTableNameObject, TObjectNameList referenceColumnNameObjectList) {
		
		String constraintName = stripQuotes(constraintNameObject);
		String referenceTableName = stripQuotes(referenceTableNameObject);
		Table referenceTable = schema.getTable(referenceTableName);

		List<Column> columns = mapColumns(currentTable, currentColumn, columnNameObjectList);		
		List<Column> referenceColumns = mapColumns(referenceTable, null, referenceColumnNameObjectList); 
								
		currentTable.addForeignKeyConstraint(constraintName, columns, referenceTable, referenceColumns);				
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
		List<Column> columns = new ArrayList<Column>();
		
		if (currentColumn != null) {
    		columns.add(currentColumn);
    	} else {
    		for (int i=0; i < columnNameObjectList.size(); i++) {
    			String columnName = stripQuotes(columnNameObjectList.getObjectName(i));
    			Column column = currentTable.getColumn(columnName);
    			columns.add(column);
    		}
    	}
		
		return columns;
	}
}
