/*
 */
package org.schemaanalyst.mutation.mutators.expression;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionVisitor;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 *
 * @author Chris J. Wright
 */
public abstract class ExpressionWalker implements ExpressionVisitor {

    protected Deque<Integer> exprStack = new ArrayDeque<>();

    @Override
    public void visit(AndExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(BetweenExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(ColumnExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(ConstantExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(InExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(ListExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(NullExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(OrExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(ParenthesisedExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }

    @Override
    public void visit(RelationalExpression expression) {
        List<Expression> subexpressions = expression.getSubexpressions();
        for (int i = 0; i < subexpressions.size(); i++) {
            exprStack.push(i);
            subexpressions.get(i).accept(this);
            exprStack.pop();
        }
    }
}
