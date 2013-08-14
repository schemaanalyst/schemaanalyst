package org.schemaanalyst.mutation;

import org.schemaanalyst.util.Duplicable;

public abstract class ArtefactSupplier<A extends Duplicable<A>, C> {

	protected A originalArtefact, currentCopy;
	
	public ArtefactSupplier(A originalArtefact) {
		this.originalArtefact = originalArtefact;
	}
	
	public A getArtefact() {
		return originalArtefact;
	}
	
	public abstract C getNextComponent();
	
	public A getArtefactCopy() {
		return originalArtefact.duplicate();
	}
	
	public abstract C getComponentCopy();
	
	public abstract void putComponentBack(C component);	
}
