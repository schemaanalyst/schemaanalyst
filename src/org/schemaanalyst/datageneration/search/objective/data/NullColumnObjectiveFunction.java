package org.schemaanalyst.datageneration.search.objective.data;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;

public class NullColumnObjectiveFunction extends ConstraintObjectiveFunction {

    protected Column column;
    protected String description;
    protected boolean goalIsToSatisfy;

    public NullColumnObjectiveFunction(Column column,
                                       String description,
                                       boolean goalIsToSatisfy) {
        this.column = column;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
    }

    @Override
    protected ObjectiveValue performEvaluation(Data data) {

        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        // NOTE: The database state (encapsulated by the state instance
        // variable can be ignored for the purposes of this objective 
        // function evaluation.  All the values in the state will obey 
        // the constraint and so do not need to be checked.

        for (Row row : data.getRows(column)) {
            Value value = row.getCell(column).getValue();
            ObjectiveValue rowObjVal =
                    NullValueObjectiveFunction.compute(value, goalIsToSatisfy);
            objVal.add(rowObjVal);
            classifyRow(objVal, row);
        }

        return objVal;
    }
}
