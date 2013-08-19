package org.schemaanalyst.mutation.artefactsupplier;

import java.util.List;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicable;

public abstract class IteratingSupplier<A extends Duplicable<A>, I, C> extends Supplier<A, C> {

    private List<I> intermediaries, duplicateIntermediaries;
    private int index;
    
    public IteratingSupplier(A originalArtefact) {
        super(originalArtefact);
        intermediaries = getIntermediaries(originalArtefact);
        index = -1;
    }
    
    @Override
    public boolean hasNext() {
        return index + 1 < intermediaries.size();
    }
    
    @Override
    public boolean haveCurrent() {        
        return index >= 0 && index < intermediaries.size();
    }
        
    @Override
    public C getNextComponent() {
        if (hasNext()) { 
            index ++;
            return getComponentFromIntermediary(originalArtefact, intermediaries.get(index));
        } else {
            return null;
        }
    }

    @Override
    public A makeDuplicate() {
        super.makeDuplicate();
        duplicateIntermediaries = getIntermediaries(currentDuplicate);        
        return currentDuplicate;
    }     
    
    @Override
    public C getDuplicateComponent() {
        if (!haveCurrent()) {
            throw new MutationException("No current component to mutate");
        }   
        return getComponentFromIntermediary(currentDuplicate, duplicateIntermediaries.get(index));
    }
    
    public I getDuplicateIntermediary() {
        return duplicateIntermediaries.get(index);
    }
    
    protected abstract List<I> getIntermediaries(A artefact);
    
    protected abstract C getComponentFromIntermediary(A artefact, I item);
    
    public abstract void putComponentBackInDuplicate(C component);
}
