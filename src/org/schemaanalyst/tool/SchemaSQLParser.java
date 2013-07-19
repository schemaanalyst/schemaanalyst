package org.schemaanalyst.tool;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

@Description("Parses a schema SQL file and then writes it back to the console.")
@RequiredParameters("schema dbms")
public class SchemaSQLParser extends Runner {

    @Parameter("The name of the schema to parse.  The SQL file must be placed " +
               "in the schemas subdirectory of casestudies")
    protected String schema;

    @Parameter(value="The ID string of the DBMS whose dialect of SQL is to be used",
               choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    protected String dbms;
    
    protected Schema schemaObject;
    protected DBMS dbmsObject;
    
    protected void instantiateDBMS() {
        try {
            if (dbmsObject == null) {
                dbmsObject = DBMSFactory.instantiate(dbms);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }  
    }
    
    protected void parseSchema() {
        // instantiate a parser
        Parser parser = new Parser(dbmsObject);

        // instantiate a mapper
        Logger logger = Logger.getLogger("Schema Mapping");
        logger.setLevel(Level.parse(loggingConfiguration.getLogLevel())); 
        SchemaMapper mapper = new SchemaMapper(logger);

        // get the file 
        File sqlFile = new File(folderConfiguration.getSchemaSrcDir() + "/" + schema + ".sql");
        
        // get the schema
        schemaObject = mapper.getSchema(schema, parser.parse(sqlFile));                    
    }
    
    protected void initialise(String... args) {
        super.initialise(args);        
        instantiateDBMS();
        parseSchema();
    }
    
    public void run(String... args) {
        initialise(args);
        
        // write the schema back to the console
        SQLWriter sqlWriter = dbmsObject.getSQLWriter();
        System.out.println(sqlWriter.writeCreateTableStatements(schemaObject));
    }

    protected void validateParameters() {
        // nothing to do here
    }
    
    public static void main(String... args) {
        new SchemaSQLParser().run(args);
    }
}
