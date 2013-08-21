/*
 */
package org.schemaanalyst.test.mutation.mutator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.mutator.RelationalOperatorExchanger;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

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
    class MockSupplier extends Supplier<Expression, RelationalOperator> {

        boolean hasNext = true, haveCurrent = false;

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public RelationalOperator getNextComponent() {
            hasNext = false;
            haveCurrent = true;
            return ((RelationalExpression) originalArtefact).getRelationalOperator();
        }

        @Override
        public boolean haveCurrent() {
            return haveCurrent;
        }

        @Override
        public RelationalOperator getDuplicateComponent() {
            return ((RelationalExpression) currentDuplicate).getRelationalOperator();
        }

        @Override
        public void putComponentBackInDuplicate(RelationalOperator component) {
            ((RelationalExpression) currentDuplicate).setRelationalOperator(component);
        }
    }

    /**
     * Tests the exchanging of a single &lt; operator in a relational expression.
     */
    @Test
    public void testExchangeLess() {
        MockSupplier supplier = new MockSupplier();
        supplier.initialise(relationalExpression1);
        RelationalOperatorExchanger exchanger = new RelationalOperatorExchanger(supplier);
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
        RelationalOperatorExchanger exchanger = new RelationalOperatorExchanger(supplier);
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
