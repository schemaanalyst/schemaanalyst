package org.schemaanalyst.mutation.analysis.util;


import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceWithNotNullCheckChecker;
import org.schemaanalyst.mutation.operator.*;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyUniqueOverlapConstraintRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.util.ArrayList;
import java.util.List;

/*
 */
/**
 * <p>
 *
 * </p>
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class HigherOrderMutationExperiment extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;

    @Override
    protected void task() {
        CSVResult result = new CSVResult();
        
        // Get the required schema class
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        result.addValue("casestudy", casestudy);

        // 1st Order
        MutationPipeline<Schema> firstPipe = new FirstPipe(schema);
        List<Mutant<Schema>> mutants = firstPipe.mutate();
        result.addValue("firstorder", mutants.size());
        
        // 2nd Order reduced
        List<Mutant<Schema>> reducedFinalMutants = new ArrayList<>(mutants);
        for (Mutant<Schema> mutant : mutants) {
            MutationPipeline<Schema> secondPipe = new SecondPipeReduced(mutant.getMutatedArtefact());
            reducedFinalMutants.addAll(secondPipe.mutate());
        }
        
        // 2nd Order non-reduced
        List<Mutant<Schema>> nonReducedFinalMutants = new ArrayList<>(mutants);
        for (Mutant<Schema> mutant : mutants) {
            MutationPipeline<Schema> secondPipe = new SecondPipe(mutant.getMutatedArtefact());
            nonReducedFinalMutants.addAll(secondPipe.mutate());
        }
        
        result.addValue("secondorder", nonReducedFinalMutants.size());
        result.addValue("secondorder-reduced", reducedFinalMutants.size());
        
        CSVFileWriter writer = new CSVFileWriter("higherorder",",");
        writer.write(result);
    }

    @Override
    protected void validateParameters() {
    }
    
    public static void main(String[] args) {
        new HigherOrderMutationExperiment().run(args);
    }

    private class FirstPipe extends MutationPipeline<Schema> {

        public FirstPipe(Schema schema) {
            addProducer(new CCNullifier(schema));
            addProducer(new FKCColumnPairR(schema));
            addProducer(new PKCColumnARE(schema));
            addProducer(new NNCAR(schema));
            addProducer(new UCColumnARE(schema));
        }
    }

    private class SecondPipeReduced extends MutationPipeline<Schema> {

        public SecondPipeReduced(Schema schema) {
            addProducer(new CCNullifier(schema));
            addProducer(new FKCColumnPairR(schema));
            addProducer(new PKCColumnARE(schema));
            addProducer(new NNCAR(schema));
            addProducer(new UCColumnARE(schema));

            addRemover(new PrimaryKeyColumnNotNullRemover());
            addRemover(new PrimaryKeyUniqueOverlapConstraintRemover());
            addRemover(new EquivalentMutantRemover<>(new SchemaEquivalenceWithNotNullCheckChecker(), schema));
            addRemover(new RedundantMutantRemover<>(new SchemaEquivalenceWithNotNullCheckChecker()));
        }
    }
    
    private class SecondPipe extends MutationPipeline<Schema> {

        public SecondPipe(Schema schema) {
            addProducer(new CCNullifier(schema));
            addProducer(new FKCColumnPairR(schema));
            addProducer(new PKCColumnARE(schema));
            addProducer(new NNCAR(schema));
            addProducer(new UCColumnARE(schema));
        }
    }
}
