package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.CompoundValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;

import java.util.List;

import static org.schemaanalyst.logic.RelationalOperator.*;

public class RelationalCompoundValueObjectiveFunction extends RelationalValueObjectiveFunction<CompoundValue> {

    protected MultiObjectiveValue objVal;
    protected List<Value> lhsSubValues, rhsSubValues;
    protected int smallestSize, sizeDiff;    
    
    public RelationalCompoundValueObjectiveFunction(RelationalOperator op, boolean allowNull) {
        super(op, allowNull);        
    }    
    
    @Override
    protected ObjectiveValue computeObjectiveValue(CompoundValue lhs, CompoundValue rhs) {

        lhsSubValues = lhs.getElements();
        rhsSubValues = rhs.getElements();

        smallestSize = Math.min(lhsSubValues.size(), rhsSubValues.size());
        sizeDiff = Math.abs(lhsSubValues.size() - rhsSubValues.size());

        instantiateMultiObjectiveValue();

        boolean finalized = false;

        // account for an empty compound value with no sub values
        finalized = addNoSubValuesPenalty();
        if (finalized) {
            return objVal;
        }

        // iterate over the compound value's sub values
        finalized = iterateOverSubValues();
        if (finalized) {
            return objVal;
        }

        // account for difference in list length
        addSizeDiffPenalty();
        
        return objVal;
    }

    protected boolean iterateOverSubValues() {

        switch (op) {

            case EQUALS:
                for (int i = 0; i < smallestSize; i++) {
                    objVal.add(subValueObjectiveValue(i, EQUALS));
                }
                break;

            case NOT_EQUALS:
                if (sizeDiff > 0) {
                    objVal.add(ObjectiveValue.optimalObjectiveValue("Compound values are of different sizes"));
                } else {
                    // value lists are of equal length
                    for (int i = 0; i < smallestSize; i++) {
                        objVal.add(subValueObjectiveValue(i, NOT_EQUALS));
                        if (objVal.isOptimal()) {
                            break;
                        }
                    }
                }
                break;

            default:
                for (int i = 0; i < smallestSize; i++) {
                    ObjectiveValue equalsObjectiveValue = subValueObjectiveValue(i, EQUALS);
                    if (equalsObjectiveValue.isOptimal()) {
                        objVal.add(equalsObjectiveValue);
                    } else {
                        ObjectiveValue greaterObjectiveValue = subValueObjectiveValue(i, op);
                        objVal.add(greaterObjectiveValue);
                        return true;
                    }
                }
        }
        return false;
    }

    protected void instantiateMultiObjectiveValue() {
        objVal = (op == NOT_EQUALS) ? new BestOfMultiObjectiveValue(description)
                : new SumOfMultiObjectiveValue(description);
    }

    protected ObjectiveValue subValueObjectiveValue(int index, RelationalOperator op) {
        Value lhs = lhsSubValues.get(index);
        Value rhs = rhsSubValues.get(index);
        return RelationalValueObjectiveFunction.compute(lhs, op, rhs, allowNull);
    }

    protected boolean addNoSubValuesPenalty() {
        boolean noSubValues = lhsSubValues.isEmpty() && rhsSubValues.isEmpty();
        boolean relevantOp = op == NOT_EQUALS || op == GREATER || op == LESS;

        if (noSubValues && relevantOp) {
            objVal.add(ObjectiveValue.worstObjectiveValue("Compound values have same size"));
            return true;
        }

        return false;
    }

    protected void addSizeDiffPenalty() {
        int sizeDiffPenalty = 0;

        if (op == EQUALS && lhsSubValues.size() != rhsSubValues.size()) {
            sizeDiffPenalty = sizeDiff;
        }

        if (op == GREATER && lhsSubValues.size() <= rhsSubValues.size()) {
            sizeDiffPenalty = 1 + sizeDiff;
        }

        if (op == GREATER_OR_EQUALS && lhsSubValues.size() < rhsSubValues.size()) {
            sizeDiffPenalty = sizeDiff;
        }

        if (op == LESS && lhsSubValues.size() >= rhsSubValues.size()) {
            sizeDiffPenalty = 1 + sizeDiff;
        }

        if (op == LESS_OR_EQUALS && lhsSubValues.size() > rhsSubValues.size()) {
            sizeDiffPenalty = sizeDiff;
        }

        for (int i = 0; i < sizeDiffPenalty; i++) {
            objVal.add(ObjectiveValue.worstObjectiveValue("Size difference penalty (" + (i + 1) + ")"));
        }
    }
}
