package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicable;

/**
 * {@link IntermediaryIteratingSupplier} is a {@link Supplier} that iterates
 * over an intermediary list of objects that are used to extract specific
 * components for mutation. For example, in mutating lists of
 * <tt>PRIMARY KEY</tt> columns, a supplier may need to iterate over the list of
 * {@link org.schemaanalyst.sqlrepresentation.Table} objects obtained from a
 * {@link org.schemaanalyst.sqlrepresentation.Schema}.
 * {@link IntermediaryIteratingSupplier} provides common code to ease this task.
 * 
 * @author Phil McMinn
 * 
 * @param <A>
 *            the type of the original artefact being mutated (e.g.
 *            {@link org.schemaanalyst.sqlrepresentation.Schema})
 * @param <I>
 *            the type of the intermediary objects from which components are
 *            being extracted (e.g.
 *            {@link org.schemaanalyst.sqlrepresentation.Table})
 * @param <C>
 *            the type of the component to be mutated (e.g. a list of columns in
 *            a <tt>PRIMARY KEY</tt>)
 */

public abstract class IntermediaryIteratingSupplier<A extends Duplicable<A>, I, C>
		extends Supplier<A, C> {

	private List<I> intermediaries, duplicateIntermediaries;
	private int index;
	private boolean initialised, haveCurrent;

	public IntermediaryIteratingSupplier() {
		initialised = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void initialise(A originalArtefact) {
		super.initialise(originalArtefact);
		intermediaries = getIntermediaries(originalArtefact);
		index = -1;
		initialised = true;
		haveCurrent = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return initialised && index + 1 < intermediaries.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean haveCurrent() {
		return initialised && haveCurrent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getNextComponent() {
		boolean hasNext = hasNext();
		haveCurrent = hasNext;
		index++;
		if (hasNext) {
			return getComponentFromIntermediary(originalArtefact,
					intermediaries.get(index));
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public A makeDuplicate() {
		super.makeDuplicate();
		duplicateIntermediaries = getIntermediaries(currentDuplicate);
		return currentDuplicate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getDuplicateComponent() {
		if (!haveCurrent()) {
			throw new MutationException("No current component to mutate");
		}
		return getComponentFromIntermediary(currentDuplicate,
				duplicateIntermediaries.get(index));
	}

	/**
	 * Gets the current intermediary item from the current duplicate.
	 * 
	 * @return The intermediary item.
	 */
	public I getDuplicateIntermediary() {
		return duplicateIntermediaries.get(index);
	}

	/**
	 * Gets the list of intermediary objects from an artefact - could be the
	 * original artefact or a duplicate of it.
	 * 
	 * @param artefact
	 *            the artefact from which to get the list of intermediary
	 *            objects.
	 */
	protected abstract List<I> getIntermediaries(A artefact);

	/**
	 * Extracts the component for mutation from the current intermediary
	 * (returned by {@link #getDuplicateIntermediary()}).
	 * 
	 * @param artefact
	 *            the artefact from which to get the component.
	 * @param intermediary
	 *            the intermediary to be used.
	 */
	protected abstract C getComponentFromIntermediary(A artefact, I intermediary);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(C component) {
        if (!haveCurrent()) {
            throw new MutationException("Cannot put component back in duplicate when there is no current duplicate");
        }		
		putComponentBackInIntermediary(getDuplicateIntermediary(), component);
	}

	/**
	 * Puts the component back in the intermediary item.
	 * 
	 * @param intermediary
	 *            the intermediary to be used.
	 * @param componentn
	 *            the component to put back.
	 */
	protected abstract void putComponentBackInIntermediary(I intermediary,
			C Component);
}
