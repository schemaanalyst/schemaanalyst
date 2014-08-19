package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 25/07/2014.
 */
public class TestRequirementID implements Comparable<TestRequirementID> {

    private String name;
    private int number;

    public TestRequirementID(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public int compareTo(TestRequirementID other) {
        int nameCompare = name.compareTo(other.name);

        if (nameCompare == 0) {
            return number - other.number;
        } else {
            return nameCompare;
        }

    }

    @Override
    public String toString() {
        return number + "-" + name;
    }
}
