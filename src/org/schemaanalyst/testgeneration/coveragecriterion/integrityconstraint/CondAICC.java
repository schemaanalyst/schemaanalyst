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

    protected void generateCheckConstraintRequirements(CheckConstraint checkConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String descMsg = generateMsg(checkConstraint);

        if (truthValue) {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, checkConstraint);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(checkConstraint.getTable()),
                            checkConstraint.getTable(),
                            checkConstraint.getExpression().getColumnsInvolved(),
                            true));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is NPC=T",
                    topLevelPredicate);

            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, checkConstraint);
            AndPredicate cctPredicate = new AndPredicate(checkConstraint.getTable());
            cctPredicate.addPredicate(new ExpressionPredicate(checkConstraint.getTable(), checkConstraint.getExpression(), true));
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            cctPredicate, checkConstraint.getTable(), checkConstraint.getExpression().getColumnsInvolved(), false));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=T",
                    topLevelPredicate);

        } else {
            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, checkConstraint);
            AndPredicate ccfPredicate = new AndPredicate(checkConstraint.getTable());
            ccfPredicate.addPredicate(new ExpressionPredicate(checkConstraint.getTable(), checkConstraint.getExpression(), false));
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            ccfPredicate, checkConstraint.getTable(), checkConstraint.getExpression().getColumnsInvolved(), false));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=F",
                    topLevelPredicate);

        }
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String descMsg = generateMsg(foreignKeyConstraint);

        if (truthValue) {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(foreignKeyConstraint.getTable()),
                            foreignKeyConstraint.getTable(),
                            foreignKeyConstraint.getColumns(),
                            true));

            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                            foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                            foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=F",
                    topLevelPredicate);
        }
    }


    protected void generateNotNullConstraintRequirements(NotNullConstraint notNullConstraint, boolean truthValue) {
        String descMsg = generateMsg(notNullConstraint);

        if (truthValue) {
            // generate CC=T requirement
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=T",
                    generateAcceptancePredicate(schema, notNullConstraint.getTable(), true));
        } else {
            // generate CC=F requirement
            ComposedPredicate topLevelPredicate = generateAcceptancePredicate(schema, notNullConstraint);
            topLevelPredicate.addPredicate(
                    new NullPredicate(notNullConstraint.getTable(), notNullConstraint.getColumn(), true));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=F",
                    generateAcceptancePredicate(schema, notNullConstraint));
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String descMsg = generateMsg(primaryKeyConstraint);

        if (truthValue) {
            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(primaryKeyConstraint.getTable()),
                            primaryKeyConstraint.getTable(),
                            primaryKeyConstraint.getColumns(),
                            true));

            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=F",
                    topLevelPredicate);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint uniqueConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String descMsg = generateMsg(uniqueConstraint);

        if (truthValue) {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(uniqueConstraint.getTable()),
                            uniqueConstraint.getTable(),
                            uniqueConstraint.getColumns(),
                            true));

            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            uniqueConstraint.getTable(), uniqueConstraint.getColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            uniqueConstraint.getTable(), uniqueConstraint.getColumns()));
            tr.addTestRequirement(
                    trIDGenerator.nextID(),
                    descMsg + " is CC=F",
                    topLevelPredicate);
        }
    }
}
