package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.logic.ThreeVL;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.predicate.*;

import java.util.List;

/**
 * Created by phil on 18/07/2014.
 */
public class PredicateGenerator {

    public static ComposedPredicate generateAcceptancePredicate(Schema schema, Table table, boolean truthValue) {
        return generateAcceptancePredicate(schema, table, truthValue, null);
    }

    public static ComposedPredicate generateAcceptancePredicate(Schema schema, Table table, boolean truthValue, Constraint ignoreConstraint) {

        ComposedPredicate ap = truthValue ? new AndPredicate() : new OrPredicate();

        for (Constraint constraint : schema.getConstraints(table)) {
            if (!constraint.equals(ignoreConstraint)) {
                ap.addPredicate(generateConditionPredicate(constraint, truthValue));
            }
        }

        return ap;
    }

    public static Predicate generateConditionPredicate(Constraint constraint, boolean truthValue) {

        return new ConstraintVisitor() {
            Predicate predicate;
            boolean truthValue;

            Predicate generateConditionPredicate(Constraint constraint, boolean truthValue) {
                this.truthValue = truthValue;
                constraint.accept(this);
                return predicate;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                predicate = generateCheckConditionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                predicate = generateForeignKeyConditionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                predicate = generateNotNullConditionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                predicate = generatePrimaryKeyConditionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                predicate = generateUniqueConditionPredicate(constraint, truthValue);
            }
        }.generateConditionPredicate(constraint, truthValue);
    }

    public static Predicate generateExpressionPredicate(Constraint constraint, ThreeVL truthValue) {

        return new ConstraintVisitor() {
            Predicate predicate;
            ThreeVL truthValue;

            Predicate generateConditionPredicate(Constraint constraint, ThreeVL truthValue) {
                this.truthValue = truthValue;
                constraint.accept(this);
                return predicate;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                predicate = generateCheckExpressionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                predicate = generateForeignKeyExpressionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                predicate = generateNotNullExpressionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                predicate = generatePrimaryKeyExpressionPredicate(constraint, truthValue);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                predicate = generateUniqueExpressionPredicate(constraint, truthValue);
            }
        }.generateConditionPredicate(constraint, truthValue);
    }

    public static Predicate generateCheckConditionPredicate(CheckConstraint checkConstraint, boolean truthValue) {
        if (truthValue) {
            OrPredicate predicate = new OrPredicate();
            predicate.addPredicate(generateCheckExpressionPredicate(checkConstraint, ThreeVL.TRUE));
            return predicate;
        } else {
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(generateCheckExpressionPredicate(checkConstraint, ThreeVL.FALSE));
            predicate.addPredicate(generateCheckExpressionPredicate(checkConstraint, ThreeVL.UNKNOWN));
            return predicate;
        }
    }

    public static Predicate generateCheckExpressionPredicate(CheckConstraint checkConstraint, ThreeVL truthValue) {
        if (truthValue.isUnknown()) {
            OrPredicate predicate = new OrPredicate();
            addNullPredicates(predicate, checkConstraint.getTable(), checkConstraint.getExpression().getColumnsInvolved());
            return predicate;
        } else {
            return new ExpressionPredicate(checkConstraint.getTable(), checkConstraint.getExpression(), truthValue.isTrue());
        }
    }

    public static Predicate generateForeignKeyConditionPredicate(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        if (truthValue) {
            OrPredicate predicate = new OrPredicate();
            predicate.addPredicate(generateForeignKeyExpressionPredicate(foreignKeyConstraint, ThreeVL.TRUE));
            predicate.addPredicate(generateForeignKeyExpressionPredicate(foreignKeyConstraint, ThreeVL.UNKNOWN));
            return predicate;
        } else {
            return generateForeignKeyExpressionPredicate(foreignKeyConstraint, ThreeVL.UNKNOWN);
        }
    }

    public static Predicate generateForeignKeyExpressionPredicate(ForeignKeyConstraint foreignKeyConstraint, ThreeVL truthValue) {
        if (truthValue.isTrue()) {
            return new MatchPredicate(
                    foreignKeyConstraint.getTable(),
                    foreignKeyConstraint.getColumns(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    foreignKeyConstraint.getReferenceTable(),
                    foreignKeyConstraint.getReferenceColumns(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    MatchPredicate.Mode.AND);
        } else if (truthValue.isFalse()) {
            return new MatchPredicate(
                    foreignKeyConstraint.getTable(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    foreignKeyConstraint.getColumns(),
                    foreignKeyConstraint.getReferenceTable(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    foreignKeyConstraint.getReferenceColumns(),
                    MatchPredicate.Mode.OR);
        } else {
            OrPredicate predicate = new OrPredicate();
            addNullPredicates(predicate, foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns());
            return predicate;
        }
    }

    public static Predicate generateNotNullConditionPredicate(NotNullConstraint notNullConstraint, boolean truthValue) {
        return generateNotNullExpressionPredicate(notNullConstraint, ThreeVL.toThreeVL(truthValue));
    }

    public static Predicate generateNotNullExpressionPredicate(NotNullConstraint notNullConstraint, ThreeVL truthValue) {
        if (truthValue.isUnknown()) {
            return null;
        }
        return new NullPredicate(notNullConstraint.getTable(), notNullConstraint.getColumn(), truthValue.isFalse());
    }

    public static Predicate generatePrimaryKeyConditionPredicate(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        if (truthValue) {
            return generatePrimaryKeyExpressionPredicate(primaryKeyConstraint, ThreeVL.TRUE);
        } else {
            OrPredicate pkcp = new OrPredicate();
            pkcp.addPredicate(generatePrimaryKeyExpressionPredicate(primaryKeyConstraint, ThreeVL.FALSE));
            pkcp.addPredicate(generatePrimaryKeyExpressionPredicate(primaryKeyConstraint, ThreeVL.UNKNOWN));
            return pkcp;
        }
    }

    public static Predicate generatePrimaryKeyExpressionPredicate(PrimaryKeyConstraint primaryKeyConstraint, ThreeVL truthValue) {
        if (truthValue.isTrue()) {
            return new MatchPredicate(
                    primaryKeyConstraint.getTable(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    primaryKeyConstraint.getColumns(),
                    MatchPredicate.Mode.OR);
        } else if (truthValue.isFalse()) {
            return new MatchPredicate(
                    primaryKeyConstraint.getTable(),
                    primaryKeyConstraint.getColumns(),
                    MatchPredicate.EMPTY_COLUMN_LIST,
                    MatchPredicate.Mode.AND);
        } else {
            OrPredicate predicate = new OrPredicate();
            addNullPredicates(predicate, primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns());
            return predicate;
        }
    }

    public static Predicate generateUniqueConditionPredicate(UniqueConstraint uniqueConstraint, boolean truthValue) {
        if (truthValue) {
            return generateUniqueOrUnknownPredicate(uniqueConstraint);
        } else {
            return generateUniqueExpressionPredicate(uniqueConstraint, ThreeVL.FALSE);
        }
    }

    public static Predicate generateUniqueExpressionPredicate(UniqueConstraint uniqueConstraint, ThreeVL truthValue) {

        if (truthValue.isTrue()) {
            // All not equal/null
            AndPredicate topLevelPredicate = new AndPredicate();
            topLevelPredicate.addPredicate(generateNonUniqueOrUnknownPredicate(uniqueConstraint));

            // ... but at least one is not null
            OrPredicate nullPredicate = new OrPredicate();
            addNotNullPredicates(nullPredicate, uniqueConstraint.getTable(), uniqueConstraint.getColumns());
            topLevelPredicate.addPredicate(nullPredicate);

            return topLevelPredicate;
        }

        if (truthValue.isFalse()) {
            // All equal
            AndPredicate topLevelPredicate = new AndPredicate();
            topLevelPredicate.addPredicate(generateNonUniqueOrUnknownPredicate(uniqueConstraint));

            // ... and all not null
            AndPredicate nullPredicate = new AndPredicate();
            addNotNullPredicates(nullPredicate, uniqueConstraint.getTable(), uniqueConstraint.getColumns());
            topLevelPredicate.addPredicate(nullPredicate);

            return topLevelPredicate;
        }

        // if (truthValue.isUnknown()) {
            // All not equal
            AndPredicate topLevelPredicate = new AndPredicate();
            topLevelPredicate.addPredicate(generateNonUniqueOrUnknownPredicate(uniqueConstraint));

            // ... but at least one is null
            OrPredicate nullPredicate = new OrPredicate();
            addNullPredicates(nullPredicate, uniqueConstraint.getTable(), uniqueConstraint.getColumns());
            topLevelPredicate.addPredicate(nullPredicate);

            return topLevelPredicate;
        // }
    }

    private static Predicate generateUniqueOrUnknownPredicate(UniqueConstraint uniqueConstraint) {
        return new MatchPredicate(
                uniqueConstraint.getTable(),
                MatchPredicate.EMPTY_COLUMN_LIST,
                uniqueConstraint.getColumns(),
                MatchPredicate.Mode.OR);
    }

    private static Predicate generateNonUniqueOrUnknownPredicate(UniqueConstraint uniqueConstraint) {
        return new MatchPredicate(
                uniqueConstraint.getTable(),
                uniqueConstraint.getColumns(),
                MatchPredicate.EMPTY_COLUMN_LIST,
                MatchPredicate.Mode.AND);
    }

    private static void addNotNullPredicates(ComposedPredicate predicate, Table table, List<Column> columns) {
        for (Column column : columns) {
            predicate.addPredicate(new NullPredicate(table, column, false));
        }
    }

    private static void addNullPredicates(ComposedPredicate predicate, Table table, List<Column> columns) {
        for (Column column : columns) {
            predicate.addPredicate(new NullPredicate(table, column, true));
        }
    }
}
