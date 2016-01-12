package org.schemaanalyst.mutation.analysis.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.MutationAnalysisVirtual;
import org.schemaanalyst.mutation.analysis.executor.testsuite.VirtualTestSuiteResult;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris J. Wright
 */
public class OutputMutantsPossiblyRedundant extends MutationAnalysisVirtual {

    @Override
    protected void task() {
        instantiateParameters();
        final TestSuite suite = instantiateTestSuite();
        final List<Mutant<Schema>> mutants = generateMutants();
        final VirtualTestSuiteResult originalResults = executeTestSuite(schema, suite);
        ListMultimap<VirtualTestSuiteResult, Mutant> results = findPossiblyRedundant(suite, mutants, originalResults);
        int index = 1;
        for (Map.Entry<VirtualTestSuiteResult, Collection<Mutant>> entrySet : results.asMap().entrySet()) {
            StringBuilder builder = new StringBuilder();
            Collection<Mutant> group = entrySet.getValue();
            if (group.size() != 1) {
                builder.append("Group ").append(index++);
                builder.append(": \n");
                for (Mutant mutant : group) {
                    builder.append("\tMutant ").append(mutant.getIdentifier());
                    builder.append(": ").append(mutant.getDescription());
                    builder.append(" ").append(mutant.getMutantType()).append("\n");
                }
                System.out.println(builder.toString());
            }
        }
    }

    @Override
    protected List<Mutant<Schema>> generateMutants() {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }

    protected ListMultimap<VirtualTestSuiteResult, Mutant> findPossiblyRedundant(TestSuite suite, List<Mutant<Schema>> mutants, VirtualTestSuiteResult originalResult) {
        ListMultimap<VirtualTestSuiteResult, Mutant> results = ArrayListMultimap.create();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            VirtualTestSuiteResult mutantResult = executeTestSuite(mutantSchema, suite);
            results.put(mutantResult, mutant);
        }
        return results;
    }

    public static void main(String[] args) {
        new OutputMutantsPossiblyRedundant().run(args);
    }
}
