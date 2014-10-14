/*
 */

package org.schemaanalyst.unittest.mutation.reduction;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.reduction.SamplingRemover;

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
public class TestSamplingRemover {
    
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
        MockPipeline pipeline = new MockPipeline(new SamplingRemover(0));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 0 should produce 0 mutants",
                0, mutants.size());
    }
    
    @Test
    public void testSmallSample() {
        MockPipeline pipeline = new MockPipeline(new SamplingRemover(2));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 2 should produce 2 mutants",
                2, mutants.size());
    }
    
    @Test
    public void testLargeSample() {
        MockPipeline pipeline = new MockPipeline(new SamplingRemover(9));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 9 should produce 9 mutants",
                9, mutants.size());
    }
    
    @Test
    public void testFullSample() {
        MockPipeline pipeline = new MockPipeline(new SamplingRemover(10));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 10 should produce 10 mutants",
                10, mutants.size());
    }
    
    @Test
    public void testOverfullSample() {
        MockPipeline pipeline = new MockPipeline(new SamplingRemover(100));
        List<Mutant<Integer>> mutants = pipeline.mutate();
        assertEquals("A sample size of 100 should produce 10 mutants when "
                + "there are only 10 mutants produced", 10, mutants.size());
    }
}
