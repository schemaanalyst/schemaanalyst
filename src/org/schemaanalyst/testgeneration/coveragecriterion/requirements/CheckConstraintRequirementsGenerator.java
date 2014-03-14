package org.schemaanalyst.testgeneration.coveragecriterion.requirements;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class CheckConstraintRequirementsGenerator extends ConstraintRequirementsGenerator {

    private Expression expression;

    public CheckConstraintRequirementsGenerator(Schema schema, CheckConstraint constraint) {
        this(schema, constraint, true);
    }

    public CheckConstraintRequirementsGenerator(Schema schema, CheckConstraint constraint, boolean generateFullPredicate) {
        super(schema, constraint, generateFullPredicate);
        this.expression = constraint.getExpression();
    }

    @Override
    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        // Expression is TRUE requirement
        addTrueRequirement(requirements);

        // Expression is FALSE requirement
        addFalseRequirement(requirements);

        // Expression is NULL requirement
        addNullRequirement(requirements);

        return requirements;
    }

    private void addTrueRequirement(Requirements requirements) {
        Predicate predicate = generatePredicate("Test TRUE evaluation for " + table + "'s " + constraint);
        predicate.addClause(new ExpressionClause(table, expression, true));

        // each of the columns should be NOT NULL
        // (otherwise the outcome of the predicate would not be TRUE, it would be NULL).
        ensureColumnsAreNotNull(predicate);

        requirements.addPredicate(predicate);
    }

    private void ensureColumnsAreNotNull(Predicate predicate) {
        for (Column column : expression.getColumnsInvolved()) {
            predicate.setColumnNullStatus(table, column, false);
        }
    }

    private void addFalseRequirement(Requirements requirements) {
        Predicate predicate = generatePredicate("Test FALSE evaluation for " + table + "'s " + constraint);
        predicate.addClause(new ExpressionClause(table, expression, false));

        // each of the columns should be NOT NULL
        // (otherwise the outcome of the predicate would not be TRUE, it would be NULL).
        ensureColumnsAreNotNull(predicate);

        requirements.addPredicate(predicate);
    }

    private void addNullRequirement(Requirements requirements) {
        Predicate predicate = generatePredicate("Test NULL evaluation for " + table + "'s " + constraint);

        List<Column> columns = expression.getColumnsInvolved();
        if (columns.size() > 0) {
            predicate.setColumnNullStatus(table, columns.get(0), true);
            requirements.addPredicate(predicate);
        }
        // if no columns are involved, this requirement is infeasible -- we don't add it.
    }
}
