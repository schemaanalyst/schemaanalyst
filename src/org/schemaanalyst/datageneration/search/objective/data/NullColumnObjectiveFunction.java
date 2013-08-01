package org.schemaanalyst.datageneration.search.objective.data;

import java.util.List;

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
        super(description, goalIsToSatisfy);
        this.column = column;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
    }

    @Override
    protected List<Row> getDataRows(Data data) {
        return data.getRows(column);
    }
    
    @Override
    protected ObjectiveValue performEvaluation(List<Row> dataRows) {

        // ALL rows must be accepted (goalIsToSatisfy == true)
        // or ALL rows must be rejected (goalIsToSatisfy == false) for
        // an optimum to be reached
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        // NOTE: The database state (encapsulated by the state instance
        // variable can be ignored for the purposes of this objective 
        // function evaluation.  All the values in the state will obey 
        // the constraint and so do not need to be checked.

        for (Row row : dataRows) {
            Value value = row.getCell(column).getValue();
            ObjectiveValue rowObjVal =
                    NullValueObjectiveFunction.compute(value, goalIsToSatisfy);
            objVal.add(rowObjVal);            
            classifyRow(rowObjVal, row);
        }

        return objVal;
    }
}
