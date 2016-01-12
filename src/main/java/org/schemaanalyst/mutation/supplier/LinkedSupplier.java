package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;

/**
 * Links two suppliers together.  A "top level" supplier provides components which
 * become the artefacts for a "bottom level" which provides further sub-components.
 * The class is useful for re-using suppliers that operate at different levels of a
 * structure.
 * 
 * @author Phil McMinn
 *
 * @param <A>
 *            the type of the original artefact being mutated (e.g.
 *            {@link org.schemaanalyst.sqlrepresentation.Schema})
 * @param <I>
 *            the type of the intermediary objects which are components of
 *            the top level supplier {@link org.schemaanalyst.sqlrepresentation.Table}), 
 *            recycled as artefacts for the bottom levelfrom which components are
 *            being extracted (e.g. {@link org.schemaanalyst.sqlrepresentation.Column}).
 *            
 * @param <C>
 *            the type of the component to be mutated (e.g. a list of columns in
 *            a <tt>PRIMARY KEY</tt>)
 */
public class LinkedSupplier<A, I, C> implements Supplier<A, C> {

	private Supplier<A, I> topLevelSupplier;
	private Supplier<I, C> bottomLevelSupplier;
	
	private boolean initialised, haveCurrent;
	private I currentTopLevelComponentDuplicate;
	
    /**
     * Constructor.
     * @param topLevelSupplier the "top level" supplier, which provides components
     * that are full level artefacts for the bottom level
     * @param bottomLevelSupplier the "bottom level" supplier 
     */	
	public LinkedSupplier(Supplier<A, I> topLevelSupplier, Supplier<I, C> bottomLevelSupplier) {
		this.topLevelSupplier = topLevelSupplier;
		this.bottomLevelSupplier = bottomLevelSupplier;		
		initialised = false;
		haveCurrent = false;
	}
	
    /**
     * {@inheritDoc}
     */ 
    @Override
	public void initialise(A originalArtefact) {
		topLevelSupplier.initialise(originalArtefact);
		initialised = true;
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
    public A getOriginalArtefact() {
        A a = topLevelSupplier.getOriginalArtefact();
        return a;
    }   	
	
	/**
	 * {@inheritDoc}
	 */	
	public A makeDuplicate() {
		if (!hasCurrent()) {
			throw new MutationException(
					"There is no current component to mutate");
		}
		A duplicate = topLevelSupplier.makeDuplicate();
		setDuplicate(duplicate);
		return duplicate;
	}	
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuplicate(A duplicate) {
        topLevelSupplier.setDuplicate(duplicate);
        currentTopLevelComponentDuplicate = topLevelSupplier.getDuplicateComponent();
        bottomLevelSupplier.setDuplicate(currentTopLevelComponentDuplicate);
    }	
	
    /**
     * {@inheritDoc}
     */
	@Override
	public boolean hasNext() {
		if (!isInitialised()) {
			throw new MutationException("Supplier has not been initialised");
		}
		
		while (!bottomLevelHasNext() && topLevelSupplier.hasNext()) {
			bottomLevelSupplier.initialise(topLevelSupplier.getNextComponent());
		}
		
		return bottomLevelHasNext();
	}

    /**
     * Checks if the bottom level supplier has another component
     */
	private boolean bottomLevelHasNext() {
		return bottomLevelSupplier.isInitialised() && bottomLevelSupplier.hasNext();
	}
		
    /**
     * {@inheritDoc}
     */
	@Override
	public C getNextComponent() {
		if (hasNext()) {
			haveCurrent = true;
			return bottomLevelSupplier.getNextComponent();
		} 		
		haveCurrent = false;
		return null;		
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public boolean hasCurrent() {
		if (!isInitialised()) {
			throw new MutationException("Supplier has not been initialised");
		}		
		return haveCurrent;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public C getDuplicateComponent() {
		return bottomLevelSupplier.getDuplicateComponent();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void putComponentBackInDuplicate(C component) {
		bottomLevelSupplier.putComponentBackInDuplicate(component);
		topLevelSupplier.putComponentBackInDuplicate(currentTopLevelComponentDuplicate);
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ChainedSupplier with " + topLevelSupplier + " and " + bottomLevelSupplier;
    }	
}
