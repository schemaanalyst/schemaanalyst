package org.schemaanalyst.test.coverage.criterion.requirements;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.NotNullConstraintRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 24/02/2014.
 */
public class TestNotNullConstraintRequirementsGenerator {

    private Schema schema;
    private Table tab1;
    private Column tab1Col1;

    @Before
    public void loadSchema() {
        schema = new SimpleSchema();
        tab1 = schema.getTable("Tab1");
        tab1Col1 = tab1.getColumn("Tab1Col1");
    }

    @Test
    public void testGeneratedRequirements() {
        NotNullConstraint nn =
                schema.createNotNullConstraint(tab1, tab1Col1);

        NotNullConstraintRequirementsGenerator reqGen
                = new NotNullConstraintRequirementsGenerator(schema, nn);

        List<Predicate> requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 2", 2, requirements.size());

        NullClause colNull = new NullClause(tab1, tab1Col1, true);
        NullClause colNotNull = new NullClause(tab1, tab1Col1, false);

        Predicate predicate1 = requirements.get(0);
        assertTrue(predicate1.hasClause(colNotNull));
        assertFalse(predicate1.hasClause(colNull));

        Predicate predicate2 = requirements.get(1);
        assertFalse(predicate2.hasClause(colNotNull));
        assertTrue(predicate2.hasClause(colNull));
    }

}
