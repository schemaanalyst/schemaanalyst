package org.schemaanalyst.data.generation;

import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.data.Data;

/**
 * Created by phil on 14/03/2014.
 */
public abstract class DataGenerationAlgorithm {

    public abstract void generateData(Data data, Data state, Predicate predicate);
}
