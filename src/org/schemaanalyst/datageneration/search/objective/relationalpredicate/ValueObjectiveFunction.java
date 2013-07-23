package org.schemaanalyst.datageneration.search.objective.relationalpredicate;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.CompoundValue;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

public class ValueObjectiveFunction extends ObjectiveFunction<RelationalPredicate<Value>> {

    public ObjectiveValue evaluate(RelationalPredicate<Value> predicate) {

        class ValueObjectiveFunctionDispatcher implements ValueVisitor {

            ObjectiveValue objVal;
            RelationalOperator op;
            Value rhs;

            ObjectiveValue dispatch(Value lhs, RelationalOperator op, Value rhs) {
                this.op = op;
                this.rhs = rhs;
                lhs.accept(this);
                return objVal;
            }

            public void visit(BooleanValue lhs) {
                objVal = BooleanValueObjectiveFunction.compute(lhs, op, (BooleanValue) rhs);
            }

            public void visit(DateValue lhs) {
                objVal = CompoundValueObjectiveFunction.compute(lhs, op, (CompoundValue) rhs);
            }

            public void visit(DateTimeValue lhs) {
                objVal = CompoundValueObjectiveFunction.compute(lhs, op, (CompoundValue) rhs);
            }

            public void visit(NumericValue lhs) {
                objVal = NumericValueObjectiveFunction.compute(lhs, op, (NumericValue) rhs);
            }

            public void visit(StringValue lhs) {
                objVal = CompoundValueObjectiveFunction.compute(lhs, op, (CompoundValue) rhs);
            }

            public void visit(TimeValue lhs) {
                objVal = CompoundValueObjectiveFunction.compute(lhs, op, (CompoundValue) rhs);
            }

            public void visit(TimestampValue lhs) {
                objVal = NumericValueObjectiveFunction.compute(lhs, op, (NumericValue) rhs);
            }
        }

        Value lhs = predicate.getLHS();
        Value rhs = predicate.getRHS();
        RelationalOperator op = predicate.getOperator();

        // if either v1 or v2 are null, the result is unknown
        if (lhs == null || rhs == null) {
            return ObjectiveValue.worstObjectiveValue(lhs + " " + op + " " + rhs);
        }

        // ensure the types of v1 and v2 are the same
        if (!lhs.getClass().equals(rhs.getClass())) {
            throw new ObjectiveFunctionException(
                    "Attempt to compute a distance for two incompatible types "
                    + lhs.getClass() + " and " + rhs.getClass());
        }

        return (new ValueObjectiveFunctionDispatcher()).dispatch(lhs, op, rhs);
    }

    public static ObjectiveValue compute(Value lhs, RelationalOperator op, Value rhs) {
        ValueObjectiveFunction objFun = new ValueObjectiveFunction();
        return objFun.evaluate(new RelationalPredicate<>(lhs, op, rhs));
    }
}
