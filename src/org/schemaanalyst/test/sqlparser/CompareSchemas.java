package org.schemaanalyst.test.sqlparser;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.sqlparser.SQLParseException;
import org.schemaanalyst.sqlparser.SchemaParser;
import org.schemaanalyst.sqlwriter.SQLWriter;

import casestudy.BankAccount;

public class CompareSchemas {

	String getCaseStudiesPath() {
		return "/Users/phil/Projects/schemaanalyst/casestudies/schemas/";
	}
	
	void assertOriginalEqualsParsed(String caseStudy, Database database) throws SQLParseException {
		File file = new File(getCaseStudiesPath() + caseStudy + ".sql");
		BankAccount originalSchema = new BankAccount();
		
		SchemaParser schemaParser = new SchemaParser();
		Schema parsedSchema = schemaParser.parse(file, caseStudy, database);
		SQLWriter sqlWriter = database.getSQLWriter();
		
		List<String> originalSQLStatements = sqlWriter.writeCreateTableStatements(originalSchema); 
		List<String> parsedSQLStatements = sqlWriter.writeCreateTableStatements(parsedSchema);
		
		String originalSQL = "";
		for (String statement : originalSQLStatements) {
			originalSQL += statement;
		}
		
		String parsedSQL = "";
		for (String statement : parsedSQLStatements) {
			parsedSQL += statement;
		}
		
		assertEquals(originalSQL, parsedSQL);
	}
	
	@Test
	public void testBankAccount() throws SQLParseException {
		assertOriginalEqualsParsed("BankAccount", new Postgres());
	}
	
	@Test
	public void testBookTown() throws SQLParseException {
		assertOriginalEqualsParsed("BookTown", new Postgres());
	}	

}
