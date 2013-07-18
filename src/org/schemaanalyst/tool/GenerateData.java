package org.schemaanalyst.tool;

import org.schemaanalyst.data.ValueFactory;
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

    @Parameter
    private String schema;
    
    @Parameter
    private String dbms;
    
    @Parameter
    private String datagenerator;
    
    @Parameter
    private long seed = 0;
    
    @Parameter
    private String cellrandomizationprofile = "Small";
    
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
                    new ValueFactory(), // TODO; should come from dbms
                    random,
                    CellRandomisationFactory.instantiate(cellrandomizationprofile, random));
            
            // generate data
            CoverageReport report = generator.generate();
            System.out.println(report);
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } 
    }
    
    public static void main(String... args) {
        new GenerateData(args).run();
    }    

}
