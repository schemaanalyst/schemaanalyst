package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Given a list of elements and a list of alternative elements, this mutator 
 * returns mutants with each alternative element added to the list in turn.
 * </p>
 * 
 * @author Phil McMinn
 * 
 * @param <A> The artefact type
 * @param <E> The element type
 */
public class ListElementAdder<A, E> extends Mutator<A, Pair<List<E>>> {

	private List<E> elements;
	private List<E> alternatives;
	private Iterator<E> iterator;
	private String mutationDescription;

	public ListElementAdder(Supplier<A, Pair<List<E>>> supplier) {
		super(supplier);
	}

	@Override
	protected void initialise(Pair<List<E>> pair) {
		this.elements = pair.getFirst();
		this.alternatives = pair.getSecond();
		iterator = alternatives.iterator();
	}

	@Override
	protected boolean isMoreMutationToDo() {
		return iterator.hasNext();
	}

	@Override
	protected Pair<List<E>> performMutation(Pair<List<E>> componentToMutate) {
		E elementToAdd = iterator.next();
		mutationDescription = "Added " + elementToAdd;

		List<E> mutatedElements = new ArrayList<>(elements);
		mutatedElements.add(elementToAdd);

		return new Pair<>(mutatedElements, null);
	}

	@Override
	protected String getDescriptionOfLastMutation() {
		return mutationDescription;
	}
}
