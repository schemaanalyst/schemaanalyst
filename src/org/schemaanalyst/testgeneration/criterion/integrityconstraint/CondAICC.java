package org.schemaanalyst.testgeneration.criterion.integrityconstraint;


import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.criterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.criterion.predicate.OrPredicate;

import static org.schemaanalyst.testgeneration.criterion.integrityconstraint.PredicateGenerator.*;

/**
 * Created by phil on 18/07/2014.
 */
public class CondAICC extends AICC {

    public CondAICC(Schema schema) {
        super(schema);
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
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String idMessage = generateMessage(foreignKeyConstraint);

        if (truthValue) {
            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                            foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(), foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(), true));

            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, foreignKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                            foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=F",
                    topLevelPredicate);
        }
    }


    protected void generateNotNullConstraintRequirements(NotNullConstraint notNullConstraint, boolean truthValue) {
        String idMessage = generateMessage(notNullConstraint);

        if (truthValue) {
            // generate CC=T requirement
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=T",
                    generateAcceptancePredicate(schema, notNullConstraint.getTable(), true));
        } else {
            // generate CC=F requirement
            ComposedPredicate topLevelPredicate = generateAcceptancePredicate(schema, notNullConstraint);
            topLevelPredicate.addPredicate(
                    new NullPredicate(notNullConstraint.getTable(), notNullConstraint.getColumn(), true));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=T",
                    generateAcceptancePredicate(schema, notNullConstraint));
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String idMessage = generateMessage(primaryKeyConstraint);

        if (truthValue) {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(), primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns(), true));

            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, primaryKeyConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=F",
                    topLevelPredicate);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint uniqueConstraint, boolean truthValue) {
        ComposedPredicate topLevelPredicate;
        String idMessage = generateMessage(uniqueConstraint);

        if (truthValue) {
            // generate NPC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is NPC=T",
                    topLevelPredicate);
            topLevelPredicate.addPredicate(
                    addNullPredicates(
                            new OrPredicate(), uniqueConstraint.getTable(), uniqueConstraint.getColumns(), true));

            // generate CC=T requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            topLevelPredicate.addPredicate(
                    generateOrNonMatch(
                            uniqueConstraint.getTable(), uniqueConstraint.getColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=T",
                    topLevelPredicate);
        } else {
            // generate CC=F requirement
            topLevelPredicate = generateAcceptancePredicate(schema, uniqueConstraint);
            topLevelPredicate.addPredicate(
                    generateAndMatch(
                            uniqueConstraint.getTable(), uniqueConstraint.getColumns()));
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " is CC=F",
                    topLevelPredicate);
        }
    }
}
