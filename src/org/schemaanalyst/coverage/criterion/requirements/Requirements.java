package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 22/01/2014.
 */
public class Requirements {

    LinkedHashSet<Predicate> requirementsSet;

    public Requirements() {
        requirementsSet = new LinkedHashSet<>();
    }

    public void add(Predicate predicate) {
        requirementsSet.add(predicate);
    }

    public void add(Requirements requirements) {
        requirementsSet.addAll(requirements.requirementsSet);
    }

    public List<Predicate> getRequirements() {
        return new ArrayList<>(requirementsSet);
    }

    public int size() {
        return requirementsSet.size();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Predicate predicate : requirementsSet) {
            sb.append(predicate.getPurpose() + "\n");
            sb.append(predicate + "\n");
        }
        return sb.toString();
    }
}
