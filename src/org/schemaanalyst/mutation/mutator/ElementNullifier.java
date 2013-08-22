package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.util.Duplicable;

public class ElementNullifier<A extends Duplicable<A>, E> extends Mutator<A, E> {

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
