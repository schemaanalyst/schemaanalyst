package org.schemaanalyst.mutation.artefactsupplier;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.util.Duplicable;

/**
 * Supplies duplicates of an artefact and their specific components to be mutated to a mutator.
 * 
 * @author Phil McMinn
 *
 * @param <A> The type of artefact being mutated
 * @param <C> The type of component of the artefact being mutated.
 */

public abstract class Supplier<A extends Duplicable<A>, C> {

	protected A originalArtefact, currentDuplicate;
	
	/**
	 * Constructor.
	 * @param originalArtefact The artefact to be mutated.
	 */
	public Supplier(A originalArtefact) {
		this.originalArtefact = originalArtefact;
	}
	
	/**
	 * Returns the original artefact that is being mutated.
	 * @return The original artefact.
	 */
	public A getOriginalArtefact() {
		return originalArtefact;
	}
	
	/**
	 * Indicates whether there are more components in the artefact for mutation.
	 * @return True if there are more components, else false.
	 */
	public abstract boolean hasNext();
	
	/**
	 * Returns the next component from the original artefact for mutation,
	 * or null if there are no more components left to mutate (i.e. the result
	 * of hasNext() is false).
	 * @return
	 */
	public abstract C getNextComponent();
	
    /**
     * Indicates whether there is a current component available for mutation
     * (following a call to getNextComponentFromOriginalArtefact()).
     * @return True if there is a current component, else false.
     */
    public abstract boolean haveCurrent();	
	
    /**
     * Returns a duplicate of the original artefact, 
     * containing a duplicate of the component for mutation.
     * If there is current component to mutate (i.e. haveCurrent() returns false), 
     * the method returns null.
     * @return A duplicate of the original artefact.
     * @throws A MutationException if there is no current component to mutate,
     * i.e. the result of haveCurrent() before calling this method is false. 
     */
    public A makeDuplicate() {
        if (!haveCurrent()) {
            throw new MutationException("There is no current component to mutate");
        }        
        currentDuplicate = originalArtefact.duplicate(); 
        return currentDuplicate;
    } 	
	
    /**
     * Extracts the component for mutation from the duplicate last returned by 
     * makeDuplicate.  
     * If there is current component to mutate (i.e. haveCurrent() returns false), 
     * the method returns null.
     * @return The component from the last made duplicate.
     * @throws A MutationException if there is no current component to mutate,
     * i.e. the result of haveCurrent() before calling this method is false.
     */	
	public abstract C getDuplicateComponent();
	
	/**
	 * Puts a mutated component back into the duplicated artefact (that last
	 * returned by makeDuplicate()).
	 * @param component The mutated component.
	 * @throws A MutationException if there is no current component to mutate,
	 * i.e. the result of haveCurrent() before calling this method is false.
	 */
	public abstract void putComponentBackInDuplicate(C component);
	
	/**
	 * Returns the simple name of the class.
	 */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }	
}
