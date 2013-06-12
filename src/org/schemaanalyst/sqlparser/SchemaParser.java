package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;

import java.io.File;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.mysql.MySQL;
import org.schemaanalyst.schema.Schema;

public class SchemaParser {
	
	private TGSqlParser sqlParser; 
	
	public Schema parse(File file, String name, Database database) throws SQLParseException {
		instantiateParser(database);
		sqlParser.setSqlfilename(file.getPath());
		return performParse(name);		
	}
	
	public Schema parse(String sql, String name, Database database) throws SQLParseException {		
		instantiateParser(database);
		sqlParser.sqltext = sql;
		return performParse(name);
	}
	
	private void instantiateParser(Database database) {
		sqlParser = new TGSqlParser(VendorResolver.resolve(database));
	}
		
	private Schema performParse(String name) throws SQLParseException {	
		Schema schema = new Schema(name);
		
		int result = sqlParser.parse();
		if (result != 0) {
			throw new SQLParseException(sqlParser.getErrormessage());
		}
		
		
        SchemaParseTreeVisitor visitor = new SchemaParseTreeVisitor(schema);
        TStatementList sqlStatements = sqlParser.sqlstatements;
        
        while (sqlStatements.hasNext()) {
        	TCustomSqlStatement sqlStatement = sqlStatements.next();
        	sqlStatement.accept(visitor);
        }	
		
		return schema;
	}
	
	
	public static void main(String[] args) throws SQLParseException {		
		File file = new File("~/Projects/schemaanalyst/casestudies/schemas/BankAccount.sql");
		String name = "BankAccount";
		Database db = new MySQL();
		
		SchemaParser schemaParser = new SchemaParser();
		schemaParser.parse(file, name, db);		
	}
}
