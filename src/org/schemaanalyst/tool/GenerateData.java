package org.schemaanalyst.tool;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.DataGeneratorFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomisationFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

public class GenerateData {

    /*
     * PSM TODO fix compile errors ...
    
    public static void generate(String schemaName,
            String dbmsName,
            String dataGeneratorName) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException {
        //TODO: use basic params here
        //Configuration.database = schema
        //Configuration.type = database
        //Configuration.datagenerator = datagenerator

        Schema schema = SchemaSQLParser.parse(schemaName, dbmsName);

        Random random = new SimpleRandom(0L);

        DataGenerator generator = DataGeneratorFactory.instantiate(
                dataGeneratorName,
                schema,
                new ValueFactory(), // should come from dbms
                random,
                CellRandomisationFactory.instantiate("Small", random));
        CoverageReport report = generator.generate();
        System.out.println(report);
    }

    public static void main(String[] args) throws Exception {
        generate(args[0], args[1], args[2]);
    }
    
    */
}
