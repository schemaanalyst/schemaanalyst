package _deprecated.datageneration.search.objective.data;

import _deprecated.datageneration.search.objective.ObjectiveValue;
import _deprecated.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Collections;

public class NullColumnObjectiveFunction extends ColumnObjectiveFunction {

    /**
     * Objective function to evaluate columns according to their NULL status
     * (typically as part of checking a NOT NULL constraint).
     * 
     * @param table
     *            The table involved. 
     * @param column
     *            The column of interest.
     * @param description
     *            Some descriptive text to describe the objective function.
     * @param goalIsToSatisfy
     *            If set to true, the goal of the objective function will be to
     *            produce rows that all satisfy the constraint, else the goal is
     *            to produce rows that all falsify the constraint.
     */
    public NullColumnObjectiveFunction(Table table, Column column, String description,
            boolean goalIsToSatisfy) {
        super(table, Collections.singletonList(column), description, goalIsToSatisfy);
    }

    @Override
    protected ObjectiveValue evaluateRow(Row row) {
        Value value = row.getCells().get(0).getValue();
        return NullValueObjectiveFunction.compute(value, goalIsToSatisfy);
    }
}
