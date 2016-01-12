package org.schemaanalyst.data.generation;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 14/03/2014.
 */
public abstract class DataGenerator {

    public abstract DataGenerationReport generateData(Data data, Data state, Predicate predicate);
}
