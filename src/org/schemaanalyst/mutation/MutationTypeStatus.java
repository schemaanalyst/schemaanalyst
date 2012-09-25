package org.schemaanalyst.mutation;

/** Stores the summary information about a mutant type and how many mutants were and were not killed */
public class MutationTypeStatus {
    /** The count of the mutants that were killed */
    private int killedCount;

    /** The count of the mutants that were not killed */
    private int notKilledCount;

    /** The count of the mutants that were still born */
    private int stillBornCount;

    /** The count of the intersection of K and Q */
    private int intersection;

    /** Initialize the count to zero */
    public MutationTypeStatus() {
	killedCount = 0;
	notKilledCount = 0;
	stillBornCount = 0;
	intersection = 0;
    }    

    /** One more killed for this type */
    public void killed() {
	killedCount++;
    }

    /** One more not killed for this type */
    public void notKilled() {
	notKilledCount++;
    }

    /** One more still born for this type */
    public void stillBorn() {
	stillBornCount++;
    }

    public void intersected() {
	intersection++;
    }

    /** Grab the killed count */
    public int getKilledCount() {
	return killedCount;
    }

   /** Grab the not killed count */
    public int getNotKilledCount() {
	return notKilledCount;
    }

    /** Grab the still born count */
    public int getStillBornCount() {
	return stillBornCount;
    }
    
    public int getIntersectionCount() {
	return intersection;
    }

    /** Return the string representation */
    public String toString() {
	return "(killed=" + killedCount + ", notkilled=" + notKilledCount + ", stillborn=" + stillBornCount + 
	    "intersection=" + intersection +")"; 
    }
}