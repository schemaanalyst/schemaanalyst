package org.schemaanalyst.datageneration.search.objective.row;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.EqualsMultiValueObjectiveFunction;

public class RelationalRowObjectiveFunction {

    public static ObjectiveValue compute(Row lhs, boolean equals, Row rhs, boolean allowNull) {
        return EqualsMultiValueObjectiveFunction.compute(
                extractValues(lhs), equals, extractValues(rhs), allowNull);        
    }           
    
    private static List<Value> extractValues(Row row) {
        List<Value> values = new ArrayList<>();
        for (Cell cell : row.getCells()) {
            values.add(cell.getValue());
        }
        return values;        
    }         
}
