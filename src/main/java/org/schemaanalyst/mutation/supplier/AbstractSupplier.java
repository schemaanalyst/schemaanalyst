package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicator;

/**
 * Provides common basic supplier functionality for subclasses to re-use.
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
public abstract class AbstractSupplier<A, C> implements Supplier<A, C> {

	/**
	 * The class that provides duplicates of A.
	 */
	protected Duplicator<A> duplicator;

	/**
	 * Instance of the <em>original artefact</em> being mutated. The original
	 * instance is unchanged, it is duplicates of this instance that are
	 * mutated.
	 */
	protected A originalArtefact;

	/**
	 * The current duplicate copy of <tt>originalArtefact</tt> which is to be
	 * mutated.
	 */
	protected A currentDuplicate;

	/**
	 * Indicates whether the supplier has been initialised or not.
	 */
	protected boolean initialised;

	/**
	 * Indicates whether there are more components for mutation.
	 */
	protected boolean haveNext;

	/**
	 * Indicates whether a current component is available for mutation or not.
	 */
	protected boolean haveCurrent;

	/**
	 * Parameterless constructor, with which there is no requirement to specify
	 * a {@link org.schemaanalyst.util.Duplicator} object for producing
	 * duplicates of the original artefact. As such, the
	 * {@link #setDuplicate(Object)} method must be used to pass the supplier
	 * duplicate objects that have been created elsewhere.
	 */
	public AbstractSupplier() {
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param duplicator
	 *            the duplicator object that produces duplicates of A, enabling
	 *            the {@link #makeDuplicate()} method to be used to produce
	 *            duplicates automatically for mutation.
	 */
	public AbstractSupplier(Duplicator<A> duplicator) {
		this.duplicator = duplicator;
		initialised = false;
		haveNext = false;
		haveCurrent = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialise(A originalArtefact) {
		this.originalArtefact = originalArtefact;
		initialised = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInitialised() {
		return initialised;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		if (!isInitialised()) {
			throw new MutationException("Supplier has not been initialised");
		}
		return haveNext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCurrent() {
		if (!isInitialised()) {
			throw new MutationException("Supplier has not been initialised");
		}
		return haveCurrent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public A getOriginalArtefact() {
		if (!isInitialised()) {
			throw new MutationException("Supplier has not been initialised");
		}
		return originalArtefact;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public A makeDuplicate() {
		if (!hasCurrent()) {
			throw new MutationException(
					"There is no current component to mutate");
		}
		if (duplicator == null) {
			throw new MutationException(
					"Cannot make duplicates of an object as no Duplicator has been set");
		}
		setDuplicate(duplicator.duplicate(originalArtefact));
		return currentDuplicate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDuplicate(A currentDuplicate) {
		if (!hasCurrent()) {
			throw new MutationException(
					"There is no current component to mutate");
		}
		this.currentDuplicate = currentDuplicate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
