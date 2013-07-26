package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
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
    protected boolean goalIsToSatisfy, allowNull;

    public ExpressionObjectiveFunctionFactory(Expression expression,
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
                    System.out.println("OBJ FUN is NULL!!!");
                    System.out.println("Expression is " + expression.getClass());                    
                }
                
                return objFun;
            }

            @Override
            public void visit(AndExpression expression) {
                objFun = new AndExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }
            
            @Override
            public void visit(BetweenExpression expression) {
                objFun = new BetweenExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(InExpression expression) {                
                objFun = new InExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(NullExpression expression) {
                objFun = new NullExpressionObjectiveFunction(
                        expression, goalIsToSatisfy);
            }

            @Override
            public void visit(OrExpression expression) {
                objFun = new OrExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }

            @Override
            public void visit(ParenthesisedExpression expression) {
                objFun = new ParenthesisedExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }            
            
            @Override
            public void visit(RelationalExpression expression) {
                objFun = new RelationalExpressionObjectiveFunction(
                        expression, goalIsToSatisfy, allowNull);
            }
        }

        return (new ExpressionDispatcher()).dispatch();
    }
}
