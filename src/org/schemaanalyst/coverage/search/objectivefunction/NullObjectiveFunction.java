package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;

/**
 * Created by phil on 24/01/2014.
 */
public class NullObjectiveFunction extends ObjectiveFunction<Row> {

    private NullClause nullClause;

    public NullObjectiveFunction(NullClause nullClause) {
        this.nullClause = nullClause;
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        return NullValueObjectiveFunction.compute(
                row.getCell(nullClause.getColumn()).getValue(),
                nullClause.getSatisfy());
    }
}
