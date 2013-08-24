package org.schemaanalyst.mutation.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;

public class MutationPipeline<A> extends MutantProducer<A> {

	private List<MutantProducer<A>> producers;
	
	public MutationPipeline() {
		this.producers = new ArrayList<MutantProducer<A>>();
	}
	
	public void add(MutantProducer<A> producer) {
		producers.add(producer);
	}
	
	@Override
	public List<Mutant<A>> mutate() {
		List<Mutant<A>> mutants = new ArrayList<Mutant<A>>();
		for (MutantProducer<A> producer : producers) {
			mutants.addAll(producer.mutate());
		}
		return mutants;
	}
}
