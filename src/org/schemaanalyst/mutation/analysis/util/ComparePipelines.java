/*
 */
package org.schemaanalyst.mutation.analysis.util;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * {@link Runner} for comparing two {@link MutationPipeline} objects.
 * </p>
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
        HashSet<Schema> pipelineAMutantArtifactsSet = new HashSet<>(pipelineAMutantArtifacts);
        HashSet<Schema> pipelineBMutantArtifactsSet = new HashSet<>(pipelineBMutantArtifacts);
        addResultColumn("unique mutant count", pipelineAMutantArtifactsSet.size(), pipelineBMutantArtifactsSet.size());

//        System.out.println("A mutant count: " + pipelineAMutantArtifacts.size());
//        System.out.println("A mutant count (uniques): " + new HashSet<>(pipelineAMutantArtifacts).size());
//        System.out.println("B mutant count: " + pipelineBMutantArtifacts.size());
//        System.out.println("B mutant count: (uniques): " + new HashSet<>(pipelineBMutantArtifacts).size());
//        System.out.println("Artifact lists disjoint? " + Collections.disjoint(pipelineAMutantArtifacts, pipelineBMutantArtifacts));
//        
//        List<Schema> unionList = new ArrayList<>();
//        unionList.addAll(pipelineAMutantArtifacts);
//        for (Schema bArtifact : pipelineBMutantArtifacts) {
//            if (!unionList.contains(bArtifact)) {
//                unionList.add(bArtifact);
//            }
//        }
//        System.out.println("UnionList size: " + unionList.size());
//
//        HashSet<Integer> aHashes = new HashSet<>();
//        for (Schema aArtifact : pipelineAMutantArtifacts) {
//            aHashes.add(aArtifact.hashCode());
//        }
//        HashSet<Integer> bHashes = new HashSet<>();
//        for (Schema bArtifact : pipelineBMutantArtifacts) {
//            bHashes.add(bArtifact.hashCode());
//        }
//        System.out.println("aHashes.size() = " + aHashes.size());
//        System.out.println("bHashes.size() = " + bHashes.size());
//        System.out.println("aHashes disjoint bHashes? = " + Collections.disjoint(aHashes, bHashes));
//
//        int equalsAndHash = 0;
//        int equals = 0;
//        int hash = 0;
//        for (int i = 0; i < pipelineAMutantArtifacts.size(); i++) {
//            for (int j = 0; j < pipelineAMutantArtifacts.size(); j++) {
//                if (i != j) {
//                    Schema one = pipelineAMutantArtifacts.get(i);
//                    Schema two = pipelineAMutantArtifacts.get(j);
//                    if (one.equals(two) && one.hashCode() == two.hashCode()) {
//                        equalsAndHash++;
////                        System.out.println("Both match");
//                    } else if (one.equals(two)) {
////                        equals++;
////                        System.out.println("Equals match");
//                    } else if (one.hashCode() == two.hashCode()) {
//                        hash++;
////                        System.out.println("Hashes match");
//                    }
//                }
//            }
//        }
//        
//        System.out.println("equalsAndHash = " + equalsAndHash);
//        System.out.println("equals = " + equals);
//        System.out.println("hash = " + hash);

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
        CSVFileWriter writer = new CSVFileWriter("results" + File.separator + pipelineA + "-" + pipelineB + ".dat", ",");
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
            mutationPipeline = MutationPipelineFactory.<Schema>instantiate(pipeline, schema, "Postgres");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return mutationPipeline;
    }
}
