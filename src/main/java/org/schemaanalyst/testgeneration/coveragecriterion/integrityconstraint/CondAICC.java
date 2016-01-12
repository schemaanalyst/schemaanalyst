package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;


import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.*;

/**
 * Created by phil on 18/07/2014.
 */
public class CondAICC extends AICC {

    public CondAICC(Schema schema,
                    TestRequirementIDGenerator testRequirementIDGenerator,
                    ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "Cond-AICC";
    }

    protected void generateRequirements(Constraint constraint, final boolean truthValue) {
        new ConstraintVisitor() {
            void generateRequirements(Constraint constraint) {
                constraint.accept(this);
            }

            @Override
            public void visit(CheckConstraint constraint) {
                generateCheckConstraintRequirements(constraint, truthValue);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                generateForeignKeyConstraintRequirements(constraint, truthValue);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                generateNotNullConstraintRequirements(constraint, truthValue);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                generatePrimaryKeyConstraintRequirements(constraint, truthValue);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                generateUniqueConstraintRequirements(constraint, truthValue);
            }
        }.generateRequirements(constraint);
    }

    protected void generateCheckConstraintRequirements(CheckConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate NC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NC=T",
                    generateCheckConstraintConditionPredicate(constraint, null, true),
                    true);

            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateCheckConstraintConditionPredicate(constraint, true, false),
                    true);

        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateCheckConstraintConditionPredicate(constraint, false, false),
                    false);
        }
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate NC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true),
                    true);

            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false),
                    true);
        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false),
                    false);
        }
    }


    protected void generateNotNullConstraintRequirements(NotNullConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    new NullPredicate(constraint.getTable(), constraint.getColumn(), false),
                    true);
        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    new NullPredicate(constraint.getTable(), constraint.getColumn(), true),
                    false);
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false),
                    true);
        } else {
            // generate NC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true),
                    false);

            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false),
                    false);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate NC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true),
                    true);

            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false),
                    true);
        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false),
                    false);
        }
    }

    protected void generateTestRequirement(Constraint constraint, String msgSuffix, Predicate predicate, Boolean result) {
        ComposedPredicate topLevelPredicate = generatePredicate(getConstraints(constraint.getTable()), constraint);
        topLevelPredicate.addPredicate(predicate);

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                generateMsg(constraint) + msgSuffix
                        ),
                        topLevelPredicate,
                        result,
                        doesRequirementRequiresComparisonRow(constraint)
                )
        );
    }
}
