package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
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
        }

        return requirements;
    }
}
