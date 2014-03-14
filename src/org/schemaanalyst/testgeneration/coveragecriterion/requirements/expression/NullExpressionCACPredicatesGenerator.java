package org.schemaanalyst.testgeneration.coveragecriterion.requirements.expression;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class NullExpressionCACPredicatesGenerator extends ExpressionCACPredicatesGenerator {

    public NullExpressionCACPredicatesGenerator(Table table, NullExpression expression) {
        super(table, expression);
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        return generatePredicate(true);
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        return generatePredicate(false);
    }

    private List<Predicate> generatePredicate(boolean outcome) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = new Predicate("Testing " + expression + " is " + outcome);
        predicate.addClause(new NullClause(table, expression.getColumnsInvolved().get(0), !outcome));
        predicates.add(predicate);
        return predicates;
    }

    @Override
    public List<Predicate> generateNullPredicates() {
        return new ArrayList<>();
    }
}
