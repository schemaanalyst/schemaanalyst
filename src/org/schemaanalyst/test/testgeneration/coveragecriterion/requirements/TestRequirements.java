package org.schemaanalyst.test.testgeneration.coveragecriterion.requirements;

import org.junit.Test;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 07/03/2014.
 */
public class TestRequirements {

    Table table = new Table("table");
    Column column = new Column("column", new IntDataType());
    NullClause nullClause1 = new NullClause(table, column, true);
    NullClause nullClause2 = new NullClause(table, column, false);

    @Test
    public void testRequirementsDifferingPredicates() {

        Predicate predicate1 = new Predicate("predicate1");
        predicate1.addClause(nullClause1);

        Predicate predicate2 = new Predicate("predicate2");
        predicate2.addClause(nullClause2);

        Requirements requirements = new Requirements();
        requirements.addPredicate(predicate1);
        requirements.addPredicate(predicate2);

        assertEquals(2, requirements.size());
    }

    @Test
    public void testRequirementsSamePredicates() {

        Predicate predicate1 = new Predicate("predicate1");
        predicate1.addClause(nullClause1);

        Predicate predicate2 = new Predicate("predicate2");
        predicate2.addClause(nullClause1);

        Requirements requirements = new Requirements();
        requirements.addPredicate(predicate1);
        requirements.addPredicate(predicate2);
        assertEquals(1, requirements.size());
        assertEquals(2, predicate1.getPurposes().size());
    }
}
