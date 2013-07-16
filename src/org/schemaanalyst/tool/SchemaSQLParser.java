package org.schemaanalyst.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SchemaSQLParser {

    private static final String ADAPTED_SQL_FILE_SUFFIX = "-SchemaAnalyst";

    private static File fileForSchemaSQL(String name, String casestudySrcDir) {
        return new File(casestudySrcDir + "/" + name + ".sql");
    }

    private static File schemaAnalystFileForSchemaSQL(String name, String casestudySrcDir) {
        File file = fileForSchemaSQL(name, casestudySrcDir);
        File saFile = fileForSchemaSQL(name + ADAPTED_SQL_FILE_SUFFIX, casestudySrcDir);
        if (saFile.exists()) {
            file = saFile;
        }
        return file;
    }

    public static Schema parse(String name, String dbmsName, String casestudySrcDir) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException {
        DBMS dbms = DBMSFactory.instantiate(dbmsName);
        Parser parser = new Parser(dbms);

        Logger logger = Logger.getLogger("Schema Mapping");
        logger.setLevel(Level.WARNING);
        SchemaMapper mapper = new SchemaMapper(logger);

        File sqlFile = schemaAnalystFileForSchemaSQL(name, casestudySrcDir);

        return mapper.getSchema(name, parser.parse(sqlFile));
    }
}
