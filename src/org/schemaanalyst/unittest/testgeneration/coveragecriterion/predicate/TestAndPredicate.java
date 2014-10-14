package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;

import java.util.Arrays;

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

        assertTrue(andPredicate.isTriviallyInfeasible());
    }

    @Test
    public void testInfeasibleIsFeasible() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column2, false);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);

        assertFalse(andPredicate.isTriviallyInfeasible());
    }

    @Test
    public void testOrInfeasibleIsFeasible() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, false);

        OrPredicate orPredicate = new OrPredicate();
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        orPredicate.addPredicate(nullPredicate2);
        orPredicate.addPredicate(nullPredicate3);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(orPredicate);

        assertFalse(andPredicate.isTriviallyInfeasible());
    }

    @Test
    public void testOrInfeasibleIsFeasible2() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, false);

        OrPredicate orPredicate = new OrPredicate();
        MatchPredicate matchPredicate = new MatchPredicate(table, Arrays.asList(column1), MatchPredicate.EMPTY_COLUMN_LIST, MatchPredicate.Mode.AND);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        orPredicate.addPredicate(matchPredicate);
        orPredicate.addPredicate(nullPredicate2);
        orPredicate.addPredicate(nullPredicate3);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(orPredicate);

        assertFalse(andPredicate.isTriviallyInfeasible());
    }

    @Test
    public void testOrInfeasibleIsFeasible3() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, false);

        OrPredicate orPredicate = new OrPredicate();
        MatchPredicate matchPredicate1 = new MatchPredicate(table, Arrays.asList(column1), MatchPredicate.EMPTY_COLUMN_LIST, MatchPredicate.Mode.AND);
        MatchPredicate matchPredicate2 = new MatchPredicate(table, Arrays.asList(column2), MatchPredicate.EMPTY_COLUMN_LIST, MatchPredicate.Mode.AND);
        orPredicate.addPredicate(matchPredicate1);
        orPredicate.addPredicate(matchPredicate2);
        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(orPredicate);

        assertFalse(andPredicate.isTriviallyInfeasible());
    }

    @Test
    public void testOrInfeasibleIsInfeasible() {

        AndPredicate andPredicate = new AndPredicate();

        NullPredicate nullPredicate1 = new NullPredicate(table, column1, false);
        NullPredicate nullPredicate2 = new NullPredicate(table, column2, false);

        OrPredicate orPredicate = new OrPredicate();
        NullPredicate nullPredicate3 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, true);
        orPredicate.addPredicate(nullPredicate3);
        orPredicate.addPredicate(nullPredicate4);

        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);
        andPredicate.addPredicate(orPredicate);

        assertTrue(andPredicate.isTriviallyInfeasible());
    }
}
