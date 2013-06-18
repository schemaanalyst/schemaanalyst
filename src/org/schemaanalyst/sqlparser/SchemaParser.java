package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TConstraintList;
import gudusoft.gsqlparser.nodes.TParseTreeVisitor;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.io.File;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.DataType;

public class SchemaParser {
	
	private TGSqlParser sqlParser; 
	
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
	
	private void instantiateParser(Database database) {
		sqlParser = new TGSqlParser(VendorResolver.resolve(database));
	}
		
	private Schema performParse(String name) {	
		Schema schema = new Schema(name);
		
		int result = sqlParser.parse();
		if (result != 0) {
			throw new SQLParseException(sqlParser.getErrormessage());
		}
		
        SchemaParseTreeVisitor visitor = new SchemaParseTreeVisitor(schema);
        TStatementList list = sqlParser.sqlstatements;
        
        for (int i=0; i < list.size(); i++) {
        	list.get(i).accept(visitor);
        }	
		
		return schema;
	}
	
	class SchemaParseTreeVisitor extends TParseTreeVisitor {

		Schema schema;
		Table currentTable;
		Column currentColumn;	
		
		ConstraintInstaller constraintMapper;
		
		SchemaParseTreeVisitor(Schema schema) {
			this.schema = schema;		
		}
				
		// **** TABLES ****
	    public void preVisit(TCreateTableSqlStatement node) {
	    	String tableName = QuoteStripper.stripQuotes(node.getTableName());	  
	    	currentTable = schema.createTable(tableName);
	    }
	    
	    public void postVisit(TCreateTableSqlStatement node) {
	    	currentTable = null;
	    }	    

	    
		// **** COLUMNS ****    
	    public void preVisit(TColumnDefinition node) {
	    	String columnName = QuoteStripper.stripQuotes(node.getColumnName());	 	    	
	    	DataType type = DataTypeMapper.map(node.getDatatype());	    	
	    	currentColumn = currentTable.addColumn(columnName, type);   	
	    }	
	    
	    public void postVisit(TColumnDefinition node) {	    
	    	// parse in any column constraints defined here
	    	TConstraintList	list = node.getConstraints();
	    	if (list != null) {
		    	for (int i=0 ; i < list.size(); i++) {
		    		list.getElement(i).accept(this);
		    	}
	    	}	    	
	    	currentColumn = null;
	    }	  	
	    
		// **** CONSTRAINTS ****
	    public void postVisit(TConstraint node) {
	    	ConstraintInstaller.install(schema, currentTable, currentColumn, node);
	    }	    
	}	
}
