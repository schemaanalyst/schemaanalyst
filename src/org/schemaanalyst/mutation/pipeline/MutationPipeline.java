package org.schemaanalyst.mutation.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.equivalence.EquivalenceReducer;

public class MutationPipeline<A> implements MutantProducer<A> {

	private List<MutantProducer<A>> producers;
	private List<EquivalenceReducer<A>> reducers;
	
	public MutationPipeline() {
		this.producers = new ArrayList<MutantProducer<A>>();
		this.reducers = new ArrayList<EquivalenceReducer<A>>();
	}
	
	public void addProducer(MutantProducer<A> producer) {
		producers.add(producer);
	}
	
	public void addReducer(EquivalenceReducer<A> reducer) {
		reducers.add(reducer);
	}
		
	@Override
	public List<Mutant<A>> mutate() {
		List<Mutant<A>> mutants = new ArrayList<Mutant<A>>();
		for (MutantProducer<A> producer : producers) {
			mutants.addAll(producer.mutate());
		}		
		for (EquivalenceReducer<A> reducer : reducers) {
			mutants = reducer.reduce(mutants);
		}				
		return mutants;
	}
}
