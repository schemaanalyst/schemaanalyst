package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class CheckConstraintRACRequirementsGenerator extends RequirementsGenerator {

    private Expression expression;

    public CheckConstraintRACRequirementsGenerator(Schema schema, Table table, CheckConstraint checkConstraint) {
        super(schema, table, checkConstraint);
        this.expression = checkConstraint.getExpression();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = generatePredicate("Testing " + expression);
        if (expression instanceof InExpression) {
            InExpression inExpression = (InExpression) expression;
            InExpressionRACRequirementsGenerator requirementsGenerator = new InExpressionRACRequirementsGenerator(schema, table, predicate, inExpression);
            requirements.addAll(requirementsGenerator.generateRequirements());
        } else {
            // fall back on normal coverage until have broken out other types of constraints....
            Predicate truePredicate = generatePredicate("Test " + expression + " evaluating to false");
            truePredicate.addClause(new ExpressionClause(table, expression, false));
            requirements.add(truePredicate);
            Predicate falsePredicate = generatePredicate("Test " + expression + " evaluating to true");
            falsePredicate.addClause(new ExpressionClause(table, expression, true));
            requirements.add(falsePredicate);
        }

        return requirements;
    }
}
