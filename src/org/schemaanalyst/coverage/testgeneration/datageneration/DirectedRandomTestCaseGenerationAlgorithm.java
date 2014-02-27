package org.schemaanalyst.coverage.testgeneration.datageneration;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.datageneration.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

/**
 * Created by phil on 26/02/2014.
 */
public class DirectedRandomTestCaseGenerationAlgorithm extends TestCaseGenerationAlgorithm {


    @Override
    public TestCase generateTestCase(Data data, Data state, Predicate predicate) {

        return null;
    }

    @Override
    public boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate) {
        PredicateObjectiveFunction objFun = new PredicateObjectiveFunction(predicate, testCase.getState());
        ObjectiveValue objVal = objFun.evaluate(testCase.getData());
        return objVal.isOptimal();
    }
}
