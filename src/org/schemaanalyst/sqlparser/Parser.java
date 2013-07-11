package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;

import java.io.File;

import org.schemaanalyst.database.Database;

public class Parser {
	
	protected TGSqlParser sqlParser; 
	
	public Parser(Database database) {
		sqlParser = new TGSqlParser(VendorResolver.resolve(database));		
	}
	
	public TStatementList parse(File file) {
		sqlParser.setSqlfilename(file.getPath());
		return performParse();		
	}

	public TStatementList parse(String sql) {		
		sqlParser.sqltext = sql;
		return performParse();
	}
			
	protected TStatementList performParse() {	
		int result = sqlParser.parse();
		if (result != 0) {
			throw new SQLParseException(sqlParser.getErrormessage());
		}
        return sqlParser.sqlstatements;        
	}	
}
