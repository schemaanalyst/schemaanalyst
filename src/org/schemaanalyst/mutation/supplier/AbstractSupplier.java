package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicator;

/**
 * Provides common basic supplier functionality for subclasses to re-use
 * 
 * @author Phil McMinn
 * 
 * @param <A>
 *            the type of artefact being mutated (e.g. a
 *            {@link org.schemaanalyst.sqlrepresentation.Schema})
 * @param <C>
 *            the type of component of the artefact being mutated (e.g. the
 *            columns of an integrity constraint)
 */
public abstract class AbstractSupplier<A, C> implements
        Supplier<A, C> {

    /**
     * The class that provides duplicates of A.
     */
    protected Duplicator<A> duplicator;
    
    /**
     * Instance of the <em>original artefact</em> being mutated. The original
     * instance is unchanged, it is duplicates of this instance that are
     * mutated.
     */
    protected A originalArtefact;

    /**
     * The current duplicate copy of <tt>originalArtefact</tt> which is to be
     * mutated.
     */
    protected A currentDuplicate;

    /**
     * Indicates whether the supplier has been initialised or not.
     */
    protected boolean initialised;

    /**
     * Indicates whether a current component is available for mutation or not.
     */
    protected boolean haveCurrent;

    /**
     * Constructor
     */
    public AbstractSupplier() {
        this(null);
    }
    
    /**
     * Constructor
     */
    public AbstractSupplier(Duplicator<A> duplicator) {
        this.duplicator = duplicator;
        initialised = false;        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise(A originalArtefact) {
        this.originalArtefact = originalArtefact;
        initialised = true;
        haveCurrent = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInitialised() {
        return initialised;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean haveCurrent() {
        return initialised && haveCurrent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A getOriginalArtefact() {
        if (!isInitialised()) {
            throw new MutationException("Supplier has not been initialised");
        }
        return originalArtefact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A makeDuplicate() {
        if (!haveCurrent()) {
            throw new MutationException(
                    "There is no current component to mutate");
        }
        if (duplicator == null) {
            throw new MutationException(
                    "Cannot make duplicates of an object as no Duplicator has been set");
        }
        setDuplicate(duplicator.duplicate(originalArtefact));
        return currentDuplicate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuplicate(A currentDuplicate) {
        if (!haveCurrent()) {
            throw new MutationException(
                    "There is no current component to mutate");
        }
        this.currentDuplicate = currentDuplicate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }    
}
