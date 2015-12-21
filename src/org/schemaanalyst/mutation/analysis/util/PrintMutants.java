package org.schemaanalyst.mutation.analysis.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 * Prints the detailed description of all mutants of a given schema.
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("dbms casestudy")
public class PrintMutants extends Runner {
    
    /**
     * The name of the DBMS to use.
     */
    @Parameter("The name of the DBMS to use.")
    protected String dbms;
    
    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsNormalisedWithClassifiers";

    @Override
    protected void task() {
        // Instantiate configured options and generate mutants
        Schema schema = instantiateSchema(casestudy);
        SQLWriter writer = instantiateDBMSWriter(dbms);
        List<Mutant<Schema>> mutants = generateMutants(schema, dbms);
        
        // Iterate mutants and print
        for (Mutant<Schema> mutant : mutants) {
            print(writer, mutant);
        }
    }
    
    private void print(SQLWriter writer, Mutant<Schema> mutant) {
        System.out.println(mutant.getIdentifier());
        System.out.println(mutant.getSimpleDescription());
        System.out.println(mutant.getDescription());
        System.out.println("Type: " + mutant.getMutantType());
        List<String> stmts = writer.writeCreateTableStatements(mutant.getMutatedArtefact());
        for (String stmt : stmts) {
            System.out.println(stmt);
        }
        System.out.println();
    }
    
    /**
     * Instantiate the schema from the given case study name.
     * 
     * @param name The case study name
     * @return The schema instance
     */
    private Schema instantiateSchema(String name) {
        // Get the required schema class
        try {
            return (Schema) Class.forName("parsedcasestudy." + name).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private SQLWriter instantiateDBMSWriter(String name) {
        DBMS dbmsInstance = DBMSFactory.instantiate(name);
        return dbmsInstance.getSQLWriter();
    }
    
    /**
     * Generates mutants of the instantiated schema using the named pipeline.
     *
     * @return The mutants
     */
    private List<Mutant<Schema>> generateMutants(Schema schema, String dbms) {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }
    
    public static void main(String[] args) {
        new PrintMutants().run(args);
    }
    
}
