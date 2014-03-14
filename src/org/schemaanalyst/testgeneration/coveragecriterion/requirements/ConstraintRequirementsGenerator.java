package org.schemaanalyst.testgeneration.coveragecriterion.requirements;

import org.schemaanalyst.logic.predicate.ConstraintPredicateGenerator;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 20/01/2014.
 */
public abstract class ConstraintRequirementsGenerator {

    protected Schema schema;
    protected Table table;
    protected Constraint constraint;

    private ConstraintPredicateGenerator predicateGenerator;

    public ConstraintRequirementsGenerator(Schema schema, Table table) {
        this(schema, table, true);
    }

    public ConstraintRequirementsGenerator(Schema schema, Table table, boolean generateFullPredicate) {
        this.schema = schema;
        this.table = table;
        this.constraint = null;

        if (generateFullPredicate) {
            predicateGenerator = new ConstraintPredicateGenerator(schema, table);
        }
    }

    public ConstraintRequirementsGenerator(Schema schema, Constraint constraint) {
        this(schema, constraint, true);
    }

    public ConstraintRequirementsGenerator(Schema schema, Constraint constraint, boolean generateFullPredicate) {
        this.schema = schema;
        this.table = constraint.getTable();
        this.constraint = constraint;

        if (generateFullPredicate) {
            predicateGenerator = new ConstraintPredicateGenerator(schema, table, constraint);
        }
    }

    public abstract Requirements generateRequirements();

    protected Predicate generatePredicate(String purpose) {
        return generatePredicate(Arrays.asList(purpose));
    }

    protected Predicate generatePredicate(List<String> purposes) {
        return predicateGenerator == null
                ? new Predicate(purposes)
                : predicateGenerator.generate(purposes);
    }
}