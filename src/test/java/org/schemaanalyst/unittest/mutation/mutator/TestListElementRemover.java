package org.schemaanalyst.unittest.mutation.mutator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.util.Duplicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestListElementRemover {

    class MockOuterObject {
                
        List<Integer> elements = new ArrayList<>();
        
        public MockOuterObject(List<Integer> elements) {
            for (Integer element : elements) {
                this.elements.add(new Integer(element));
            }
        }
        
        public MockOuterObject duplicate() {
            return new MockOuterObject(elements);
        }        
        
        public String toString() {
            return elements.toString();
        }
    }
    
    public class ListSupplier extends SolitaryComponentSupplier<MockOuterObject, List<Integer>> {
        
        public ListSupplier() {
            super(new Duplicator<MockOuterObject>() {
                @Override
                public MockOuterObject duplicate(MockOuterObject mockOuterObject) {
                    return mockOuterObject.duplicate();
                }
            });
        }

        @Override
        public void putComponentBackInDuplicate(List<Integer> elements) {
            currentDuplicate.elements = elements;         
        }

        @Override
        protected List<Integer> getComponent(MockOuterObject artefact) {
            return artefact.elements;
        }
    }    
    
    @Test 
    public void testOneElementList() {
        List<Integer> elements = Arrays.asList(1);
        MockOuterObject mockOuterObject = new MockOuterObject(elements);
        ListSupplier listSupplier = new ListSupplier();
        listSupplier.initialise(mockOuterObject);        

        ListElementRemover<MockOuterObject, Integer> mutator = new ListElementRemover<>(listSupplier);
        List<Mutant<MockOuterObject>> mutants = mutator.mutate();
        
        assertEquals(
                "There should be 1 mutant produced",
                1, mutants.size());
        
        assertEquals(
                "The mutant should be the empty list as the element 1 has been removed",
                0, mutants.get(0).getMutatedArtefact().elements.size());
    }
    
    @Test 
    public void testTwoElementList() {
        List<Integer> elements = Arrays.asList(1, 2);
        MockOuterObject mockOuterObject = new MockOuterObject(elements);
        ListSupplier listSupplier = new ListSupplier();
        listSupplier.initialise(mockOuterObject);
        
        ListElementRemover<MockOuterObject, Integer> mutator = new ListElementRemover<>(listSupplier);
        List<Mutant<MockOuterObject>> mutants = mutator.mutate();
        
        assertEquals(
                "There should be 2 mutants produced",
                2, mutants.size());
        
        assertEquals(
                "The first mutant should be of size 1 as the element 1 has been removed",
                1, mutants.get(0).getMutatedArtefact().elements.size());

        assertEquals(
                "The first mutant should be the list [2]",
                new Integer(2), mutants.get(0).getMutatedArtefact().elements.get(0));        
        
        assertEquals(
                "The first mutant should be of size 1 as the element 2 has been removed",
                1, mutants.get(1).getMutatedArtefact().elements.size());
        
        assertEquals(
                "The second mutant should be the list [1]",
                new Integer(2), mutants.get(0).getMutatedArtefact().elements.get(0));            
    }    
}
