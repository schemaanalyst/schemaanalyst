package org.schemaanalyst.mutation2.mutators;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation2.ArtefactSupplier;
import org.schemaanalyst.mutation2.Mutator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.util.Duplicable;

public class ColumnListMutator<A extends Duplicable<A>> extends Mutator<A, List<Column>> {
	
	public ColumnListMutator(ArtefactSupplier<A, List<Column>> artefactSupplier) {
		super(artefactSupplier);
	}

	@Override
	public List<A> mutate() {
		List<A> mutants = new ArrayList<>();
	
		boolean stoppingCondition = false;
		while (!stoppingCondition) {
			
			// get hold of the next artefact we wish to mutate, and the
			// specific subcomponent which we actually want to modify
			List<Column> nextComponent = artefactSupplier.getNextComponent();
			
			boolean innerStoppingCondition = false;
			while (!innerStoppingCondition) {
				A artefactCopy = artefactSupplier.getArtefactCopy();
				List<Column> columnsCopy = artefactSupplier.getComponentCopy(); 
				
				// do mutation
				List<Column> mutatedComponent = columnsCopy;
				
				// put the mutated component back in the artefact
				artefactSupplier.putComponentBack(mutatedComponent);
				
				// add the artefact to the list of mutants
				mutants.add(artefactCopy);
			}
			
		}	
		return mutants;
	}

}
