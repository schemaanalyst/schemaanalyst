package org.schemaanalyst.tool;

import java.io.File;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;

@Description("Parses a schema SQL file and then writes it back to the console.")
@RequiredParameters("schema dbms")
public class SchemaSQLParser extends Runner {

    @Parameter("The name of the schema to parse.  The SQL file must be placed " +
               "in the schemas subdirectory of casestudies")
    protected String schema;

    @Parameter(value = "The ID string of the DBMS whose dialect of SQL is to be used",
               choicesMethod = "org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    protected String dbms;
    
    protected Schema schemaObject;
    protected DBMS dbmsObject;
    
    protected void instantiateDBMS() {
        try {
            if (dbmsObject == null) {
                dbmsObject = DBMSFactory.instantiate(dbms);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }  
    }
    
    protected void parseSchema() {
        // instantiate a parser
        Parser parser = new Parser(dbmsObject);

        // instantiate a mapper
        SchemaMapper mapper = new SchemaMapper();

        // get the file 
        File sqlFile = new File(locationsConfiguration.getSchemaSrcDir() + File.separator + schema + ".sql");
        
        // get the schema
        schemaObject = mapper.getSchema(schema, parser.parse(sqlFile));                    
    }
    
    @Override
    protected void initialise(String... args) {
        super.initialise(args);        
        instantiateDBMS();
        parseSchema();
    }
    
    @Override
    protected void task() {
        // write the schema back to the console
        SQLWriter sqlWriter = dbmsObject.getSQLWriter();
        System.out.println(sqlWriter.writeCreateTableStatements(schemaObject));
    }

    @Override
    protected void validateParameters() {
        // nothing to do here
    }
    
    public static void main(String... args) {
        new SchemaSQLParser().run(args);
    }
}
