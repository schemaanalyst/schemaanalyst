package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.predicate.function.NullFunction;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;

/**
 * Created by phil on 24/01/2014.
 */
public class NullObjectiveFunction extends ObjectiveFunction<Row> {

    private NullFunction nullFunction;

    public NullObjectiveFunction(NullFunction nullFunction) {
        this.nullFunction = nullFunction;
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return NullValueObjectiveFunction.compute(
                row.getCell(nullFunction.getColumn()).getValue(),
                nullFunction.getSatisfy());
    }
}
