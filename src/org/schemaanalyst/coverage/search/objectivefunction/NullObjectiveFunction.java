package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;

import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class NullObjectiveFunction extends ObjectiveFunction<Data> {

    private NullClause nullClause;

    public NullObjectiveFunction(NullClause nullClause) {
        this.nullClause = nullClause;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        List<Row> rows = data.getRows(nullClause.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue();

            for (Row row : rows) {
                objVal.add(
                        NullValueObjectiveFunction.compute(
                                row.getCell(nullClause.getColumn()).getValue(),
                                nullClause.getSatisfy())
                );
            }
            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue();
    }
}
