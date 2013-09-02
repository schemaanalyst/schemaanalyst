/*
 */
package org.schemaanalyst.mutation.analysis.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy pipelineA pipelineB")
public class ComparePipelines extends Runner {

    private final static Logger LOGGER = Logger.getLogger(ComparePipelines.class.getName());
    
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    @Parameter("The first pipeline to compare.")
    protected String pipelineA;
    @Parameter("The second pipeline to compare.")
    protected String pipelineB;
    List<CSVResult> results;
    
    @Override
    protected void task() {
        // Get the required schema class
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        
        // Get the mutation pipelines and generate mutants
        MutationPipeline<Schema> mutationPipelineA;
        try {
            mutationPipelineA = MutationPipelineFactory.<Schema>instantiate(pipelineA, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> pipelineAMutants = mutationPipelineA.mutate();
        
        // Get the mutation pipelines and generate mutants
        MutationPipeline<Schema> mutationPipelineB;
        try {
            mutationPipelineB = MutationPipelineFactory.<Schema>instantiate(pipelineB, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> pipelineBMutants = mutationPipelineB.mutate();
        
        // Perform comparisons
        results = new ArrayList<>();
        addResultRow("pipeline", pipelineA, pipelineB);
        addResultRow("mutant count", pipelineAMutants.size(), pipelineBMutants.size());
        
        // Write results
        CSVWriter writer = new CSVWriter("results" + File.separator + casestudy + "-" + pipelineA + "-" + pipelineB + ".dat");
        for (CSVResult result : results) {
            writer.write(result);
        }
    }
    
    private void addResultRow(String category, Object a, Object b) {
        CSVResult result = new CSVResult();
        result.addValue("category", category);
        result.addValue("A", a);
        result.addValue("B", b);
        results.add(result);
    }

    @Override
    protected void validateParameters() {
        
    }
    
    public static void main(String[] args) {
        new ComparePipelines().run(args);
    }
    
}
