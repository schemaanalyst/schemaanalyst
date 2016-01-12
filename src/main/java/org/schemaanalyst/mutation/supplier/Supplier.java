package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;

/**
 * <p>
 * {@link Supplier} and {@link org.schemaanalyst.mutation.mutator.Mutator} are
 * designed to separate the two concerns of implementing mutation in
 * SchemaAnalyst of 1) providing an algorithm underlying the mutation operator
 * (e.g. "removal elements one-by-one from a list") and 2) obtaining access to
 * the specific components in a duplicate of the original artefact that are
 * targeted for mutation (e.g. the primary key columns of a constraint, forming
 * part of a schema).
 * </p>
 * 
 * <p>
 * Typically, specific components are identified using the original version of
 * the artefact (the so-called <em>original artefact</em>), of which mutant
 * copies are required. The original artefact acts as a "reference"; in order to
 * actually mutate the duplicate the overall mutation algorithm has to navigate
 * to the <em>same</em> component in the duplicate as identified in this
 * original. It is this problem that {@link Supplier} handles. It duplicates the
 * original artefact and passes duplicates of the components to be mutated to
 * subclasses of {@link org.schemaanalyst.mutation.mutator.Mutator}, which is
 * responsible for actually performing the mutation steps.
 * </p>
 * 
 * <p>
 * Following construction, and a call to the {@link #initialise(Object)}
 * method to set the original artefact, {@link Supplier} is used as follows
 * (refer to the source code of
 * {@link org.schemaanalyst.mutation.mutator.Mutator#mutate()} for an actual
 * example). First, we check that there is a component in the artefact to
 * mutate, through a call to {@link #hasNext()}. If the method call returns
 * true, we get a reference version of the component to mutate from the original
 * artefact using {@link #getNextComponent()} (as the original, this object is
 * to be left unchanged). The caller is then free to call
 * {@link #makeDuplicate()} and {@link #getDuplicateComponent()} in sequence as
 * many times as is required to make mutants. After each component is mutated, a
 * call to {@link #putComponentBackInDuplicate(Object)} is required to insert
 * the mutated component back into the duplicate copy. Once a component is done
 * with mutation-wise, {@link #hasNext()} can be used to check for the existence
 * of more components to mutate, and so on.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <A>
 *            the type of artefact being mutated (e.g. a
 *            {@link org.schemaanalyst.sqlrepresentation.Schema})
 * @param <C>
 *            the type of component of the artefact being mutated (e.g. the
 *            columns of an integrity constraint)
 */
public interface Supplier<A, C> {

    /**
     * Initialises the supplier and sets the original artefact to be mutated and
     * used as a "reference" for mutations.
     * 
     * @param originalArtefact
     *            the artefact to be mutated.
     */
    public void initialise(A originalArtefact);

    /**
     * Indicates whether there the supplier has been initialised (through a call
     * to {@link #initialise(Object)}
     * 
     * @return True if the supplier has been initialised, else false;
     */
    public boolean isInitialised();

    /**
     * Returns the original artefact that is being mutated.
     * 
     * @return the original artefact.
     * @throws MutationException
     *             if the supplier has not been initialised (through a call to
     *             {@link #initialise(Object)}
     */
    public A getOriginalArtefact();

    /**
     * Indicates whether there are more components in the original artefact for
     * mutation.
     * 
     * @return True if there are more components, else false.
     * @throws MutationException
     *             if the supplier has not been initialised (through a call to
     *             {@link #initialise(Object)}
     */
    public boolean hasNext();

    /**
     * Returns the next component from the original artefact for mutation, or
     * null if there are no more components left to mutate (i.e. the result of
     * {@link #hasNext()} is false). The returned object is referred to as the
     * "current component".
     * 
     * @return The next component from the to be mutated.
     * @throws MutationException
     *             if the supplier has not been initialised (through a call to
     *             {@link #initialise(Object)}
     */
    public C getNextComponent();

    /**
     * Indicates whether there is a current component available for mutation
     * (following a call to {@link #getNextComponent()}</tt>).
     * 
     * @return True if there is a current component, else false.
     * @throws MutationException
     *             if the supplier has not been initialised (through a call to
     *             {@link #initialise(Object)} 
     */
    public boolean hasCurrent();

    /**
     * Returns a duplicate of the original artefact. If there is no current
     * component to mutate (i.e. a call to {@link #hasCurrent()} would return
     * false), the method throws a
     * {@link org.schemaanalyst.mutation.MutationException}.
     * 
     * @return A duplicate of the original artefact.
     * @throws MutationException
     *             if there is no current component to mutate, i.e. the result
     *             of {@link #hasCurrent()} before calling this method is
     *             false.
     */
    public A makeDuplicate();

    /**
     * Instead of having the supplier make its own duplicate, a duplicate can be
     * set from another source instead using this method. The result of
     * {@link #getDuplicateComponent()} is derived from the duplicate set rather
     * than the duplicate made.
     * 
     * @throws MutationException
     *             if there is no current component to mutate, i.e. the result
     *             of {@link #hasCurrent()} before calling this method is
     *             false.
     */
    public void setDuplicate(A duplicate);

    /**
     * Extracts the component for mutation from the duplicate copy of the
     * original artefact that was last returned by {@link #makeDuplicate()}, or
     * set using {@link #setDuplicate(Object)}. If there is no current
     * component to mutate (i.e. a call {@link #hasCurrent()} would return
     * false), the method throws a
     * {@link org.schemaanalyst.mutation.MutationException}.
     * 
     * @return The component from the last made duplicate.
     * @throws MutationException
     *             if there is no current component to mutate, i.e. the result
     *             of {@link #hasCurrent()} before calling this method is
     *             false.
     */
    public C getDuplicateComponent();

    /**
     * Puts a mutated component back into the duplicated artefact (i.e. that
     * which was last returned by {@link #makeDuplicate()}).
     * 
     * @param component
     *            The mutated component.
     * @throws MutationException
     *             if there is no current component to mutate, i.e. the result
     *             of {@link #hasCurrent()} before calling this method is
     *             false.
     */
    public void putComponentBackInDuplicate(C component);
}
