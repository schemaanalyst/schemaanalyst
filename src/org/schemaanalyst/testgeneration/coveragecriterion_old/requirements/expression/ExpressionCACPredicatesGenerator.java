package org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.expression;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public abstract class ExpressionCACPredicatesGenerator {

    protected Table table;
    protected Expression expression;

    public ExpressionCACPredicatesGenerator(Table table, Expression expression) {
        this.table = table;
        this.expression = expression;
    }

    public abstract List<Predicate> generateTruePredicates();

    public abstract List<Predicate> generateFalsePredicates();

    public abstract List<Predicate> generateNullPredicates();

    protected void setNullStatusForColumns(Predicate predicate) {
        List<Column> columns = expression.getColumnsInvolved();
        for (Column column : columns) {
            predicate.setColumnNullStatus(table, column, false);
        }
    }

    public static List<Predicate> generatePredicates(Table table, Expression expression) {
        ExpressionCACPredicatesGenerator predicatesGenerator = createPredicateGenerator(table, expression);
        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(predicatesGenerator.generateTruePredicates());
        predicates.addAll(predicatesGenerator.generateFalsePredicates());
        predicates.addAll(predicatesGenerator.generateNullPredicates());
        return predicates;
    }

    public static List<Predicate> generateTruePredicates(Table table, Expression expression) {
        ExpressionCACPredicatesGenerator predicatesGenerator = createPredicateGenerator(table, expression);
        return predicatesGenerator.generateTruePredicates();
    }

    public static List<Predicate> generateFalsePredicates(Table table, Expression expression) {
        ExpressionCACPredicatesGenerator predicatesGenerator = createPredicateGenerator(table, expression);
        return predicatesGenerator.generateFalsePredicates();
    }

    public static List<Predicate> generateNullPredicates(Table table, Expression expression) {
        ExpressionCACPredicatesGenerator predicatesGenerator = createPredicateGenerator(table, expression);
        return predicatesGenerator.generateNullPredicates();
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
            public void visit(ParenthesisedExpression expression) {
                expression.getSubexpression().accept(this);
            }

            @Override
            public void visit(RelationalExpression expression) {
                expressionRACPredicatesGenerator = new RelationalExpressionCACPredicatesGenerator(table, expression);
            }
        }

        return new ExpressionRACPredicatesGeneratorCreator().create(table, expression);
    }
}
