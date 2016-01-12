package org.schemaanalyst.unittest.testgeneration.coveragecriterion;

import org.junit.Test;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementID;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;

import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.SCHEMA;
import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.TABLE;

/**
 * Created by phil on 18/08/2014.
 */
public class TestTestRequirementIDGenerator {

    @Test
    public void test() {
        TestRequirementIDGenerator testRequirementIDGenerator = new TestRequirementIDGenerator(TABLE);
        TestRequirementID id;

        testRequirementIDGenerator.reset(TABLE, "A");
        id = testRequirementIDGenerator.nextID();
        assertEquals("1-A", id.toString());
        id = testRequirementIDGenerator.nextID();
        assertEquals("2-A", id.toString());

        testRequirementIDGenerator.reset(TABLE, "B");
        id = testRequirementIDGenerator.nextID();
        assertEquals("1-B", id.toString());
        id = testRequirementIDGenerator.nextID();
        assertEquals("2-B", id.toString());

        testRequirementIDGenerator.reset(TABLE, "A");
        id = testRequirementIDGenerator.nextID();
        assertEquals("3-A", id.toString());
        testRequirementIDGenerator.reset(SCHEMA, "S");
        id = testRequirementIDGenerator.nextID();
        assertEquals("4-A", id.toString());
    }

}
