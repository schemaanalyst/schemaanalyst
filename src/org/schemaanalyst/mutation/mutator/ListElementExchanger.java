package org.schemaanalyst.mutation.mutator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.artefactsupplier.Supplier;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.Pair;

public class ListElementExchanger<A extends Duplicable<A>, E> extends Mutator<A, Pair<List<E>>> {
    
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
                mutatedElements.add(element);
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
    protected String getMutationDescription() {
        return toString() + " - " + mutationDescription;
    }
}