package org.schemaanalyst.test.coverage.criterion.requirements;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.CheckConstraintRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.NotNullConstraintRequirementsGenerator;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 24/02/2014.
 */
public class TestCheckConstraintRequirementsGenerator {

    private Schema schema;
    private Table tab1;
    private Column tab1Col1, tab1Col2;

    @Before
    public void loadSchema() {
        schema = new SimpleSchema();
        tab1 = schema.getTable("Tab1");
        tab1Col1 = tab1.getColumn("Tab1Col1");
        tab1Col2 = tab1.getColumn("Tab1Col2");
    }

    @Test
    public void testGeneratedRequirements() {
        Expression exp = new RelationalExpression(
                new ColumnExpression(tab1, tab1Col1),
                RelationalOperator.EQUALS,
                new ColumnExpression(tab1, tab1Col2));

        CheckConstraint cc = schema.createCheckConstraint(tab1, exp);

        CheckConstraintRequirementsGenerator reqGen
                = new CheckConstraintRequirementsGenerator(schema, cc);

        List<Predicate> requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 3", 3, requirements.size());

        ExpressionClause expTrue = new ExpressionClause(tab1, exp, true);
        ExpressionClause expFalse = new ExpressionClause(tab1, exp, false);

        NullClause col1Null = new NullClause(tab1, tab1Col1, true);
        NullClause col1NotNull = new NullClause(tab1, tab1Col1, false);
        NullClause col2NotNull = new NullClause(tab1, tab1Col2, false);

        Predicate predicate1 = requirements.get(0);
        assertTrue(predicate1.hasClause(expTrue));
        assertTrue(predicate1.hasClause(col1NotNull));
        assertTrue(predicate1.hasClause(col2NotNull));

        Predicate predicate2 = requirements.get(1);
        assertTrue(predicate2.hasClause(expFalse));
        assertTrue(predicate2.hasClause(col1NotNull));
        assertTrue(predicate2.hasClause(col2NotNull));

        Predicate predicate3 = requirements.get(2);
        assertTrue(predicate3.hasClause(col1Null));
        assertFalse(predicate3.hasClause(col2NotNull));
    }
}
