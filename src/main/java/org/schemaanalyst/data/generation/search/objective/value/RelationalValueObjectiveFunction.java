package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.*;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.util.tuple.Pair;

public abstract class RelationalValueObjectiveFunction<T> extends ObjectiveFunction<Pair<T>> {

    protected RelationalOperator op;
    protected boolean allowNull;
    protected String description;
    
    public RelationalValueObjectiveFunction(RelationalOperator op, boolean allowNull) {
        this.op = op;
        this.allowNull = allowNull;
    }
        
    @Override
    public ObjectiveValue evaluate(Pair<T> candidateSolution) {
        T lhs = candidateSolution.getFirst();
        T rhs = candidateSolution.getSecond();

        description = lhs + " " + op + " " + rhs;
        
        if (lhs == null || rhs == null) {
            if (allowNull) {
                return ObjectiveValue.optimalObjectiveValue(description);
            } else {
                return ObjectiveValue.worstObjectiveValue(description);
            }            
        }
        
        return computeObjectiveValue(lhs, rhs);
    }

    protected abstract ObjectiveValue computeObjectiveValue(T lhs, T rhs);
    
    public static ObjectiveValue compute(Value lhs, RelationalOperator op, Value rhs, boolean allowNull) {

        class RelationalObjectiveFunctionDispatcher implements ValueVisitor {

            Value lhs, rhs;
            RelationalOperator op;
            boolean allowNull;
            ObjectiveValue objVal;
            
            ObjectiveValue dispatch(Value lhs, RelationalOperator op, Value rhs, boolean allowNull) {
                this.lhs = lhs;
                this.op = op;
                this.rhs = rhs;
                this.allowNull = allowNull;
                
                if (lhs != null) {
                    lhs.accept(this);
                } else if (rhs != null) {
                    rhs.accept(this);
                } else {
                    numericValue();
                }
                return objVal;
            }

            @Override
            public void visit(BooleanValue value) {
                booleanValue();
            }

            @Override
            public void visit(DateValue value) {
                compoundValue();
            }

            @Override
            public void visit(DateTimeValue value) {
                compoundValue();
            }

            @Override
            public void visit(NumericValue value) {
                numericValue();
            }

            @Override
            public void visit(StringValue value) {
                compoundValue();
            }

            @Override
            public void visit(TimeValue value) {
                compoundValue();
            }

            @Override
            public void visit(TimestampValue value) {
                numericValue();
            }
            
            protected void booleanValue() {
                RelationalBooleanValueObjectiveFunction objFun = new RelationalBooleanValueObjectiveFunction(op, allowNull);
                objVal = objFun.evaluate(new Pair<>((BooleanValue) lhs, (BooleanValue) rhs));
            }
            
            protected void numericValue() {
                RelationalNumericValueObjectiveFunction objFun = new RelationalNumericValueObjectiveFunction(op, allowNull);
                objVal = objFun.evaluate(new Pair<>((NumericValue) lhs, (NumericValue) rhs));  
            }
            
            protected void compoundValue() {
                RelationalCompoundValueObjectiveFunction objFun = new RelationalCompoundValueObjectiveFunction(op, allowNull);
                objVal = objFun.evaluate(new Pair<>((CompoundValue) lhs, (CompoundValue) rhs));
            }
        }      

        return (new RelationalObjectiveFunctionDispatcher()).dispatch(lhs, op, rhs, allowNull);
    }
}
