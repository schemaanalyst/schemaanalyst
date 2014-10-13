package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ConstraintAdaptor;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phil on 18/08/2014.
 */
public class ANCC extends NCC {

    protected ConstraintSupplier constraintSupplier;
    private Map<Column, Constraint> clashingConstraints;

    public ANCC(Schema schema,
                TestRequirementIDGenerator testRequirementIDGenerator,
                ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator);
        this.constraintSupplier = constraintSupplier;
    }

    public String getName() {
        return "ANCC";
    }

    private void buildClashingConstraintMap(Table table) {
        clashingConstraints = new HashMap<>();

        for (Constraint constraint : constraintSupplier.getConstraints(schema, table)) {
            constraint.accept(new ConstraintAdaptor() {
                @Override
                public void visit(NotNullConstraint constraint) {
                    clashingConstraints.put(constraint.getColumn(), constraint);
                }

                @Override
                public void visit(PrimaryKeyConstraint constraint) {
                    for (Column column : constraint.getColumns()) {
                        clashingConstraints.put(column, constraint);
                    }
                }
            });
        }
    }

    protected void generateRequirements(Table table) {
        buildClashingConstraintMap(table);
        super.generateRequirements(table);
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {
        List<Constraint> constraints = constraintSupplier.getConstraints(schema, table);

        Constraint clashingConstraint = clashingConstraints.get(column);
        ComposedPredicate topLevelPredicate;

        if (clashingConstraint != null && !truthValue) {
            // THERE IS A clashing constraint AND we want the column to be NOT NULL....

            // just satisfy the original constraint if it was supposed to be NOT NULL and
            // there is a clashing constraint (which could be a PRIMARY KEY and embody the additional
            // property of uniqueness)
            topLevelPredicate = PredicateGenerator.generatePredicate(constraints);
        } else {
            // remove clashing constraint to prevent an infeasible requirement
            topLevelPredicate = PredicateGenerator.generatePredicate(constraints, clashingConstraint);
            topLevelPredicate.addPredicate(new NullPredicate(table, column, truthValue));
        }

        boolean result = !truthValue || clashingConstraint == null;

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "NULL" : "NOT NULL")
                        ),
                        topLevelPredicate,
                        result,
                        false
                )
        );
    }
}
