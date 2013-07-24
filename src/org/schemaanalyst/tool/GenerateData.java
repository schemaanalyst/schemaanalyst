package org.schemaanalyst.tool;

import java.io.File;

import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.DataGeneratorFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomisationFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;

@Description("Generates constraint covering data for a schema, printing the INSERTs to the screen.")
@RequiredParameters("schema dbms datagenerator")
public class GenerateData extends Runner {

    @Parameter("The name of the schema to be processed. " + 
               "The SQL must be placed in the schemas subdirectory of casestudies")
    private String schema;
    
    @Parameter(value="The identification string of the DBMS to be used",
               choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    private String dbms;
    
    @Parameter("The identification string of the data generator to be used")
    private String datagenerator;
    
    @Parameter("The random seed to start the search algorithm")
    private long seed = 0;
    
    @Parameter("The identification string of the cell randomisation profile to be used")
    private String cellrandomisationprofile = "Small";
    
    @Override
    protected void task() {
        try {
            // get a DBMS instance
            DBMS dbmsObject = DBMSFactory.instantiate(dbms);
            
            // instantiate a parser
            Parser parser = new Parser(dbmsObject);

            // instantiate a mapper
            SchemaMapper mapper = new SchemaMapper(logger);

            // get the file 
            File sqlFile = new File(folderConfiguration.getSchemaSrcDir() + File.separator + schema + ".sql");

            // get the schema
            Schema schemaObject = mapper.getSchema(schema, parser.parse(sqlFile));    
            
            Random random = new SimpleRandom(seed);    
            
            DataGenerator generator = DataGeneratorFactory.instantiate(
                    datagenerator,
                    schemaObject,
                    dbmsObject,
                    random,
                    CellRandomisationFactory.instantiate(cellrandomisationprofile, random));
            
            // generate data
            CoverageReport report = generator.generate();
            System.out.println(report);
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } 
    }
    
    @Override
    protected void validateParameters() {
        // TODO: complete
    }
    
    public static void main(String... args) {
        new GenerateData().run(args);
    }    

}
