package org.schemaanalyst.test.coverage.criterion.predicate;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.ClauseFactory;
import org.schemaanalyst.coverage.criterion.predicate.ConstraintPredicateGenerator;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 24/02/2014.
 */
public class TestConstraintPredicateGenerator {

    private Schema schema;
    private Table tab1, tab2;
    private Column tab1Col1, tab1Col2, tab2Col1;

    @Before
    public void loadSchema() {
        schema = new SimpleSchema();
        tab1 = schema.getTable("Tab1");
        tab1Col1 = tab1.getColumn("Tab1Col1");
        tab1Col2 = tab1.getColumn("Tab1Col2");

        tab2 = schema.getTable("Tab2");
        tab2Col1 = tab2.getColumn("Tab2Col1");
    }

    @Test
         public void testPredicateGenerationWithPrimaryKeyConstraint() {
        PrimaryKeyConstraint pk = schema.createPrimaryKeyConstraint(tab1, tab1Col1);

        ConstraintPredicateGenerator gen1 = new ConstraintPredicateGenerator(schema, tab1);
        Predicate fullPredicate = gen1.generate("test predicate");
        assertTrue("Predicate should have match clause", fullPredicate.hasClause(ClauseFactory.unique(tab1, tab1Col1, true)));
        assertTrue("Predicate should have not null clause", fullPredicate.hasClause(ClauseFactory.isNotNull(tab1, tab1Col1)));

        ConstraintPredicateGenerator gen2 = new ConstraintPredicateGenerator(schema, tab1, pk);
        Predicate predicate = gen2.generate("test predicate");
        assertEquals("Predicate should have no clauses", 0, predicate.getClauses().size());
    }

    @Test
    public void testPredicateGenerationWithForeignKeyConstraint() {
        ForeignKeyConstraint fk = schema.createForeignKeyConstraint(tab1, tab1Col1, tab2, tab2Col1);

        ConstraintPredicateGenerator gen1 = new ConstraintPredicateGenerator(schema, tab1);
        Predicate fullPredicate = gen1.generate("test predicate");
        assertTrue("Predicate should have match clause", fullPredicate.hasClause(
                ClauseFactory.references(tab1, Arrays.asList(tab1Col1), tab2, Arrays.asList(tab2Col1))));

        ConstraintPredicateGenerator gen2 = new ConstraintPredicateGenerator(schema, tab1, fk);
        Predicate predicate = gen2.generate("test predicate");
        assertEquals("Predicate should have no clauses", 0, predicate.getClauses().size());
    }

    @Test
         public void testPredicateGenerationWithNotNullConstraint() {
        NotNullConstraint nc = schema.createNotNullConstraint(tab1, tab1Col1);

        ConstraintPredicateGenerator gen1 = new ConstraintPredicateGenerator(schema, tab1);
        Predicate fullPredicate = gen1.generate("test predicate");
        assertTrue("Predicate should have not null clause", fullPredicate.hasClause(ClauseFactory.isNotNull(tab1, tab1Col1)));

        ConstraintPredicateGenerator gen2 = new ConstraintPredicateGenerator(schema, tab1, nc);
        Predicate predicate = gen2.generate("test predicate");
        assertEquals("Predicate should have no clauses", 0, predicate.getClauses().size());
    }

    @Test
    public void testPredicateGenerationWithCheckConstraint() {
        Expression exp = new RelationalExpression(
                new ColumnExpression(tab1, tab1Col1),
                RelationalOperator.EQUALS,
                new ColumnExpression(tab1, tab1Col2));
        CheckConstraint cc = schema.createCheckConstraint(tab1, exp);

        ConstraintPredicateGenerator gen1 = new ConstraintPredicateGenerator(schema, tab1);
        Predicate fullPredicate = gen1.generate("test predicate");
        assertTrue("Predicate should have expression clause", fullPredicate.hasClause(ClauseFactory.expression(tab1, exp)));

        ConstraintPredicateGenerator gen2 = new ConstraintPredicateGenerator(schema, tab1, cc);
        Predicate predicate = gen2.generate("test predicate");
        assertEquals("Predicate should have no clauses", 0, predicate.getClauses().size());
    }

    @Test
    public void testPredicateGenerationWithUniqueConstraint() {
        UniqueConstraint uc = schema.createUniqueConstraint(tab1, tab1Col1);

        ConstraintPredicateGenerator gen1 = new ConstraintPredicateGenerator(schema, tab1);
        Predicate fullPredicate = gen1.generate("test predicate");
        assertTrue("Predicate should have match clause", fullPredicate.hasClause(ClauseFactory.unique(tab1, tab1Col1, true)));

        ConstraintPredicateGenerator gen2 = new ConstraintPredicateGenerator(schema, tab1, uc);
        Predicate predicate = gen2.generate("test predicate");
        assertEquals("Predicate should have no clauses", 0, predicate.getClauses().size());
    }
}
