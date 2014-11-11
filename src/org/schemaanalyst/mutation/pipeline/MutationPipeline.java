package org.schemaanalyst.mutation.pipeline;

import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    protected List<MutantProducer<A>> producers = new ArrayList<>();
    protected List<MutantRemover<A>> removers = new ArrayList<>();
    protected Map<Class, Integer> producerCounts = new HashMap<>();
    protected Map<Class, Integer> removerCounts = new HashMap<>();
    protected Map<Class,StopWatch> producerTimings = new HashMap<>();
    protected Map<Class,StopWatch> removerTimings = new HashMap<>();

    public void addProducer(MutantProducer<A> producer) {
        producers.add(producer);
    }

    public void addRemover(MutantRemover<A> remover) {
        removers.add(remover);
    }

    protected void addRemoverToFront(MutantRemover<A> remover) {
        removers.add(0, remover);
    }

    @Override
    public List<Mutant<A>> mutate() {
        resetCounts();
        List<Mutant<A>> mutants = new ArrayList<>();
        applyProducers(mutants);
        mutants = applyRemovers(mutants);
        addIdentifiers(mutants);
        return mutants;
    }

    private void applyProducers(List<Mutant<A>> mutants) {
        for (MutantProducer<A> producer : producers) {
            // Time the application of each producer
            StopWatch timer = new StopWatch();
            timer.start();
            List<Mutant<A>> producerMutants = producer.mutate();
            timer.stop();
            producerTimings.put(producer.getClass(), timer);
            
            // Record how many mutants were added by the operator
            int newMutants = producerMutants.size();
            Class producerClass = producer.getClass();
            // Following 2 lines are for compatibility with higher-order mutation
            int producerCount = producerCounts.containsKey(producerClass) ? producerCounts.get(producerClass) : 0;
            producerCounts.put(producerClass, producerCount + newMutants);

            // Store the name of the operator as the simple description
            String simpleDescription = producer.getClass().getSimpleName();
            for (Mutant<A> mutant : producerMutants) {
                mutant.setSimpleDescription(simpleDescription);
            }
            mutants.addAll(producerMutants);
        }
    }
    
    private List<Mutant<A>> applyRemovers(List<Mutant<A>> mutants) {
        for (MutantRemover<A> remover : removers) {
            int initialMutants = mutants.size();
            
            // Time the application of each remover
            StopWatch timer = new StopWatch();
            timer.start();
            mutants = remover.removeMutants(mutants);
            timer.stop();
            removerTimings.put(remover.getClass(), timer);
            
            // Record how many mutants were removed by the operator
            int removedMutants = initialMutants - mutants.size();
            Class removerClass = remover.getClass();
            int removerCount = removerCounts.containsKey(removerClass) ? removerCounts.get(removerClass) : 0;
            removerCounts.put(removerClass, removerCount + removedMutants);
        }
        return mutants;
    }
    
     private void addIdentifiers(List<Mutant<A>> mutants) {
         for (int i = 0; i < mutants.size(); i++) {
             Mutant<A> mutant = mutants.get(i);
             mutant.setIdentifier(i + 1);
         }
    }

    public Map<Class, Integer> getProducerCounts() {
        return producerCounts;
    }

    public Map<Class, Integer> getRemoverCounts() {
        return removerCounts;
    }
    
    public Map<Class, StopWatch> getProducerTimings() {
        return producerTimings;
    }

    public Map<Class, StopWatch> getRemoverTimings() {
        return removerTimings;
    }

    private void resetCounts() {
        producerCounts.clear();
        removerCounts.clear();
        producerTimings.clear();
        removerTimings.clear();
    }

}
