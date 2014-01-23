package org.schemaanalyst.coverage.predicate;

import java.util.LinkedHashSet;

/**
 * Created by phil on 22/01/2014.
 */
public class TestRequirements {

    LinkedHashSet<Predicate> requirementsSet;

    public TestRequirements() {
        requirementsSet = new LinkedHashSet<>();
    }

    public void add(Predicate predicate) {
        requirementsSet.add(predicate);
    }

    public void add(TestRequirements testRequirements) {
        requirementsSet.addAll(testRequirements.requirementsSet);
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
