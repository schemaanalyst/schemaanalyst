package org.schemaanalyst.coverage.testgeneration.datageneration;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.PredicateChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.fixer.PredicateFixer;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.util.random.Random;

/**
 * Created by phil on 12/03/2014.
 */
public class RandomTestCaseGenerationAlgorithm extends TestCaseGenerationAlgorithm {

    private Random random;
    private CellValueGenerator cellValueGenerator;
    private int maxEvaluations;

    public RandomTestCaseGenerationAlgorithm(Random random, CellValueGenerator cellValueGenerator, int maxEvaluations) {
        this.random = random;
        this.cellValueGenerator = cellValueGenerator;
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public TestCase generateTestCase(Data data, Data state, Predicate predicate) {
        PredicateChecker predicateChecker = new PredicateChecker(predicate, data, state);

        // use a start initialiser?

        boolean success = predicateChecker.check();
        int evaluations = 0;
        while (!success && evaluations < maxEvaluations) {

            for (Cell cell : data.getCells()) {
                cellValueGenerator.generateCellValue(cell);
            }

            evaluations ++;
            success = predicateChecker.check();
        }

        TestCase testCase = new TestCase(data, state, predicate, success);
        testCase.addInfo("info", predicateChecker.getInfo());

        return testCase;
    }

    @Override
    public boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate) {
        PredicateChecker predicateChecker = new PredicateChecker(predicate, testCase.getData(), testCase.getState());
        return predicateChecker.check();
    }
}
