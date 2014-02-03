package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.data.Data;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerator {

    public abstract TestCase generate(Data data, Data state, Predicate predicate);
}
