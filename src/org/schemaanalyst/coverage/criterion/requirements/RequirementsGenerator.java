package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.predicate.ConstraintPredicateGenerator;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.List;

/**
 * Created by phil on 20/01/2014.
 */
public abstract class RequirementsGenerator {

    protected Schema schema;
    protected Table table;
    protected Constraint constraint;

    private ConstraintPredicateGenerator predicateGenerator;

    public RequirementsGenerator(Schema schema, Table table) {
        this(schema, table, null);
    }

    public RequirementsGenerator(Schema schema, Table table, Constraint constraint) {
        this.schema = schema;
        this.table = table;
        this.constraint = constraint;

        predicateGenerator = new ConstraintPredicateGenerator(schema, table, constraint);
    }

    public abstract List<Predicate> generateRequirements();

    protected Predicate generatePredicate(String purpose) {
        return predicateGenerator.generate(purpose);
    }
}