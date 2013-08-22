package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicable;

public class ChainedSupplier<A extends Duplicable<A>, I extends Duplicable<I>, C>
		implements Supplier<A, C> {

	private Supplier<A, I> topLevelSupplier;
	private Supplier<I, C> bottomLevelSupplier;
	
	private boolean initialised, bottomLevelInitialised, haveCurrent;
	
	public ChainedSupplier(Supplier<A, I> topLevelSupplier, Supplier<I, C> bottomLevelSupplier) {
		this.topLevelSupplier = topLevelSupplier;
		this.bottomLevelSupplier = bottomLevelSupplier;
		
		initialised = false;
		bottomLevelInitialised = false;
		haveCurrent = false;
	}
	
    /**
     * {@inheritDoc}
     */ 
    @Override
	public void initialise(A originalArtefact) {
		topLevelSupplier.initialise(originalArtefact);
		initialised = true;
		bottomLevelInitialised = false;
	}
	
	private void initialiseBottomLevel(I topLevelComponent) {
		bottomLevelSupplier.initialise(topLevelComponent);
		bottomLevelInitialised = true;		
	}

    /**
     * {@inheritDoc}
     */ 
    @Override
    public A getOriginalArtefact() {
        return topLevelSupplier.getOriginalArtefact();
    }   	
	
	/**
	 * {@inheritDoc}
	 */	
	public A makeDuplicate() {
		if (!haveCurrent()) {
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
        I duplicateComponent = topLevelSupplier.getDuplicateComponent();
        bottomLevelSupplier.setDuplicate(duplicateComponent);
    }	
	
	@Override
	public boolean hasNext() {
		if (!initialised) {
			return false;
		}
		
		while (!bottomLevelHasNext() && topLevelSupplier.hasNext()) {
			initialiseBottomLevel(topLevelSupplier.getNextComponent());			
		}
		
		return bottomLevelHasNext();
	}

	private boolean bottomLevelHasNext() {
		return bottomLevelInitialised && bottomLevelSupplier.hasNext();
	}
		
	@Override
	public C getNextComponent() {
		if (hasNext()) {
			haveCurrent = true;
			return bottomLevelSupplier.getNextComponent();
		} 		
		haveCurrent = false;
		return null;		
	}
	
	@Override
	public boolean haveCurrent() {
		return initialised && haveCurrent;
	}

	@Override
	public C getDuplicateComponent() {
        bottomLevelSupplier.makeDuplicate();
		return bottomLevelSupplier.getDuplicateComponent();
	}

	@Override
	public void putComponentBackInDuplicate(C component) {
		bottomLevelSupplier.putComponentBackInDuplicate(component);
	}
}
