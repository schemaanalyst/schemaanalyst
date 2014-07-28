package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 25/07/2014.
 */
public class TestRequirementIDUsingTable extends TestRequirementID {

    private int number;
    private String tableName;

    public TestRequirementIDUsingTable(int number, String tableName) {
        this.number = number;
        this.tableName = tableName;
    }

    @Override
    public int compareTo(TestRequirementID other) {

        if (other instanceof TestRequirementIDUsingTable) {
            TestRequirementIDUsingTable otherUsingTable = (TestRequirementIDUsingTable) other;

            int tableNameCompare = tableName.compareTo(otherUsingTable.tableName);

            if (tableNameCompare == 0) {
                return number - otherUsingTable.number;
            } else {
                return tableNameCompare;
            }
        } else {
            return toString().compareTo(other.toString());
        }
    }

    @Override
    public String toString() {
        return number + "-" + tableName;
    }

}
