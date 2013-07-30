package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class ExpressionObjectiveFunctionFactory {

    protected Expression expression;
    protected boolean goalIsToSatisfy, nullAccepted;

    public ExpressionObjectiveFunctionFactory(Expression expression,
                                              boolean goalIsToSatisfy,
                                              boolean nullAccepted) {
        this.expression = expression;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.nullAccepted = nullAccepted;
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
                objFun = new AndExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }
            
            @Override
            public void visit(BetweenExpression expression) {
                objFun = new BetweenExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }            
            
            @Override
            public void visit(InExpression expression) {                
                objFun = new InExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }            
            
            @Override
            public void visit(NullExpression expression) {
                objFun = new NullExpressionObjectiveFunction(
                        expression, goalIsToSatisfy);
            }

            @Override
            public void visit(OrExpression expression) {
                objFun = new OrExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                objFun = new ParenthesisedExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }            
            
            @Override
            public void visit(RelationalExpression expression) {
                objFun = new RelationalExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, nullAccepted);
            }
        }

        return (new ExpressionDispatcher()).dispatch();
    }
}
