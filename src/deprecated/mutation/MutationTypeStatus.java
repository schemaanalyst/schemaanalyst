package deprecated.mutation;

/**
 * Stores the summary counts of how many mutants were killed, remained alive,
 * were still born and the intersection of killed and still born (interstected).
 */
public class MutationTypeStatus {

    /**
     * The count of the mutants that were killed
     */
    private int killed = 0;
    /**
     * The count of the mutants that were not killed
     */
    private int alive = 0;
    /**
     * The count of the mutants that were still born
     */
    private int stillBorn = 0;
    /**
     * The count of the intersection of K and Q
     */
    private int intersected = 0;

    /**
     * Increment the killed count.
     */
    public void incrementKilled() {
        killed++;
    }

    /**
     * Increment the alive count.
     */
    public void incrementAlive() {
        alive++;
    }

    /**
     * Increment the still born count.
     */
    public void incrementStillBorn() {
        stillBorn++;
    }

    /**
     * Increment the intersected count.
     */
    public void incrementIntersected() {
        intersected++;
    }

    /**
     * Get the killed count.
     *
     * @return The killed count.
     */
    public int getKilled() {
        return killed;
    }

    /**
     * Get the alive count.
     *
     * @return The alive count.
     */
    public int getAlive() {
        return alive;
    }

    /**
     * Get the still born count.
     *
     * @return The still born count.
     */
    public int getStillBorn() {
        return stillBorn;
    }

    /**
     * Get the intersected count.
     *
     * @return The intersected count.
     */
    public int getIntersected() {
        return intersected;
    }

    
    @Override
    public String toString() {
        return "MutationTypeStatus{" + "killed=" + killed + ", alive=" + alive + ", stillBorn=" + stillBorn + ", intersected=" + intersected + '}';
    }
}