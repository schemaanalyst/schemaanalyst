package org.schemaanalyst.test.testgeneration.coveragecritrerion.predicate;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 24/07/2014.
 */
public class TestAndPredicate {

    Table table = new Table("table");
    Column column1 = new Column("column1", new IntDataType());
    Column column2 = new Column("column2", new IntDataType());

    @Test
    public void testInfeasibleIsInfeasible() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);

        assertTrue(andPredicate.isInfeasible());
    }

    @Test
    public void testInfeasibleIsFeasible() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column2, false);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);

        assertFalse(andPredicate.isInfeasible());
    }

}
