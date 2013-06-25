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
	
	public Schema parse(File file, String name, Database database) {
		instantiateParser(database);
		sqlParser.setSqlfilename(file.getPath());
		return performParse(name);		
	}
	
	public Schema parse(String sql, String name, Database database) {		
		instantiateParser(database);
		sqlParser.sqltext = sql;
		return performParse(name);
	}
	
	protected void instantiateParser(Database database) {
		sqlParser = new TGSqlParser(VendorResolver.resolve(database));
	}
		
	protected Schema performParse(String name) {	
		schema = new Schema(name);
		
		int result = sqlParser.parse();
		if (result != 0) {
			throw new SQLParseException(sqlParser.getErrormessage());
		}
		
        TStatementList list = sqlParser.sqlstatements;        
        for (int i=0; i < list.size(); i++) {        	
        	analyseStatement(list.get(i));
        }	
		
		return schema;
	}
	
	protected void analyseStatement(TCustomSqlStatement statement) {

		switch (statement.sqlstatementtype){

        	case sstcreatetable:
        		analyseCreateTableStatement((TCreateTableSqlStatement) statement);
        		break;
        	case sstaltertable:
        		analyseAlterTableStatement((TAlterTableStatement) statement);
        		break;
        	default:
        		throw new SQLParseException(
        				"Cannot handle \"" + 
        				statement.sqlstatementtype.toString() + 
        				"\" in schema parsing mode");
        }		
	}
	
	protected void analyseCreateTableStatement(TCreateTableSqlStatement createTableStatement) {
    	
		// create table
		String tableName = stripQuotes(createTableStatement.getTableName());	  
    	Table table = schema.createTable(tableName);	
		
    	TColumnDefinitionList columnList = createTableStatement.getColumnList();
    	
    	// analyse columns
        for (int i=0; i< columnList.size(); i++){
            TColumnDefinition columnDefinition = columnList.getColumn(i);
	    	String columnName = stripQuotes(columnDefinition.getColumnName());	 	    	
	    	
	    	DataType type = DataTypeMapper.map(columnDefinition.getDatatype());	    	
	    	Column column = table.addColumn(columnName, type);          	
        		    	
	    	// analyse any inline column constraints
	    	TConstraintList	list = columnDefinition.getConstraints();
	    	if (list != null) {
		    	for (int j=0 ; j < list.size(); j++) {
		    		analyseConstraint(list.getConstraint(j), table, column);
		    	}
	    	}	
        }
        
        // analyse table constraints
        analyseConstraintList(createTableStatement.getTableConstraints(), table);
	}
	
	protected void analyseAlterTableStatement(TAlterTableStatement alterTableStatement) {
		String tableName = stripQuotes(alterTableStatement.getTableName());
		Table table = schema.getTable(tableName);
		
		System.out.println(alterTableStatement.getTableElementList());
		
		TAlterTableOptionList optionList = alterTableStatement.getAlterTableOptionList();
		for (int i=0; i < optionList.size(); i++){
	    	analyseAlterTableOption(optionList.getAlterTableOption(i), table);
		}
	}
	
	protected void analyseAlterTableOption(TAlterTableOption alterTableOption, Table currentTable) {

        switch (alterTableOption.getOptionType()){		
        	case AddConstraint:
        		analyseConstraintList(alterTableOption.getConstraintList(), currentTable);
        		break;
        	case AddConstraintPK:
        		setPrimaryKeyConstraint(
        				currentTable, null, 
        				alterTableOption.getConstraintName(),
        				alterTableOption.getColumnNameList());
        		break;
        	case AddConstraintUnique:
        		addUniqueConstraint(
        				currentTable, null,
        				alterTableOption.getConstraintName(),
        				alterTableOption.getColumnNameList());
        		break;        		
        	default:
        		throw new UnsupportedFeatureException(alterTableOption);
        }
	}
	
	protected void analyseConstraintList(TConstraintList constraintList, Table currentTable) {
		if (constraintList != null) {
			for (int i=0; i < constraintList.size(); i++) {
				analyseConstraint(constraintList.getConstraint(i), currentTable, null);
			}
		}
	}
	
	protected void analyseConstraint(TConstraint constraintDefinition, Table currentTable, Column currentColumn) {
		
      	switch (constraintDefinition.getConstraint_type()) {    	
    		case check:
    			addCheckConstraint(
    					currentTable, currentColumn,
    					constraintDefinition.getConstraintName(), 
    					constraintDefinition.getCheckCondition());
	    		break;
    		case foreign_key:
    		case reference:
    			addForeignKeyConstraint(
    					currentTable, currentColumn,
    					constraintDefinition.getConstraintName(), 
    					constraintDefinition.getColumnList(), 
    					constraintDefinition.getReferencedObject(),
    					constraintDefinition.getReferencedColumnList());
    			break;
    		case notnull:
    			addNotNullConstraint(
    					currentTable, currentColumn, 
    					constraintDefinition.getConstraintName(), 
    					constraintDefinition.getColumnList());
    			break;    			
    		case primary_key:    			
    			setPrimaryKeyConstraint(
    					currentTable, currentColumn,
    					constraintDefinition.getConstraintName(), 
    					constraintDefinition.getColumnList());
    			break;
    		case unique:
    			addUniqueConstraint(
    					currentTable, currentColumn,
    					constraintDefinition.getConstraintName(), 
    					constraintDefinition.getColumnList());
    			break;
    		default:
    			throw new UnsupportedFeatureException(constraintDefinition);
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

		List<Column> columns = getColumns(currentTable, currentColumn, columnNameObjectList);		
		List<Column> referenceColumns = getColumns(referenceTable, null, referenceColumnNameObjectList); 
								
		currentTable.addForeignKeyConstraint(constraintName, columns, referenceTable, referenceColumns);				
	}
	
	protected void addNotNullConstraint(
			Table currentTable, Column currentColumn,
			TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {
		
		String constraintName = stripQuotes(constraintNameObject);
		Column[] columns = getColumns(currentTable, currentColumn, columnNameObjectList).toArray(new Column[0]);
		currentTable.addNotNullConstraint(constraintName, columns[0]);		
	}	
	
	protected void setPrimaryKeyConstraint(
			Table currentTable, Column currentColumn,
			TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {
		
		String constraintName = stripQuotes(constraintNameObject);
		List<Column> columns = getColumns(currentTable, currentColumn, columnNameObjectList);
		currentTable.setPrimaryKeyConstraint(constraintName, columns);
	}
	
	protected void addUniqueConstraint(
			Table currentTable, Column currentColumn,
			TObjectName constraintNameObject, TObjectNameList columnNameObjectList) {
		
		String constraintName = stripQuotes(constraintNameObject);
		List<Column> columns = getColumns(currentTable, currentColumn, columnNameObjectList);
		currentTable.addUniqueConstraint(constraintName, columns);		
	}	
	
	protected List<Column> getColumns(Table currentTable, Column currentColumn, TObjectNameList columnNameObjectList) {
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
