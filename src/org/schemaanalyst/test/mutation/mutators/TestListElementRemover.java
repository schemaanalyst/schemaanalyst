package org.schemaanalyst.test.mutation.mutators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.artefactsupplier.Supplier;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.util.Duplicable;

import static org.junit.Assert.*;

public class TestListElementRemover {

    class MockOuterObject implements Duplicable<MockOuterObject> {
        
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
    
    public class ListSupplier extends Supplier<MockOuterObject, List<Integer>> {

        boolean hasNext = true, haveCurrent = false;
        
        public ListSupplier(MockOuterObject originalArtefact) {
            super(originalArtefact);
        }
        
        public boolean hasNext() {
            return hasNext;
        }
        
        public boolean haveCurrent() {
            return haveCurrent;
        }
        
        public List<Integer> getNextComponent() {
            hasNext = false;
            haveCurrent = true;
            return originalArtefact.elements; 
        }
        
        public List<Integer> getDuplicateComponent() {
            return currentDuplicate.elements;
        }

        public void putComponentBackInDuplicate(List<Integer> elements) {
            currentDuplicate.elements = elements;         
        }
        
        public String toString() {
            return "ListSupplier";
        }
    }    
    
    @Test 
    public void testOneElementList() {
        List<Integer> elements = Arrays.asList(1);
        MockOuterObject mockOuterObject = new MockOuterObject(elements);
        ListSupplier listSupplier = new ListSupplier(mockOuterObject);
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
        ListSupplier listSupplier = new ListSupplier(mockOuterObject);
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
