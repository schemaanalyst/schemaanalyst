package org.schemaanalyst.test.runner;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.SchemaAnalyst;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.sqlparser.SchemaParser;
import org.schemaanalyst.sqlrepresentation.Schema;

import originalcasestudy.BankAccount;
import originalcasestudy.StudentResidence;

public class GenerateData {

	public static void main(String[] args) throws Exception {
		//for (Schema schema : InstantiateSchema.instantiateSchemas(args)) {			
			
		File file = new File("/Users/phil/Projects/schemaanalyst/casestudies/schemas/BookTown-SchemaAnalyst.sql");
		Logger logger = Logger.getLogger("test");
		logger.setLevel(Level.WARNING);		
		SchemaParser schemaParser = new SchemaParser(new Postgres(), logger);
		Schema schema = schemaParser.parseSchema("StudentResidence", file);		
		//Schema schema = new StudentResidence();
			//if (!schema.getName().equals("Pagila'")) {
			
				DataGenerator generator = SchemaAnalyst.constructDataGenerator(
						"alternatingvalue", schema, new ValueFactory());
			
				CoverageReport report = generator.generate();
			
				System.out.println(report);
			//}
		//}
	}		
}
     