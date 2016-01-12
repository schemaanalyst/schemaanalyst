package org.schemaanalyst.testgeneration.coveragecriterion;

import java.io.Serializable;

/**
 * Created by phil on 25/07/2014.
 */
public class TestRequirementID implements Comparable<TestRequirementID>, Serializable {

    private static final long serialVersionUID = -2477609817199475731L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestRequirementID that = (TestRequirementID) o;

        if (number != that.number) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + number;
        return result;
    }
}
