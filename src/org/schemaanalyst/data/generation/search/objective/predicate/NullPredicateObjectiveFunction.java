package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class NullPredicateObjectiveFunction extends ObjectiveFunction<Data> {

    private NullPredicate nullPredicate;

    public NullPredicateObjectiveFunction(NullPredicate nullPredicate) {
        this.nullPredicate = nullPredicate;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = nullPredicate.toString();
        List<Row> rows = data.getRows(nullPredicate.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

            for (Row row : rows) {
                objVal.add(
                        NullValueObjectiveFunction.compute(
                                row.getCell(nullPredicate.getColumn()).getValue(),
                                nullPredicate.getTruthValue())
                );
            }
            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }
}
