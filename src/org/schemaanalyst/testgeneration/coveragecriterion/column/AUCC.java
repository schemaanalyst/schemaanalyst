package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;

import java.util.List;

/**
 * Created by phil on 18/08/2014.
 */
public class AUCC extends UCC {

    private ConstraintSupplier constraintSupplier;

    public AUCC(Schema schema,
                TestRequirementIDGenerator testRequirementIDGenerator,
                ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator);
        this.constraintSupplier = constraintSupplier;
    }

    public String getName() {
        return "AUCC";
    }

    protected List<Column> getColumns(Table table) {
        List<Column> columns = table.getColumns();
        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            List<Column> uniqueConstraintColumns = uniqueConstraint.getColumns();
            if (uniqueConstraintColumns.size() == 1) {
                columns.remove(uniqueConstraintColumns.get(0));
            }

        }
        return columns;
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {
        List<Constraint> constraints = constraintSupplier.getConstraints(schema, table);

        ComposedPredicate topLevelPredicate = PredicateGenerator.generatePredicate(constraints);
        topLevelPredicate.addPredicate(generateMatchPredicate(table, column, truthValue));

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "UNIQUE" : "NOT UNIQUE")
                        ),
                        topLevelPredicate,
                        truthValue,
                        true
                )
        );
    }
}
