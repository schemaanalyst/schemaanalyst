package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.supplier.Supplier;

/**
 * 
 * @author Phil McMinn
 *
 * @param <A>
 * @param <E>
 */
public class ElementNullifier<A, E> extends Mutator<A, E> {

	private boolean isMoreMuationToDo = false;
	private E element;

	public ElementNullifier(Supplier<A, E> supplier) {
		super(supplier);
	}	
	
	@Override
	protected void initialise(E element) {
		isMoreMuationToDo = true;
		this.element = element;
	}

	@Override
	protected boolean isMoreMutationToDo() {
		return isMoreMuationToDo;
	}

	@Override
	protected E performMutation(E componentToMutate) {
        isMoreMuationToDo = false;
		return null;
	}

    @Override
    protected String getDescriptionOfLastMutation() {
        return "set " + element + " to null";
    }
}
