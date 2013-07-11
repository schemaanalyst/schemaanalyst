package org.schemaanalyst.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SchemaSQLParser {

	private static final String ADAPTED_SQL_FILE_SUFFIX = "-SchemaAnalyst";
	
	private static File fileForSchemaSQL(String name) {
		return new File(FolderConfiguration.schema_src_dir + "/" + name + ".sql");
	}
	
	private static File schemaAnalystFileForSchemaSQL(String name) {
		File file = fileForSchemaSQL(name);
		File saFile = fileForSchemaSQL(name + ADAPTED_SQL_FILE_SUFFIX);
		if (saFile.exists()) {
			file = saFile;
		}
		return file;
	}
	
	public static Schema parse(String name, String dbmsName) throws ClassNotFoundException, 
																		InstantiationException, 
																		IllegalAccessException {
		DBMS dbms = DBMSFactory.instantiate(dbmsName);		
		Parser parser = new Parser(dbms);

		Logger logger = Logger.getLogger("Schema Mapping");
		logger.setLevel(Level.WARNING);		
		SchemaMapper mapper = new SchemaMapper(logger);
		
		File sqlFile = schemaAnalystFileForSchemaSQL(name);
		
		return mapper.getSchema(name, parser.parse(sqlFile));	
	}
}
