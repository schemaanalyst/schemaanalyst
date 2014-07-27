package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;


import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.*;

/**
 * Created by phil on 18/07/2014.
 */
public class CondAICC extends AICC {

    public CondAICC(Schema schema, TestRequirementIDGenerator trIDGenerator) {
        super(schema, trIDGenerator);
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
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateCheckConstraintConditionPredicate(constraint, true, false));
        } else {
            // generate NPC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NPC=T",
                    generateCheckConstraintConditionPredicate(constraint, null, true));

            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateCheckConstraintConditionPredicate(constraint, false, false));
        }
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false));
        } else {
            // generate NPC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NPC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true));

            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false));
        }
    }


    protected void generateNotNullConstraintRequirements(NotNullConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    new NullPredicate(constraint.getTable(), constraint.getColumn(), false));
        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    new NullPredicate(constraint.getTable(), constraint.getColumn(), true));
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=T requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false));
        } else {
            // generate NPC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NPC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true));

            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false));
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint constraint, boolean truthValue) {
        if (truthValue) {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, false, false));

            // generate NPC=T requirement
            generateTestRequirement(
                    constraint,
                    " is NPC=T",
                    generateMultiColumnConstraintConditionPredicate(constraint, null, true));
        } else {
            // generate CC=F requirement
            generateTestRequirement(
                    constraint,
                    " is CC=F",
                    generateMultiColumnConstraintConditionPredicate(constraint, true, false));
        }
    }

    protected void generateTestRequirement(Constraint constraint, String msgSuffix, Predicate predicate) {
        String msg = generateMsg(constraint) + msgSuffix;

        ComposedPredicate topLevelPredicate = generateAcceptancePredicate(schema, constraint);
        topLevelPredicate.addPredicate(predicate);

        tr.addTestRequirement(trIDGenerator.nextID(), msg, topLevelPredicate);
    }
}
