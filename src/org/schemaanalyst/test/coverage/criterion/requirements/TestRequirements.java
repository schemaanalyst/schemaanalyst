package org.schemaanalyst.test.coverage.criterion.requirements;

import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.clause.ClauseVisitor;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static junit.framework.Assert.assertEquals;

/**
 * Created by phil on 07/03/2014.
 */
public class TestRequirements {

    private Table table = new Table("table");
    private Column column = new Column("column", new IntDataType());
    private NullClause nullClause1 = new NullClause(table, column, true);
    private NullClause nullClause2 = new NullClause(table, column, false);

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
    }

}