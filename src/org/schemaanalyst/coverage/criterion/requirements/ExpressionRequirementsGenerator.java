package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 31/01/2014.
 */
public class ExpressionRequirementsGenerator extends RequirementsGenerator {

    private Expression expression;

    public ExpressionRequirementsGenerator(Schema schema, Table table, CheckConstraint checkConstraint) {
        super(schema, table, checkConstraint);
        this.expression = checkConstraint.getExpression();
    }

    @Override
    public Requirements generateRequirements() {
        Requirements reqs = new Requirements();

        // TO DO: break out expressions .... (handle INs and BETWEENs separately ???)

        Predicate predicate = generatePredicate("Test " + expression + " evaluating to false");
        predicate.addClause(new ExpressionClause(table, expression, false));

        return reqs;
    }
}
