package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Given a list of elements and a list of alternative elements, this mutator 
 * returns mutants with each element in the list replaced with each alternative.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <A> The artefact type
 * @param <E> The element type
 */
public class ListElementExchanger<A, E> extends Mutator<A, Pair<List<E>>> {
    
    private List<E> elements;
    private List<E> alternatives;
    private int elementsIndex, alternativesIndex;    
    private String mutationDescription;
    
    public ListElementExchanger(Supplier<A, Pair<List<E>>> supplier) {
        super(supplier);
    }

    @Override
    protected void initialise(Pair<List<E>> pair) {
        this.elements = pair.getFirst();
        this.alternatives = pair.getSecond();
        elementsIndex = 0;
        alternativesIndex = 0;
    }
    
    @Override
    protected boolean isMoreMutationToDo() {
        if (elementsIndex < elements.size()) {
            return alternativesIndex < alternatives.size();
        } else {
            return false;
        }
    }
    
    @Override
    protected Pair<List<E>> performMutation(Pair<List<E>> componentToMutate) {
        E element = elements.get(elementsIndex);
        E alternative = alternatives.get(alternativesIndex);
        
        mutationDescription = "Exchanged " + element + " with " + alternative;
        
        List<E> mutatedElements = new ArrayList<>();
        for (E currentElement : elements) {
            if (currentElement.equals(element)) {
                mutatedElements.add(alternative);
            } else {
                mutatedElements.add(currentElement);
            }
        }

        // iterate to the next mutation
        alternativesIndex ++;
        if (alternativesIndex >= alternatives.size()) {
            elementsIndex ++;
            alternativesIndex = 0;
        }
        
        return new Pair<>(mutatedElements, null);
    }
    
    @Override
    protected String getDescriptionOfLastMutation() {
        return mutationDescription;
    }
}