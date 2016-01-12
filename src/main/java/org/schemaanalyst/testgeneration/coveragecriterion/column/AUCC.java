package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.addNullPredicate;

/**
 * Created by phil on 18/08/2014.
 */
public class AUCC extends UCC {

    private ConstraintSupplier constraintSupplier;
    private Map<Column, MultiColumnConstraint> clashingConstraints;

    public AUCC(Schema schema,
                TestRequirementIDGenerator testRequirementIDGenerator,
                ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator);
        this.constraintSupplier = constraintSupplier;
    }

    public String getName() {
        return "AUCC";
    }


    private List<MultiColumnConstraint> getUniquenessConstraints(Table table) {
        final List<MultiColumnConstraint> uniquenessConstraints = new ArrayList<>();

        for (Constraint constraint : constraintSupplier.getConstraints(schema, table)) {
            constraint.accept(new ConstraintAdaptor() {
                @Override
                public void visit(PrimaryKeyConstraint constraint) {
                    uniquenessConstraints.add(constraint);
                }

                @Override
                public void visit(UniqueConstraint constraint) {
                    uniquenessConstraints.add(constraint);
                }
            });
        }

        return uniquenessConstraints;
    }

    private void buildUniquenessConstraintMap(Table table) {
        clashingConstraints = new HashMap<>();

        for (MultiColumnConstraint uniquenessConstraint : getUniquenessConstraints(table)) {
            List<Column> uniqueConstraintColumns = uniquenessConstraint.getColumns();
            if (uniqueConstraintColumns.size() == 1) {
                Column column = uniqueConstraintColumns.get(0);
                clashingConstraints.put(column, uniquenessConstraint);
            }
        }
    }

    protected void generateRequirements(Table table) {
        buildUniquenessConstraintMap(table);
        super.generateRequirements(table);
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {
        List<Constraint> constraints = constraintSupplier.getConstraints(schema, table);

        AndPredicate predicate = new AndPredicate();
        predicate.addPredicate(generateMatchPredicate(table, column, truthValue));
        addNullPredicate(predicate, table, column, false);

        Constraint clashingConstraint = clashingConstraints.get(column);

        ComposedPredicate topLevelPredicate = PredicateGenerator.generatePredicate(constraints, clashingConstraint);
        topLevelPredicate.addPredicate(predicate);

        boolean result = clashingConstraint == null || truthValue;

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "UNIQUE" : "NOT UNIQUE")
                        ),
                        topLevelPredicate,
                        result,
                        true
                )
        );
    }
}
