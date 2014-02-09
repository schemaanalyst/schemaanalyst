package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.data.Data;

import java.util.List;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerationAlgorithm {

    public abstract TestCase generateTestCase(Data data, Data state, Predicate predicate);

    public abstract TestCase testCaseThatSatisfiesPredicate(Predicate predicate, TestSuite testSuite);

    public abstract boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate);

    public abstract double computeCoverage(TestSuite testSuite, List<Predicate> requirements);
}
