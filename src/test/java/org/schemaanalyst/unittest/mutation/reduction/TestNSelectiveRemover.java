/*
 */
package org.schemaanalyst.unittest.mutation.reduction;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.reduction.NSelectiveRemover;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TestNSelectiveRemover {

    MutantProducer<Integer> prod1 = new MutantProducer<Integer>() {

        @Override
        public List<Mutant<Integer>> mutate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    MutantProducer<Integer> prod2 = new MutantProducer<Integer>() {

        @Override
        public List<Mutant<Integer>> mutate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    MutantProducer<Integer> prod3 = new MutantProducer<Integer>() {

        @Override
        public List<Mutant<Integer>> mutate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    
    @Test
    public void testNoMutantsNoRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(0);
        List<Mutant<Integer>> result = remover.removeMutants(new ArrayList<Mutant<Integer>>());
        assertEquals("Removing mutants from 0 mutators should produce 0 mutants"
                + " if the list of mutants is empty", 0, result.size());
    }
    
    @Test
    public void testNoMutantsOneRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(1);
        List<Mutant<Integer>> result = remover.removeMutants(new ArrayList<Mutant<Integer>>());
        assertEquals("Removing mutants from 1 mutator should produce 0 mutants"
                + " if the list of mutants is empty", 0, result.size());
    }
    
    @Test
    public void testOneMutatorNoRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(0);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 0 mutators should produce 3 mutants"
                + " if the list of mutants has size 3", 3, result.size());
    }
    
    @Test
    public void testOneMutatorOneRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(1);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 1 mutators should produce 0 mutants"
                + " if all mutants are from that producer", 0, result.size());
    }
    
    @Test
    public void testOneMutatorTwoRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(2);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 2 mutators should produce 0 mutants"
                + " if all mutants are from one producer", 0, result.size());
    }
    
    @Test
    public void testTwoMutatorOneRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(1);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod2)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 1 mutators should produce 1 mutants"
                + " if all but 1 mutants are from that producer", 1, result.size());
    }
    
    @Test
    public void testTwoMutatorTwoRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(2);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod2)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 2 mutators should produce 1 mutants"
                + " if all mutants are from those producers", 0, result.size());
    }
    
    @Test
    public void testThreeMutatorOneRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(1);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1));
        mutants.add(makeMutant(4, prod2));
        mutants.add(makeMutant(5, prod2));
        mutants.add(makeMutant(6, prod3)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 1 mutators should produce 3 mutants"
                + " if count(prod1)=3, count(prod2)=2, count(prod3)=1 ",
                3, result.size());
    }
    
    @Test
    public void testThreeMutatorTwoRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(2);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1));
        mutants.add(makeMutant(4, prod2));
        mutants.add(makeMutant(5, prod2));
        mutants.add(makeMutant(6, prod3)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 2 mutators should produce 1 mutants"
                + " if count(prod1)=3, count(prod2)=2, count(prod3)=1 ",
                1, result.size());
        assertSame("The remaining mutant should be the one produced by prod3",
                mutants.get(5), result.get(0));
    }
    
    @Test
    public void testThreeMutatorThreeRemoval() {
        NSelectiveRemover<Integer> remover = new NSelectiveRemover<>(3);
        ArrayList<Mutant<Integer>> mutants = new ArrayList<>();
        mutants.add(makeMutant(1, prod1));
        mutants.add(makeMutant(2, prod1));
        mutants.add(makeMutant(3, prod1));
        mutants.add(makeMutant(4, prod2));
        mutants.add(makeMutant(5, prod2));
        mutants.add(makeMutant(6, prod3)); 
        List<Mutant<Integer>> result = remover.removeMutants(mutants);
        assertEquals("Removing mutants from 3 mutators should produce 0 mutants"
                + " if count(prod1)=3, count(prod2)=2, count(prod3)=1 ",
                0, result.size());
    }
    
    private Mutant<Integer> makeMutant(Integer i, MutantProducer producer) {
        Mutant<Integer> mutant = new Mutant<>(i, "");
        mutant.setMutantProducer(producer);
        return mutant;
    }
}
