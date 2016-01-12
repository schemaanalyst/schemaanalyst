package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicator;

/**
 * A supplier that supplies only a single component for each artefact to be
 * mutated. For example, a <tt>CHECK</tt> constraint has only one component, and
 * as such
 * {@link org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier}
 * extends this class.
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
public abstract class SolitaryComponentSupplier<A, C> extends
		AbstractSupplier<A, C> {

	/**
	 * Parameterless constructor, with which there is no requirement to specify
	 * a {@link org.schemaanalyst.util.Duplicator} object for producing
	 * duplicates of the original artefact. As such, the
	 * {@link #setDuplicate(Object)} method must be used to pass the supplier
	 * duplicate objects that have been created elsewhere.
	 */
	public SolitaryComponentSupplier() {
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
	public SolitaryComponentSupplier(Duplicator<A> duplicator) {
		super(duplicator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialise(A originalArtefact) {
		super.initialise(originalArtefact);
		haveNext = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getNextComponent() {
		if (hasNext()) {
			haveNext = false;
			haveCurrent = true;
			currentDuplicate = null;
			return getComponent(originalArtefact);
		} else {
			haveCurrent = false;
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getDuplicateComponent() {
		if (!hasCurrent()) {
			throw new MutationException("No current component to mutate");
		}
		if (currentDuplicate == null) {
			throw new MutationException(
					"Cannot get duplicate component if no duplicate artefact has been made");
		}
		return getComponent(currentDuplicate);
	}

	/**
	 * Retrieves the component of interest from an artefact (either the original
	 * or a duplicate).
	 * 
	 * @param artefact
	 *            the artefact from which to extract the component.
	 * @return the artefact's component.
	 */
	protected abstract C getComponent(A artefact);
}
