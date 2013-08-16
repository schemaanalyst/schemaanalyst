package org.schemaanalyst.mutation.mutator;

import java.util.Set;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.artefactsupplier.ArtefactSupplier;
import org.schemaanalyst.util.Duplicable;

public abstract class Mutator<A extends Duplicable<A>, C> {
	
	protected ArtefactSupplier<A, C> artefactSupplier;
	
	public Mutator(ArtefactSupplier<A, C> artefactSupplier) {
		this.artefactSupplier = artefactSupplier;
	}
	
	public abstract Set<Mutant<A>> mutate();
	
	public String toString() {
		return getClass().getSimpleName() + " with " + artefactSupplier;
	}
}
