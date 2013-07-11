package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.database.Database;
import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SchemaSQLToJava {

	public static final String ADAPTED_SQL_FILE_SUFFIX = "-SchemaAnalyst";
	
	public static File fileForSchemaSQL(String name) {
		return new File(FolderConfiguration.schema_src_dir + "/" + name + ".sql");
	}
	
	public static File fileForCaseStudyJavaSrc(String name) {
		return new File(FolderConfiguration.casestudy_src_dir + "/" + name + ".java");
	}
	
	public static void parse(String name, String databaseName) throws ClassNotFoundException, 
																	  InstantiationException, 
																	  IllegalAccessException, 
																	  FileNotFoundException {
		Database database = Database.instantiate(databaseName);
		
		File sqlFile = fileForSchemaSQL(name);
		File adaptedSQLFile = fileForSchemaSQL(name + ADAPTED_SQL_FILE_SUFFIX);
		if (adaptedSQLFile.exists()) {
			sqlFile = adaptedSQLFile;
		}

		Logger logger = Logger.getLogger("Schema Mapping");
		logger.setLevel(Level.WARNING);
				
		Parser parser = new Parser(database);
		SchemaMapper mapper = new SchemaMapper(logger);
		Schema parsedSchema = mapper.getSchema(name, parser.parse(sqlFile));
		
		String javaCode = (new SchemaJavaWriter(parsedSchema)).writeSchema("parsedcasestudy");
		PrintWriter out = new PrintWriter(fileForCaseStudyJavaSrc(name));
		out.println(javaCode);		
		out.close();
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println(
					"Usage: java " + 
					SchemaSQLToJava.class.getName() + 
					" schema_sql_file database");
			System.exit(1);
		}
		parse(args[0], args[1]);
	}	
}
