package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.util.Duplicator;

public abstract class SolitaryComponentSupplier<A, C> extends AbstractSupplier<A, C> {

    private boolean hasNext;

    public SolitaryComponentSupplier() {
        this(null);
    }
    
    public SolitaryComponentSupplier(Duplicator<A> duplicator) {
        super(duplicator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise(A originalArtefact) {
        super.initialise(originalArtefact);
        hasNext = true;
    }    
    
    @Override
    public boolean hasNext() {
        return isInitialised() && hasNext;
    }

    @Override
    public C getNextComponent() {
        if (hasNext) {
            hasNext = false;
            haveCurrent = true;
            return getComponent(originalArtefact);
        } else {
            haveCurrent = false;
            return null;
        }
    }

    @Override
    public C getDuplicateComponent() {
        return getComponent(currentDuplicate);
    }

    protected abstract C getComponent(A artefact);
}
