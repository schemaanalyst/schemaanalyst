package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.data.Data;

import java.util.List;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerationAlgorithm {

    public abstract TestCase generateTestCase(Data data, Data state, Predicate predicate);

    public abstract TestCase checkIfTestCaseExists(Predicate predicate, TestSuite testSuite);
}
