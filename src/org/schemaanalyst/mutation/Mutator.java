package org.schemaanalyst.mutation;

import java.util.List;

import org.schemaanalyst.util.Duplicable;

public abstract class Mutator<A extends Duplicable<A>, C> {
	
	protected ArtefactSupplier<A, C> artefactSupplier;
	
	public Mutator(ArtefactSupplier<A, C> artefactSupplier) {
		this.artefactSupplier = artefactSupplier;
	}
	
	public abstract List<Mutant<A>> mutate();
	
	public String toString() {
		return getClass().getSimpleName() + " with " + artefactSupplier;
	}
}
