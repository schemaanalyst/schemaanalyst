package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public abstract class ExpressionRACRequirementsGenerator extends RequirementsGenerator {

    private Predicate predicate;

    public ExpressionRACRequirementsGenerator(Schema schema, Table table, Predicate predicate) {
        super(schema, table);
        this.predicate = predicate;
    }

    public List<Predicate> generateRequirements() {
        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(generateTruePredicates());
        predicates.addAll(generateFalsePredicates());
        return predicates;
    }

    public Predicate generateTruePredicate() {
        return generateTruePredicates().get(0);
    }

    public Predicate generateFalsePredicate() {
        return generateFalsePredicates().get(0);
    }

    public abstract List<Predicate> generateTruePredicates();

    public abstract List<Predicate> generateFalsePredicates();

    protected Predicate generatePredicate(String purpose) {
        Predicate predicate = this.predicate.duplicate();
        predicate.setPurpose(purpose);
        return predicate;
    }
}
