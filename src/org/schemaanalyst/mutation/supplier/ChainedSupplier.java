package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.util.Duplicable;

public class ChainedSupplier<A extends Duplicable<A>, I extends Duplicable<I>, C>
		extends Supplier<A, C> {

	private Supplier<A, I> topLevelSupplier;
	private Supplier<I, C> bottomLevelSupplier;
	
	private boolean initialised, haveCurrent;
	
	public ChainedSupplier(Supplier<A, I> topLevelSupplier, Supplier<I, C> bottomLevelSupplier) {
		this.topLevelSupplier = topLevelSupplier;
		this.bottomLevelSupplier = bottomLevelSupplier;
		
		initialised = false;
		haveCurrent = false;
	}
	
	@Override
	public void initialise(A originalArtefact) {
		topLevelSupplier.initialise(originalArtefact);
		initialised = true;
	}
	
	@Override
	public boolean hasNext() {
		if (!initialised) {
			return false;
		}
		
		while (!bottomLevelHasNext() && topLevelSupplier.hasNext()) {
			I topLevelComponent = topLevelSupplier.getNextComponent();
			bottomLevelSupplier.initialise(topLevelComponent);
		}
		
		return bottomLevelHasNext();
	}

	private boolean bottomLevelHasNext() {
		return bottomLevelSupplier != null && bottomLevelSupplier.hasNext();
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
		return bottomLevelSupplier.getDuplicateComponent();
	}

	@Override
	public void putComponentBackInDuplicate(C component) {
		bottomLevelSupplier.putComponentBackInDuplicate(component);
	}
}
