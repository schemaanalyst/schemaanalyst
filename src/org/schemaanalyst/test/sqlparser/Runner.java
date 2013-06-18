package org.schemaanalyst.test.sqlparser;

import java.io.File;
import java.util.List;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.mysql.MySQL;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.sqlparser.SchemaParser;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class Runner {

	static String getCaseStudiesPath() {
		return "/Users/phil/Projects/schemaanalyst/casestudies/schemas/";
	}
	
	static void run(String caseStudy, Database database) throws Exception {
		//Schema originalSchema = (Schema) Class.forName("casestudy." + caseStudy).newInstance();
		
		File file = new File(getCaseStudiesPath() + caseStudy + ".sql");		
		SchemaParser schemaParser = new SchemaParser();
		Schema parsedSchema = schemaParser.parse(file, caseStudy, database);
		SQLWriter sqlWriter = database.getSQLWriter();
		 
		List<String> parsedSQLStatements = sqlWriter.writeCreateTableStatements(parsedSchema);
		
		String parsedSQL = "";
		for (String statement : parsedSQLStatements) {
			parsedSQL += statement + "\n";
		}
		
		/*
		List<String> originalSQLStatements = sqlWriter.writeCreateTableStatements(originalSchema);
		String originalSQL = "";
		for (String statement : originalSQLStatements) {
			originalSQL += statement + "\n";
		}
		
		System.out.println("-- ************ Original ************");
		System.out.println(originalSQL);
		*/
		
		System.out.println("-- ************ Parsed ************");
		System.out.println(parsedSQL);
		
	}
	
	public static void main(String[] args) throws Exception {
		run("_debug", new MySQL());
	}
}
