package org.schemaanalyst.mutation.mutator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.Pair;

public class ListElementAdder<A extends Duplicable<A>, E> extends Mutator<A, Pair<List<E>>> {
    
    private List<E> elements;
    private List<E> alternatives;
    private Iterator<E> iterator;
    private String mutationDescription;
    
    public ListElementAdder(Supplier<A, Pair<List<E>>> supplier) {
        super(supplier);
    }

    @Override
    protected void initialise(Pair<List<E>> pair) {
        this.elements = pair.getFirst();
        this.alternatives = pair.getSecond();
        iterator = alternatives.iterator();
    }
    
    @Override
    protected boolean isMoreMutationToDo() {
        return iterator.hasNext();
    }
    
    @Override
    protected Pair<List<E>> performMutation(Pair<List<E>> componentToMutate) {
        E elementToAdd = iterator.next();
        mutationDescription = "Added " + elementToAdd;
        
        List<E> mutatedElements = new ArrayList<>(elements);
        mutatedElements.add(elementToAdd);
        
        return new Pair<>(mutatedElements, null);
    }
    
    @Override
    protected String getDescriptionOfLastMutation() {
        return mutationDescription;
    }
}
