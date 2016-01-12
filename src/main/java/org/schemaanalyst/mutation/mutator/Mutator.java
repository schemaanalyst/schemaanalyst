package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.supplier.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Mutator} takes a {@link Supplier} to generate duplicates of an
 * artefact and retrieve components from those duplications for mutation. In
 * particular, the {@link #mutate()} method implements the protocol for handling
 * a supplier. The class provides abstract methods which should be overridden in
 * order to actually perform mutation on those components.
 *
 * @author Phil McMinn
 *
 * @param <A> the type of the artefact being mutated.
 * @param <C> the type of component of the artefact which is to be mutated.
 */
public abstract class Mutator<A, C> {

    /**
     * The supplier for this mutator.
     */
    protected Supplier<A, C> supplier;

    /**
     * Constructor.
     *
     * @param supplier the supplier of duplicate artefacts and components for
     * this mutator.
     */
    public Mutator(Supplier<A, C> supplier) {
        this.supplier = supplier;
    }

    /**
     * Interacts with the supplier to mutate the components of its duplicates.
     */
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
                Mutant<A> mutant = new Mutant<>(duplicate, getOperatorInfo()
                        + " - " + getDescriptionOfLastMutation());

                // add it to the set of mutants
                mutants.add(mutant);
            }
        }

        return mutants;
    }

    /**
     * Performs any initialisation steps before the mutation using the component
     * from the original artefact (for example creating instance variables to
     * cache collections that are to be iterated over in
     * {@link #performMutation(Object)}. This component should not be changed,
     * only its duplicates in the {@link #performMutation} method.
     *
     * @param componentFromOriginalArtefact the component from the original
     * artefact.
     */
    protected abstract void initialise(C componentFromOriginalArtefact);

    /**
     * Returns true when all mutation has been exhausted for the current
     * component (that is, the object passed to {@link #initialise(Object)},
     * signalling that the next one should be obtained from the supplier.
     *
     * @return True if all mutation has been performed on the current component.
     */
    protected abstract boolean isMoreMutationToDo();

    /**
     * Performs mutation on a copy of the object originallly passed to
     * {@link #initialise(Object)}.
     *
     * @param componentToMutate the component to mutate.
     * @return the mutated component.
     */
    protected abstract C performMutation(C componentToMutate);

    /**
     * Returns a description of what occurred in the last call to
     * {@link #performMutation(Object)} used to accompany the generated mutants
     * returned from {@link #mutate()}.
     *
     * @return a descriptor string of the last mutation operation.
     */
    protected abstract String getDescriptionOfLastMutation();

    /**
     * Returns an information detailing the current mutator and the supplier
     * being used.
     *
     * @return a descriptor string.
     */
    public String getOperatorInfo() {
        return getClass().getSimpleName() + " with " + supplier;
    }

    @Override
    public String toString() {
        return getOperatorInfo();
    }
}
