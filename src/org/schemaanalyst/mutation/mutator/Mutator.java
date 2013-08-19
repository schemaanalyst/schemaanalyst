package org.schemaanalyst.mutation.mutator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.util.Duplicable;

public abstract class Mutator<A extends Duplicable<A>, C> extends
        MutationPipeline<A> {

    protected Supplier<A, C> supplier;

    public Mutator(Supplier<A, C> supplier) {
        this.supplier = supplier;
    }

    public List<Mutant<A>> mutate() {
        // Create the collection in which to store created mutants.
        List<Mutant<A>> mutants = new ArrayList<>();

        // are there more components to mutate?
        while (supplier.hasNext()) {

            // get the reference component from the original artefact to mutate
            C component = supplier.getNextComponent();

            // perform any initialisation steps
            initialise(component);

            while (isMoreMutationToDo()) {
                // make a duplicate of the original artefact
                A duplicate = supplier.makeDuplicate();

                // get the component to be mutated from the duplicate
                C componentToMutate = supplier.getDuplicateComponent();

                // do the mutation
                C mutatedComponent = performMutation(componentToMutate);

                // put the mutated component back in the duplicate
                supplier.putComponentBackInDuplicate(mutatedComponent);

                // make a mutant from the duplicate
                Mutant<A> mutant = new Mutant<>(duplicate, getMutationDescription());

                // add it to the set of mutants
                mutants.add(mutant);
            }
        }

        return mutants;
    }

    protected abstract void initialise(C componentFromOriginalArtefact);

    protected abstract boolean isMoreMutationToDo();

    protected abstract C performMutation(C componentToMutate);

    protected abstract String getMutationDescription();

    public String toString() {
        return getClass().getSimpleName() + " with " + supplier;
    }
}
