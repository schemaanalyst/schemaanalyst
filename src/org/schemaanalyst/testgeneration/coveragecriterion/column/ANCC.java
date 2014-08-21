package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

import java.util.List;

/**
 * Created by phil on 18/08/2014.
 */
public class ANCC extends NCC {

    protected ConstraintSupplier constraintSupplier;

    public ANCC(Schema schema,
                TestRequirementIDGenerator testRequirementIDGenerator,
                ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator);
        this.constraintSupplier = constraintSupplier;
    }

    public String getName() {
        return "ANCC";
    }

    protected List<Column> getColumns(Table table) {
        List<Column> columns = table.getColumns();
        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            columns.remove(notNullConstraint.getColumn());
        }
        return columns;
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {
        List<Constraint> constraints = constraintSupplier.getConstraints(schema, table);

        ComposedPredicate topLevelPredicate = PredicateGenerator.generatePredicate(constraints);
        topLevelPredicate.addPredicate(new NullPredicate(table, column, truthValue));

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "NULL" : "NOT NULL")
                        ),
                        topLevelPredicate,
                        true,
                        false
                )
        );
    }
}
