package org.schemaanalyst.unittest.data.generation.search;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellinitialization.DefaultCellInitializer;
import org.schemaanalyst.data.generation.search.HyperAlternatingValueSearch;
import org.schemaanalyst.data.generation.search.SwitcherAVS;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.unittest.data.generation.search.TestSwitcherAVS.ImproveUntilAlternatingValueSearch;
import org.schemaanalyst.unittest.data.generation.search.TestSwitcherAVS.MockObjectiveFunction;
import org.schemaanalyst.unittest.data.generation.search.TestSwitcherAVS.NoImprovementAlternatingValueSearch;
import org.schemaanalyst.unittest.testutil.mock.MockDatabase;
import org.schemaanalyst.unittest.testutil.mock.TwoColumnMockDatabase;

public class TestHyperAlterntingValueSearch {

	   class MockObjectiveFunction extends ObjectiveFunction<Data> {

	        @Override
	        public ObjectiveValue evaluate(Data data) {
	            return ObjectiveValue.worstObjectiveValue();
	        }
	    }

	    class NoImprovementAlternatingValueSearch extends HyperAlternatingValueSearch {

	        public NoImprovementAlternatingValueSearch() {
	        	super(null, new DefaultCellInitializer(), null, null);
	        }

	        @Override
	        protected boolean valueSearch(Cell cell) {
	            evaluate(data);
	            return false;
	        }
	    }

	    class ImproveUntilAlternatingValueSearch extends HyperAlternatingValueSearch {

	        int count = 0;
	        int improveUntil;

	        public ImproveUntilAlternatingValueSearch(int improveUntil) {
	        	super(null, new DefaultCellInitializer(), null, null);
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

	        HyperAlternatingValueSearch havs = new NoImprovementAlternatingValueSearch();
	        TerminationCriterion terminationCriterion = new CounterTerminationCriterion(havs.getEvaluationsCounter(), ">=", maxEvals);
	        havs.setTerminationCriterion(terminationCriterion);
	        havs.setObjectiveFunction(new MockObjectiveFunction());
	        havs.search(data);
	        assertEquals("The maximum no. of evaluations should be " + expected, expected, havs.getNumEvaluations());
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

	        HyperAlternatingValueSearch havs = new ImproveUntilAlternatingValueSearch(improveUntil);
	        TerminationCriterion terminationCriterion = new CounterTerminationCriterion(havs.getRestartsCounter(), ">", 0);
	        havs.setTerminationCriterion(terminationCriterion);
	        havs.setObjectiveFunction(new MockObjectiveFunction());
	        havs.search(data);
	        assertEquals("The maximum no. of evaluations should be " + improveUntil, expected, havs.getNumEvaluations());
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
