package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.ConstraintPredicateGenerator;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

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
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        // TO DO: break out expressions .... (handle INs and BETWEENs separately ???)

        // Expression is TRUE requirement
        Predicate truePredicate = predicateGenerator.generate(
                "Test " + expression + " evaluating to true");
        truePredicate.addClause(new ExpressionClause(table, expression, true));
        //requirements.add(truePredicate);

        // Expression is FALSE requirement
        Predicate falsePredicate = predicateGenerator.generate(
                "Test " + expression + " evaluating to false");
        falsePredicate.addClause(new ExpressionClause(table, expression, false));
        //requirements.add(falsePredicate);

        return requirements;
    }
}
