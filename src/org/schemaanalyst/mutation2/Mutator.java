package org.schemaanalyst.mutation2;

import java.util.List;

import org.schemaanalyst.util.Duplicable;

public abstract class Mutator<A extends Duplicable<A>, C> {
	
	protected ArtefactSupplier<A, C> artefactSupplier;
	
	public Mutator(ArtefactSupplier<A, C> artefactSupplier) {
		this.artefactSupplier = artefactSupplier;
	}
	
	public abstract List<A> mutate();
}
