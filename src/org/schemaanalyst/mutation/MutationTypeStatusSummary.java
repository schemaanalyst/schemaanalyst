package org.schemaanalyst.mutation;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.schemaanalyst.mutation.MutantReport;

/**
 * Stores the summary information about a mutant type and how many mutants were
 * and were not killed
 */
public class MutationTypeStatusSummary {

    /**
     * The HashMap relating the mutant type, as a String, to the
     * MutantTypeStatus
     */
    private LinkedHashMap<String, MutationTypeStatus> summaryMap;

    /**
     * Initialize the HashMap for the summary information
     */
    public MutationTypeStatusSummary() {
        summaryMap = new LinkedHashMap<String, MutationTypeStatus>();
    }

    /**
     * Return a list of all of the mutant types (i.e., the String keys)
     */
    public List<String> getMutantTypes() {
        Set<String> summaryMapKeys = summaryMap.keySet();
        List<String> mutantTypeList = new ArrayList<String>(summaryMapKeys);
        return mutantTypeList;
    }

    /**
     * Return the correct count based on string parameter
     */
    public int getStatusCount(String mutationType, String status) {
        int count = -1;
        if (status.equals("killed")) {
            count = this.getKilledCount(mutationType);
        } else if (status.equals("notkilled")) {
            count = this.getNotKilledCount(mutationType);
        } else if (status.equals("stillborn")) {
            count = this.getStillBornCount(mutationType);
        } else if (status.equals("intersection")) {
            count = this.getIntersectionCount(mutationType);
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
            this.notKilled(mutantReport.getDescription());
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
            mutationTypeStatus.intersected();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.intersected();
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
            mutationTypeStatus.killed();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.killed();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Not killed again for a specified type of mutant
     */
    public void notKilled(String mutationType) {
        // we have already encountered this mutation, increment
        if (summaryMap.containsKey(mutationType)) {
            MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
            mutationTypeStatus.notKilled();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.notKilled();
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
            mutationTypeStatus.stillBorn();
        } // this is the first time we have seend this mutant, add it now
        else {
            MutationTypeStatus mutationTypeStatusNew = new MutationTypeStatus();
            mutationTypeStatusNew.stillBorn();
            summaryMap.put(mutationType, mutationTypeStatusNew);
        }
    }

    /**
     * Get the killed count for the specified type of mutant
     */
    public int getKilledCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getKilledCount();
    }

    /**
     * Get the not killed count for the specified type of mutant
     */
    public int getNotKilledCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getNotKilledCount();
    }

    /**
     * Get the still born count for the specified type of mutant
     */
    public int getStillBornCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getStillBornCount();
    }

    public int getIntersectionCount(String mutationType) {
        MutationTypeStatus mutationTypeStatus = summaryMap.get(mutationType);
        return mutationTypeStatus.getIntersectionCount();
    }

    /**
     * Return the string representation
     */
    public String toString() {
        return summaryMap.toString();
    }
}