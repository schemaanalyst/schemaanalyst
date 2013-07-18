package org.schemaanalyst.tool;

import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.DataGeneratorFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomisationFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

@RequiredParameters("schema dbms datagenerator")
public class GenerateData extends Runner {

    @Parameter("The name of the schema to be processed")
    private String schema;
    
    @Parameter("The identification string of the DBMS to be used")
    private String dbms;
    
    @Parameter("The identification string of the data generator to be used")
    private String datagenerator;
    
    @Parameter("The random seed to start the search algorithm")
    private long seed = 0;
    
    @Parameter("The identification string of the cell randomisation profile to be used")
    private String cellrandomisationprofile = "Small";
    
    public GenerateData(String... args) {
        super(args);
    }
    
    public void run() {
        
        try {
            // get hold of required objects for parameter strings
            DBMS dbmsObject = DBMSFactory.instantiate(dbms);                 
            
            Schema schemaObject = SchemaSQLParser.parse(schema, dbmsObject, folderConfiguration.getSchemaSrcDir());    
            
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
    
    protected void validateParameters() {
        // to complete
    }
    
    public static void main(String... args) {
        new GenerateData(args).run();
    }    

}
