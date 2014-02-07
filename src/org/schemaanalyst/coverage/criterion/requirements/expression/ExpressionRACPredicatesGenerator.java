package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public abstract class ExpressionRACPredicatesGenerator {

    protected Table table;

    public ExpressionRACPredicatesGenerator(Table table) {
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

    public static ExpressionRACPredicatesGenerator createPredicateGenerator(Table table, Expression expression) {
        class ExpressionRACPredicatesGeneratorCreator extends ExpressionAdapter {

            Table table;
            ExpressionRACPredicatesGenerator expressionRACPredicatesGenerator;

            ExpressionRACPredicatesGenerator create(Table table, Expression expression) {
                this.table = table;
                expressionRACPredicatesGenerator = null;
                expression.accept(this);
                return expressionRACPredicatesGenerator;
            }

            @Override
            public void visit(AndExpression expression) {
                expressionRACPredicatesGenerator = new AndExpressionRACPredicatesGenerator(table, expression);
            }

            @Override
            public void visit(BetweenExpression expression) {
                expressionRACPredicatesGenerator = new BetweenExpressionRACPredicatesGenerator(table, expression);
            }

            @Override
            public void visit(InExpression expression) {
                expressionRACPredicatesGenerator = new InExpressionRACPredicatesGenerator(table, expression);
            }

            // TODO: needed
            @Override
            public void visit(NullExpression expression) {

            }

            // TODO: might be needed one day
            // @Override
            // public void visit(OrExpression expression) {
            //
            // }

            @Override
            public void visit(RelationalExpression expression) {
                expressionRACPredicatesGenerator = new RelationalExpressionRACPredicatesGenerator(table, expression);
            }
        }

        return new ExpressionRACPredicatesGeneratorCreator().create(table, expression);
    }
}
