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
		SQLWriter sqlWriter = database.getSQLWriter();		
		//parseAndPrintOriginalSchema(caseStudy, sqlWriter);
		parseAndPrintTextSchema(caseStudy, database, sqlWriter);
		
	}

	private static void parseAndPrintTextSchema(String caseStudy, Database database, SQLWriter sqlWriter) {
		File file = new File(getCaseStudiesPath() + caseStudy + ".sql");		
		SchemaParser schemaParser = new SchemaParser();
		Schema parsedSchema = schemaParser.parse(file, caseStudy, database);
		
		List<String> parsedSQLStatements = sqlWriter.writeCreateTableStatements(parsedSchema);
		
		String parsedSQL = "";
		for (String statement : parsedSQLStatements) {
			parsedSQL += statement + "\n";
		}
		
		System.out.println("-- ************ Parsed ************");
		System.out.println(parsedSQL);
	}

	private static void parseAndPrintOriginalSchema(String caseStudy, SQLWriter sqlWriter) throws InstantiationException,
																								  IllegalAccessException, 
																								  ClassNotFoundException {
		Schema originalSchema = (Schema) Class.forName("casestudy." + caseStudy).newInstance();
		List<String> originalSQLStatements = sqlWriter.writeCreateTableStatements(originalSchema);
		String originalSQL = "";
		for (String statement : originalSQLStatements) {
			originalSQL += statement + "\n";
		}
		
		System.out.println("-- ************ Original ************");
		System.out.println(originalSQL);
	}
	
	public static void main(String[] args) throws Exception {
		run("FrenchTowns", new Postgres());
	}
}
