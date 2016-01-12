package org.schemaanalyst.mutation.analysis.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
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
 * Prints the detailed description of specific numbered mutants of a given schema.
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy ids")
public class MutantReporter extends Runner {
    
    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    
    /**
     * The comma-separated list of mutants to print details of (starting from 1)
     */
    @Parameter("The comma-separated list of mutants to print details of (starting from 1)")
    protected String ids;
    
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithClassifiers";

    private static final Logger LOGGER = Logger.getLogger(MutantReporter.class.getName());
    
    @Override
    protected void task() {
        report(casestudy, databaseConfiguration.getDbms(), ids);
    }
    
    public void report(String casestudy, String dbms, String ids) {
        // Instantiate configured options and generate mutants
        Schema schema = instantiateSchema(casestudy);
        SQLWriter writer = instantiateDBMSWriter(dbms);
        List<Mutant<Schema>> mutants = generateMutants(schema, dbms);
        
        // Iterate mutants and print
        for (String idString : ids.split(",")) {
            int id = Integer.valueOf(idString);
            int index = id - 1;
            if (index < mutants.size()) {
                Mutant<Schema> mutant = mutants.get(index);
                print(id,writer,mutant);
            } else {
                LOGGER.log(Level.SEVERE, "Mutant ID out of bounds, skipping (index {0} from list of {1})", new Object[] {index, mutants.size()});
            }
        }
    }
    
    public void reportToFile(String casestudy, String dbms, String ids, String path) {
        // Instantiate configured options and generate mutants
        Schema schema = instantiateSchema(casestudy);
        SQLWriter writer = instantiateDBMSWriter(dbms);
        List<Mutant<Schema>> mutants = generateMutants(schema, dbms);
        
        // Iterate mutants and print
        for (String idString : ids.split(",")) {
            int id = Integer.valueOf(idString);
            int index = id - 1;
            if (index < mutants.size()) {
                Mutant<Schema> mutant = mutants.get(index);
                print(id,writer,mutant,path + id);
            } else {
                LOGGER.log(Level.SEVERE, "Mutant ID out of bounds, skipping (index {0} from list of {1})", new Object[] {index, mutants.size()});
            }
        }
    }

    @Override
    protected void validateParameters() {
        // Do nothing
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
        DBMS dbms = DBMSFactory.instantiate(name);
        return dbms.getSQLWriter();
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

    private void print(int id, SQLWriter writer, Mutant<Schema> mutant) {
        System.out.println(id);
        System.out.println(mutant.getSimpleDescription());
        System.out.println(mutant.getDescription());
        List<String> stmts = writer.writeCreateTableStatements(mutant.getMutatedArtefact());
        for (String stmt : stmts) {
            System.out.println(stmt);
        }
        System.out.println(StringUtils.repeat("-", 30));
    }
    
    private void print(int id, SQLWriter writer, Mutant<Schema> mutant, String file) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(mutant.getSimpleDescription());
            output.newLine();
            output.newLine();
            output.write(mutant.getDescription());
            output.newLine();
            output.newLine();
            List<String> stmts = writer.writeCreateTableStatements(mutant.getMutatedArtefact());
            for (String stmt : stmts) {
                output.write(stmt);
            }
            output.newLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args) {
        new MutantReporter().run(args);
    }
    
}
