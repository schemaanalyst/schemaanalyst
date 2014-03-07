package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.supplier.Supplier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Given a list of elements this mutator returns mutants with each element 
 * removed from the list in turn.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <A> The artefact type
 * @param <E> The element type
 */
public class ListElementRemover<A, E> extends Mutator<A, List<E>> {

	private List<E> elements;
	private Iterator<E> iterator;
	private String mutationDescription;

	public ListElementRemover(Supplier<A, List<E>> supplier) {
		super(supplier);
	}

	@Override
	protected void initialise(List<E> elements) {
		this.elements = elements;
		iterator = elements.iterator();
	}

	@Override
	protected boolean isMoreMutationToDo() {
		return iterator.hasNext();
	}

	@Override
	protected List<E> performMutation(List<E> componentToMutate) {
		E element = iterator.next();
		mutationDescription = "Removed " + element;

		List<E> mutatedElements = new ArrayList<>();

		for (E duplicatedElement : elements) {
			if (!duplicatedElement.equals(element)) {
				mutatedElements.add(duplicatedElement);
			}
		}

		return mutatedElements;
	}

	@Override
	protected String getDescriptionOfLastMutation() {
		return mutationDescription;
	}
}
