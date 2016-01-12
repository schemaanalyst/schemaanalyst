package org.schemaanalyst.mutation.supplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicator;

import java.util.List;

/**
 * <p>
 * A supplier that provides components that are a part of some internal
 * collection in the original artefact. For example, a schema may have multiple
 * <tt>CHECK</tt> constraints, and as such
 * {@link org.schemaanalyst.mutation.supplier.schema.CheckConstraintSupplier}
 * extends this class.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <A>
 *            the type of artefact being mutated (e.g. a
 *            {@link org.schemaanalyst.sqlrepresentation.Schema}).
 * @param <C>
 *            the type of component of the artefact being mutated (e.g. the
 *            columns of an integrity constraint).
 */
public abstract class IteratingSupplier<A, C> extends AbstractSupplier<A, C> {

	private List<C> components, duplicateComponents;
	private int index;

	/**
	 * Parameterless constructor, with which there is no requirement to specify
	 * a {@link org.schemaanalyst.util.Duplicator} object for producing
	 * duplicates of the original artefact. As such, the
	 * {@link #setDuplicate(Object)} method must be used to pass the supplier
	 * duplicate objects that have been created elsewhere.
	 * 
	 */
	public IteratingSupplier() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param duplicator
	 *            the duplicator object that produces duplicates of A, enabling
	 *            the {@link #makeDuplicate()} method to be used to produce
	 *            duplicates automatically for mutation.
	 */
	public IteratingSupplier(Duplicator<A> duplicator) {
		super(duplicator);
	}

	/**
	 * {@inheritDoc}
	 */
	public void initialise(A originalArtefact) {
		super.initialise(originalArtefact);
		components = getComponents(originalArtefact);
		index = -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		if (!isInitialised()) {
			throw new MutationException("Supplier not initialised");
		}
		haveNext = index + 1 < components.size();
		return haveNext;
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
			currentDuplicate = null;
			return components.get(index);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDuplicate(A currentDuplicate) {
		super.setDuplicate(currentDuplicate);
		duplicateComponents = getComponents(currentDuplicate);
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
		return duplicateComponents.get(index);
	}

	/**
	 * Gets the components from the artefact
	 * 
	 * @param artefact
	 *            the artefact to get the components from
	 * @return a list of the components
	 */
	protected abstract List<C> getComponents(A artefact);
}
