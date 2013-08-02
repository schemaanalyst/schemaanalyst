package org.schemaanalyst.datageneration.search.objective.data;

import java.util.Collections;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class NullConstraintObjectiveFunction extends ConstraintObjectiveFunction {

    private Column column;

    /**
     * Objective function to evaluate columns according to their NULL status
     * (typically as part of checking a NOT NULL constraint).
     * 
     * @param column
     *            The column of interest.
     * @param description
     *            Some descriptive text to describe the objective function.
     * @param goalIsToSatisfy
     *            If set to true, the goal of the objective function will be to
     *            produce rows that all satisfy the constraint, else the goal is
     *            to produce rows that all falsify the constraint.
     */
    public NullConstraintObjectiveFunction(Column column, String description,
            boolean goalIsToSatisfy) {
        super(Collections.singletonList(column), description, goalIsToSatisfy);
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }
    
    @Override
    protected boolean zeroRowsOptimalityCondition() {
        return !goalIsToSatisfy;
    }

    @Override
    protected ObjectiveValue evaluateRow(Row row) {
        Value value = row.getCell(column).getValue();
        return NullValueObjectiveFunction.compute(value, goalIsToSatisfy);
    }
}
