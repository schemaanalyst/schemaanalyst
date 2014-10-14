/*
 */
package org.schemaanalyst.unittest.mutation.mutator;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.mutator.RelationalOperatorExchanger;
import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestRelationalOperatorExchanger {

    RelationalExpression relationalExpression1 = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)),
            RelationalOperator.LESS,
            new ConstantExpression(new NumericValue(2)));
    RelationalExpression relationalExpression2 = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)),
            RelationalOperator.EQUALS,
            new ConstantExpression(new NumericValue(2)));

    /**
     * Mock implementation of a Supplier
     */
    class MockSupplier extends SolitaryComponentSupplier<Expression, RelationalOperator> {
  
        public MockSupplier() {
            super(new Expression.Duplicator());
        }

        @Override
        public void putComponentBackInDuplicate(RelationalOperator component) {
            ((RelationalExpression) currentDuplicate).setRelationalOperator(component);
        }

        @Override
        protected RelationalOperator getComponent(Expression expression) {
            return ((RelationalExpression) expression).getRelationalOperator();
        }
    }

    /**
     * Tests the exchanging of a single &lt; operator in a relational expression.
     */
    @Test
    public void testExchangeLess() {
        MockSupplier supplier = new MockSupplier();
        supplier.initialise(relationalExpression1);
        RelationalOperatorExchanger<Expression> exchanger = new RelationalOperatorExchanger<>(supplier);
        List<Mutant<Expression>> mutants = exchanger.mutate();
        assertEquals(5, mutants.size());
        Set<RelationalOperator> ops = EnumSet.noneOf(RelationalOperator.class);
        for (Mutant<Expression> mutant : mutants) {
            RelationalOperator op = ((RelationalExpression) mutant.getMutatedArtefact()).getRelationalOperator();
            assertFalse("The original relational operator should not occur in any mutant",
                    op.equals(RelationalOperator.LESS));
            assertFalse("The same relational operator should not occur in two mutants",
                    ops.contains(op));
            ops.add(op);
        }
        assertEquals("There should be exactly 5 mutants produced when replacing one relational operator",
                5, ops.size());
    }

    /**
     * Tests the exchanging of a single = operator in a relational expression.
     */
    @Test
    public void testExchangeEquals() {
        MockSupplier supplier = new MockSupplier();
        supplier.initialise(relationalExpression2);
        RelationalOperatorExchanger<Expression> exchanger = new RelationalOperatorExchanger<>(supplier);
        List<Mutant<Expression>> mutants = exchanger.mutate();
        assertEquals(5, mutants.size());
        Set<RelationalOperator> ops = EnumSet.noneOf(RelationalOperator.class);
        for (Mutant<Expression> mutant : mutants) {
            RelationalOperator op = ((RelationalExpression) mutant.getMutatedArtefact()).getRelationalOperator();
            assertFalse("The original relational operator should not occur in any mutant",
                    op.equals(RelationalOperator.EQUALS));
            assertFalse("The same relational operator should not occur in two mutants",
                    ops.contains(op));
            ops.add(op);
        }
        assertEquals("There should be exactly 5 mutants produced when replacing one relational operator",
                5, ops.size());
    }
}
