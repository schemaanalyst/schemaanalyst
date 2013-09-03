/*
 */
package org.schemaanalyst.mutation.analysis.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
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
    @Parameter("The name of the schema to use, or multiple case studies "
            + "separated by the ':' character.")
    protected String casestudy;
    @Parameter("The first pipeline to compare.")
    protected String pipelineA;
    @Parameter("The second pipeline to compare.")
    protected String pipelineB;
    CSVResult pipelineAResult;
    CSVResult pipelineBResult;

    @Override
    protected void task() {
        String[] casestudies = casestudy.split(":");
        for (String schemaName : casestudies) {
            if (!schemaName.isEmpty()) {
                Schema schema = instantiateSchema(schemaName);
                compareWithSchema(schema);
            }
        }
    }

    private void compareWithSchema(Schema schema) {
        // Get the mutation pipelines and generate mutants
        MutationPipeline<Schema> mutationPipelineA = instantiatePipeline(schema, pipelineA);
        MutationPipeline<Schema> mutationPipelineB = instantiatePipeline(schema, pipelineB);
        List<Mutant<Schema>> pipelineAMutants = mutationPipelineA.mutate();
        List<Mutant<Schema>> pipelineBMutants = mutationPipelineB.mutate();

        // Perform comparisons
        pipelineAResult = new CSVResult();
        pipelineBResult = new CSVResult();

        addResultColumn("pipeline", pipelineA, pipelineB);
        addResultColumn("casestudy", schema, schema);
        addResultColumn("mutant count", pipelineAMutants.size(), pipelineBMutants.size());
        List<Schema> pipelineAMutantArtifacts = new ArrayList<>(pipelineAMutants.size());
        List<Schema> pipelineBMutantArtifacts = new ArrayList<>(pipelineBMutants.size());
        for (Mutant<Schema> mutant : pipelineAMutants) {
            pipelineAMutantArtifacts.add(mutant.getMutatedArtefact());
        }
        for (Mutant<Schema> mutant : pipelineBMutants) {
            pipelineBMutantArtifacts.add(mutant.getMutatedArtefact());
        }

        HashSet<Schema> union = new HashSet<>();
        union.addAll(pipelineAMutantArtifacts);
        union.addAll(pipelineBMutantArtifacts);
        addResultColumn("union", union.size(), union.size());

        HashSet<Schema> intersection = new HashSet<>();
        intersection.addAll(pipelineAMutantArtifacts);
        intersection.retainAll(pipelineBMutantArtifacts);
        addResultColumn("intersection", intersection.size(), intersection.size());

        HashSet<Schema> differenceAB = new HashSet<>();
        differenceAB.addAll(pipelineAMutantArtifacts);
        differenceAB.removeAll(pipelineBMutantArtifacts);
        HashSet<Schema> differenceBA = new HashSet<>();
        differenceBA.addAll(pipelineBMutantArtifacts);
        differenceBA.removeAll(pipelineAMutantArtifacts);
        addResultColumn("difference", differenceAB.size(), differenceBA.size());

        // Write results
        CSVWriter writer = new CSVWriter("results" + File.separator + pipelineA + "-" + pipelineB + ".dat");
        LOGGER.log(Level.FINE, "PipelineA: {0}", pipelineAResult);
        LOGGER.log(Level.FINE, "PipelineB: {0}", pipelineBResult);
        writer.write(pipelineAResult);
        writer.write(pipelineBResult);
    }

    private void addResultColumn(String category, Object a, Object b) {
        pipelineAResult.addValue(category, a);
        pipelineBResult.addValue(category, b);
    }

    @Override
    protected void validateParameters() {
    }

    public static void main(String[] args) {
        new ComparePipelines().run(args);
    }

    private static Schema instantiateSchema(String name) throws RuntimeException {
        Schema schema;
        try {
            schema = (Schema) Class.forName(name).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        return schema;
    }

    private MutationPipeline<Schema> instantiatePipeline(Schema schema, String pipeline) throws RuntimeException {
        // Get the mutation pipelines and generate mutants
        MutationPipeline<Schema> mutationPipeline;
        try {
            mutationPipeline = MutationPipelineFactory.<Schema>instantiate(pipeline, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return mutationPipeline;
    }
}
