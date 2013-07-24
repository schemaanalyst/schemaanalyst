package org.schemaanalyst.test.datageneration.search;

import java.math.BigDecimal;

import org.junit.Test;

import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.util.Duplicable;

import static org.junit.Assert.*;

public class TestSearchEvaluation {

    class DuplicableDouble implements Duplicable<DuplicableDouble> {

        public double value;

        public DuplicableDouble(double value) {
            this.value = value;
        }

        @Override
        public DuplicableDouble duplicate() {
            return new DuplicableDouble(value);
        }
    }

    class MockObjectiveFunction extends ObjectiveFunction<DuplicableDouble> {

        @Override
        public ObjectiveValue evaluate(DuplicableDouble duplicableDouble) {
            ObjectiveValue objVal = new ObjectiveValue();
            objVal.setValue(duplicableDouble.value);
            return objVal;
        }
    }

    class MockSearch extends Search<DuplicableDouble> {

        public MockSearch() {
            super();
        }

        @Override
        public void search(DuplicableDouble d) {
        }

        @Override
        public ObjectiveValue evaluate(DuplicableDouble d) {
            return super.evaluate(d);
        }
    }

    @Test
    public void test() {
        MockSearch s = new MockSearch();

        s.setObjectiveFunction(new MockObjectiveFunction());
        s.evaluate(new DuplicableDouble(0.6));
        s.evaluate(new DuplicableDouble(0.2));
        s.evaluate(new DuplicableDouble(0.5));

        assertEquals(3, s.getEvaluationsCounter().getValue());
        assertEquals(new BigDecimal(0.2), s.getBestObjectiveValue().getValue());
    }
}
