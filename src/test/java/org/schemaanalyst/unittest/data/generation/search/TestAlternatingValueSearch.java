package org.schemaanalyst.unittest.data.generation.search;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellinitialization.DefaultCellInitializer;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.unittest.testutil.mock.MockDatabase;
import org.schemaanalyst.unittest.testutil.mock.TwoColumnMockDatabase;

import static org.junit.Assert.assertEquals;

public class TestAlternatingValueSearch {

    class MockObjectiveFunction extends ObjectiveFunction<Data> {

        @Override
        public ObjectiveValue evaluate(Data data) {
            return ObjectiveValue.worstObjectiveValue();
        }
    }

    class NoImprovementAlternatingValueSearch extends AlternatingValueSearch {

        public NoImprovementAlternatingValueSearch() {
            super(null, new DefaultCellInitializer(), null);
        }

        @Override
        protected boolean valueSearch(Cell cell) {
            evaluate(data);
            return false;
        }
    }

    class ImproveUntilAlternatingValueSearch extends AlternatingValueSearch {

        int count = 0;
        int improveUntil;

        public ImproveUntilAlternatingValueSearch(int improveUntil) {
            super(null, new DefaultCellInitializer(), null);
            this.improveUntil = improveUntil;
        }

        @Override
        protected boolean valueSearch(Cell cell) {
            evaluate(data);
            count++;
            return count <= improveUntil;
        }
    }

    protected void testMaxEvalsTermination(int maxEvals, int expected) {
        MockDatabase database = new TwoColumnMockDatabase();
        Data data = database.createData(1);

        AlternatingValueSearch avs = new NoImprovementAlternatingValueSearch();
        TerminationCriterion terminationCriterion = new CounterTerminationCriterion(avs.getEvaluationsCounter(), ">=", maxEvals);
        avs.setTerminationCriterion(terminationCriterion);
        avs.setObjectiveFunction(new MockObjectiveFunction());
        avs.search(data);
        assertEquals("The maximum no. of evaluations should be " + expected, expected, avs.getNumEvaluations());
    }

    @Test
    public void maxEvalsTerminationZero() {
        testMaxEvalsTermination(0, 1);
    }

    @Test
    public void maxEvalsTerminationOne() {
        testMaxEvalsTermination(1, 1);
    }

    @Test
    public void maxEvalsTerminationTwo() {
        testMaxEvalsTermination(2, 2);
    }

    public void testNumValueSearchesImprovement(int improveUntil, int expected) {
        MockDatabase database = new TwoColumnMockDatabase();
        Data data = database.createData(1);

        AlternatingValueSearch avs = new ImproveUntilAlternatingValueSearch(improveUntil);
        TerminationCriterion terminationCriterion = new CounterTerminationCriterion(avs.getRestartsCounter(), ">", 0);
        avs.setTerminationCriterion(terminationCriterion);
        avs.setObjectiveFunction(new MockObjectiveFunction());
        avs.search(data);
        assertEquals("The maximum no. of evaluations should be " + improveUntil, expected, avs.getNumEvaluations());
    }

    @Test
    public void numEvalsImprovementOne() {
        testNumValueSearchesImprovement(1, 4);
    }

    @Test
    public void numEvalsImprovementTwo() {
        testNumValueSearchesImprovement(2, 5);
    }

    @Test
    public void numEvalsImprovementThree() {
        testNumValueSearchesImprovement(3, 6);
    }
}
