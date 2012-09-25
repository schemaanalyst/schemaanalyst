package casestudy.runner;

import java.util.List;

import casestudy.AllCaseStudies;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class InstantiateSchema {
	
	public static void printSchema(Schema schema) {
		printSchema(schema, new Postgres());
	}
	
	public static void printSchema(Schema schema, Database database) {
		SQLWriter sqlWriter = database.getSQLWriter();
		
		List<String> comments = sqlWriter.writeComments(schema.getComments());
		for (String comment : comments) {
			System.out.println(comment);
		}
		
		List<String> statements = sqlWriter.writeDropTableStatements(schema, true);
		statements.addAll(sqlWriter.writeCreateTableStatements(schema));
		for (String statement : statements) {
			System.out.println(statement + ";");
		}			
	}
	
	public static Schema[] instantiateSchemas(String[] schemaStrings) throws InstantiationException, 
																			 IllegalAccessException, 
																			 ClassNotFoundException {
		Schema[] schemas;
		if (schemaStrings.length != 0) {
			schemas = new Schema[schemaStrings.length];
			for (int i=0; i < schemaStrings.length; i++) {
				String schemaString = schemaStrings[i];
				if (!schemaString.contains(".")) {
					schemaString = "casestudy." + schemaString;
				}				
				schemas[i] = (Schema) Class.forName(schemaString).newInstance();
			}
		} else {
			schemas = AllCaseStudies.schemas;
		}
		return schemas;
	}
	
	public static void main(String[] args) throws Exception {
		Schema[] schemas = instantiateSchemas(args);
		for (Schema schema : schemas) {
			printSchema(schema);
		}
	}
}
