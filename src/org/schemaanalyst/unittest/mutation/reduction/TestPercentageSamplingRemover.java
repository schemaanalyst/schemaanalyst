/*
 */

package org.schemaanalyst.unittest.mutation.reduction;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.reduction.PercentageSamplingRemover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chris J. Wright
 */
public class TestPercentageSamplingRemover {
    
    private class MockPipeline extends MutationPipeline<Integer>{

        public MockPipeline(MutantRemover<Integer> remover) {
            addProducer(new MockProducer());
            addRemover(remover);
        }
        
        private class MockProducer implements MutantProducer<Integer> {

            @Override
            public List<Mutant<Integer>> mutate() {
                List<Mutant<Integer>> mutants = new ArrayList<>();
                for (Integer integer : Arrays.asList(1,2,3,4,5,6,7,8,9,10)) {
                    mutants.add(new Mutant<>(integer,""));
                }
                return mutants;
            }
            
        }
        
    }
    
    @Test
    public void testEmptySample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(0));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 0% should produce 0 mutants",
                mutants.size(), 0);
    }
    
    @Test
    public void testRoundingDownSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(0.14));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 14% should produce 1 mutants",
                mutants.size(), 1);
    }
    
    @Test
    public void testRoundingUpSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(0.16));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 16% should produce 2 mutants",
                mutants.size(), 2);
    }
    
    @Test
    public void testSmallSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(0.20));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 20% should produce 2 mutants",
                mutants.size(), 2);
    }
    
    @Test
    public void testLargeSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(0.80));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 80% should produce 8 mutants",
                mutants.size(), 8);
    }
    
    @Test
    public void testFullSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(1));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 100% should produce 10 mutants",
                mutants.size(), 10);
    }
    
    @Test
    public void testOverfullSample() {
        MockPipeline pipeline = new MockPipeline(new PercentageSamplingRemover(1.50));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 150% should produce 10 mutants if there "
                + "are only 10 mutants produced",
                mutants.size(), 10);
    }
}
