package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public abstract class ExpressionCACPredicatesGenerator {

    protected Table table;

    public ExpressionCACPredicatesGenerator(Table table) {
        this.table = table;
    }

    public abstract List<Predicate> generateTruePredicates();

    public abstract List<Predicate> generateFalsePredicates();


    public static List<Predicate> generateTruePredicates(Table table, Expression expression) {
        return createPredicateGenerator(table, expression).generateTruePredicates();
    }

    public static List<Predicate> generateFalsePredicates(Table table, Expression expression) {
        return createPredicateGenerator(table, expression).generateFalsePredicates();
    }

    public static ExpressionCACPredicatesGenerator createPredicateGenerator(Table table, Expression expression) {
        class ExpressionRACPredicatesGeneratorCreator extends ExpressionAdapter {

            Table table;
            ExpressionCACPredicatesGenerator expressionRACPredicatesGenerator;

            ExpressionCACPredicatesGenerator create(Table table, Expression expression) {
                this.table = table;
                expressionRACPredicatesGenerator = null;
                expression.accept(this);
                return expressionRACPredicatesGenerator;
            }

            @Override
            public void visit(AndExpression expression) {
                expressionRACPredicatesGenerator = new AndExpressionCACPredicatesGenerator(table, expression);
            }

            @Override
            public void visit(BetweenExpression expression) {
                expressionRACPredicatesGenerator = new BetweenExpressionCACPredicatesGenerator(table, expression);
            }

            @Override
            public void visit(InExpression expression) {
                expressionRACPredicatesGenerator = new InExpressionCACPredicatesGenerator(table, expression);
            }

            // TODO: needed
            @Override
            public void visit(NullExpression expression) {
                expressionRACPredicatesGenerator = new NullExpressionCACPredicatesGenerator(table, expression);
            }

            // TODO: might be needed one day
            // @Override
            // public void visit(OrExpression expression) {
            //
            // }

            @Override
            public void visit(RelationalExpression expression) {
                expressionRACPredicatesGenerator = new RelationalExpressionCACPredicatesGenerator(table, expression);
            }
        }

        return new ExpressionRACPredicatesGeneratorCreator().create(table, expression);
    }
}
