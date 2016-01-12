package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.sqlrepresentation.expression.*;

public class ExpressionRowObjectiveFunctionFactory {

    protected Expression expression;
    protected boolean goalIsToSatisfy, allowNull;

    public ExpressionRowObjectiveFunctionFactory(Expression expression,
                                              boolean goalIsToSatisfy,
                                              boolean allowNull) {
        this.expression = expression;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
    }

    public ObjectiveFunction<Row> create() {

        class ExpressionDispatcher extends ExpressionAdapter {

            ObjectiveFunction<Row> objFun;

            ObjectiveFunction<Row> dispatch() {
                objFun = null;
                expression.accept(this);
                
                if (objFun == null) {
                    throw new ObjectiveFunctionException("Expression type " 
                            + expression.getClass().getSimpleName() 
                            + " not supported for creating objective functions");                    
                }
                
                return objFun;
            }

            @Override
            public void visit(AndExpression expression) {
                objFun = new AndExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }
            
            @Override
            public void visit(BetweenExpression expression) {
                objFun = new BetweenExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(InExpression expression) {                
                objFun = new InExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(NullExpression expression) {
                objFun = new NullExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy);
            }

            @Override
            public void visit(OrExpression expression) {
                objFun = new OrExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                objFun = new ParenthesisedExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(RelationalExpression expression) {
                objFun = new RelationalExpressionRowObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }
        }

        return (new ExpressionDispatcher()).dispatch();
    }
}
