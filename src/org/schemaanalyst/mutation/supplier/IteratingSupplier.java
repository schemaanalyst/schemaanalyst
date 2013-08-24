package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicator;

public abstract class IteratingSupplier<A, C> extends AbstractSupplier<A, C> {

    private List<C> components, duplicateComponents;
    private int index;

    /**
     * Constructor.
     */
    public IteratingSupplier() {
        super();
    }

    /**
     * Constructor.
     * @param duplicator the Duplicator for instances of A. 
     */
    public IteratingSupplier(Duplicator<A> duplicator) {
        super(duplicator);
    }
    
    /**
     * {@inheritDoc}
     */
    public void initialise(A originalArtefact) {
        super.initialise(originalArtefact);
        components = getComponents(originalArtefact);
        index = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return initialised && index + 1 < components.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C getNextComponent() {
        boolean hasNext = hasNext();
        haveCurrent = hasNext;
        index++;
        if (hasNext) {
            return components.get(index);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuplicate(A currentDuplicate) {
        super.setDuplicate(currentDuplicate);
        duplicateComponents = getComponents(currentDuplicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public C getDuplicateComponent() {
        if (!haveCurrent()) {
            throw new MutationException("No current component to mutate");
        }
        return duplicateComponents.get(index);
    }

    /**
     * Gets the components from the artefact
     * @param artefact the artefact to get the components from
     * @return a list of the components
     */
    protected abstract List<C> getComponents(A artefact);
}
