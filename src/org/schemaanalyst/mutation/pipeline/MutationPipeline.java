package org.schemaanalyst.mutation.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;

/**
 * <p>
 * A {@link MutationPipeline} is something capable of using a series of mutation
 * operators to produce a series of mutants, and then remove mutants by invoking
 * {@link org.schemaanalyst.mutation.pipeline.MutantRemover}s.
 * </p>
 *
 * @author Phil McMinn
 *
 * @param <A> The class of the artefact to be mutated by the pipeline.
 */
public class MutationPipeline<A> implements MutantProducer<A> {

    protected List<MutantProducer<A>> producers;
    protected List<MutantRemover<A>> remover;

    public MutationPipeline() {
        this.producers = new ArrayList<>();
        this.remover = new ArrayList<>();
    }

    public void addProducer(MutantProducer<A> producer) {
        producers.add(producer);
    }

    public void addRemover(MutantRemover<A> reducer) {
        remover.add(reducer);
    }

    protected void addRemoverToFront(MutantRemover<A> reducer) {
        remover.add(0, reducer);
    }

    @Override
    public List<Mutant<A>> mutate() {
        List<Mutant<A>> mutants = new ArrayList<>();
        for (MutantProducer<A> producer : producers) {
            List<Mutant<A>> producerMutants = producer.mutate();
            String simpleDescription = producer.getClass().getSimpleName();
            for (Mutant<A> mutant : producerMutants) {
                mutant.setSimpleDescription(simpleDescription);
            }
            mutants.addAll(producerMutants);
        }
        for (MutantRemover<A> reducer : remover) {
            mutants = reducer.removeMutants(mutants);
        }
        return mutants;
    }
}
