package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class CheckConstraintRequirementsGenerator extends RequirementsGenerator {

    private Expression expression;

    public CheckConstraintRequirementsGenerator(Schema schema, Table table, CheckConstraint checkConstraint) {
        super(schema, table, checkConstraint);
        this.expression = checkConstraint.getExpression();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        // Expression is TRUE requirement
        addTrueRequirement(requirements);

        // Expression is FALSE requirement
        addFalseRequirement(requirements);

        return requirements;
    }

    private void addFalseRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test " + expression + " evaluating to false");
        predicate.addClause(new ExpressionClause(table, expression, false));
        requirements.add(predicate);
    }

    private void addTrueRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test " + expression + " evaluating to true");
        predicate.addClause(new ExpressionClause(table, expression, true));
        requirements.add(predicate);
    }
}
