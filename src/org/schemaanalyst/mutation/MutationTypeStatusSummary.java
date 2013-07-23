package org.schemaanalyst.mutation;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Stores the summary information about a mutant type and how many mutants were
 * and were not killed.
 */
public class MutationTypeStatusSummary {

    /**
     * The HashMap relating the mutant type, as a String, to the
     * MutantTypeStatus.
     */
    private LinkedHashMap<String, MutationTypeStatus> summaryMap;

    /**
     * Initialize the HashMap for the summary information
     */
    public MutationTypeStatusSummary() {
        summaryMap = new LinkedHashMap<>();
    }

    /**
     * Return a list of all of the mutant types (i.e., the String keys)
     */
    public List<String> getMutantTypes() {
        Set<String> summaryMapKeys = summaryMap.keySet();
        List<String> mutantTypeList = new ArrayList<>(summaryMapKeys);
        return mutantTypeList;
    }

    /**
     * Return the correct count based on string parameter
     */
    public int getStatusCount(String mutationType, String status) {
        int count = -1;
        switch (status) {
            case "killed":
                count = this.getKilledCount(mutationType);
                break;
            case "notkilled":
                count = this.getAliveCount(mutationType);
                break;
            case "stillborn":
                count = this.getStillBornCount(mutationType);
                break;
            case "intersection":
                count = this.getIntersectionCount(mutationType);
                break;
        }
        return count;
    }

    /**
     * Process the provided MutantReport by updating all of the type counts
     */
    public void process(MutantReport mutantReport) {
        // handle the killed information
        if (mutantReport.isKilled()) {
            this.killed(mutantReport.getDescription());
        } else {
            this.alive(mutantReport.getDescription());
        }

        // handle the still born information
        if (mutantReport.isStillBorn()) {
            this.stillBorn(mutantReport.getDescription());
        }

        if (mutantReport.isIntersected()) {
            this.intersected(mutantReport.getDescription());
        }
    }

    /**
     * Intersected again for a specified type of mutant
     */
    public void intersected(String mutationType) {
        // we have already encountered this mutation, increment
        if (summaryMap.containsKey(mutationType)) {
            MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
            mutationTypeStatus.incrementIntersected();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.incrementIntersected();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Killed again for a specified type of mutant
     */
    public void killed(String mutationType) {
        // we have already encountered this mutation, increment
        if (summaryMap.containsKey(mutationType)) {
            MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
            mutationTypeStatus.incrementKilled();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.incrementKilled();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Not killed again for a specified type of mutant
     */
    public void alive(String mutationType) {
        // we have already encountered this mutation, increment
        if (summaryMap.containsKey(mutationType)) {
            MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
            mutationTypeStatus.incrementAlive();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.incrementAlive();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Not killed again for a specified type of mutant
     */
    public void stillBorn(String mutationType) {
        // we have already encountered this mutation, increment
        if (summaryMap.containsKey(mutationType)) {
            MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
            mutationTypeStatus.incrementStillBorn();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.incrementStillBorn();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Get the killed count for the specified type of mutant
     */
    public int getKilledCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getKilled();
    }

    /**
     * Get the not killed count for the specified type of mutant
     */
    public int getAliveCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getAlive();
    }

    /**
     * Get the still born count for the specified type of mutant
     */
    public int getStillBornCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getStillBorn();
    }

    /**
     * Get the intersection count for the specified type of mutant
     */
    public int getIntersectionCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getIntersected();
    }

    @Override
    public String toString() {
        return "MutationTypeStatusSummary{" + "summaryMap=" + summaryMap + '}';
    }
}