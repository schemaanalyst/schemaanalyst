package org.schemaanalyst.testgeneration;

import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.data.Data;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerationAlgorithm {

    public abstract TestCase generateTestCase(Data data, Data state, Predicate predicate);

}
