package org.schemaanalyst.datageneration.search.objective.relationalpredicate;

import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

public class ListOfCellsObjectiveFunction extends ObjectiveFunction<RelationalPredicate<List<Cell>>> {

    @Override
    public ObjectiveValue evaluate(RelationalPredicate<List<Cell>> predicate) {
        List<Cell> lhs = predicate.getLHS();
        List<Cell> rhs = predicate.getRHS();
        RelationalOperator op = predicate.getOperator();

        // check rows are of the same size
        if (lhs.size() != rhs.size()) {
            throw new ObjectiveFunctionException("Rows " + lhs + " and " + rhs + " are not of the same size");
        }

        // instantiate objective value
        MultiObjectiveValue objVal;
        String description = lhs + " " + op + " " + rhs;

        switch (op) {
            case EQUALS:
                objVal = new SumOfMultiObjectiveValue(description);
                break;
            case NOT_EQUALS:
                objVal = new BestOfMultiObjectiveValue(description);
                break;
            default:
                throw new ObjectiveFunctionException(op + " operator cannot be used to compare rows");
        }

        // iterate through the cells of each row being compared
        Iterator<Cell> row1Iterator = lhs.iterator();
        Iterator<Cell> row2Iterator = rhs.iterator();

        while (row1Iterator.hasNext() && row2Iterator.hasNext()) {

            Cell cell1 = row1Iterator.next();
            Cell cell2 = row2Iterator.next();

            Value value1 = cell1.getValue();
            Value value2 = cell2.getValue();

            ObjectiveValue valueObjVal = ValueObjectiveFunction.compute(value1, op, value2);
            objVal.add(valueObjVal);
        }

        return objVal;
    }

    public static ObjectiveValue compute(List<Cell> lhs, RelationalOperator op, List<Cell> rhs) {
        ListOfCellsObjectiveFunction objFun = new ListOfCellsObjectiveFunction();
        return objFun.evaluate(new RelationalPredicate<>(lhs, op, rhs));
    }
}
