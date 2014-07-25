package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.List;

/**
 * Created by phil on 18/07/2014.
 */
public class PredicateGenerator {

    public static ComposedPredicate generateAcceptancePredicate(Schema schema, Table table) {
        return generateAcceptancePredicate(schema, table, true);
    }

    public static ComposedPredicate generateAcceptancePredicate(Schema schema, Table table, boolean truthValue) {
        return generateAcceptancePredicate(schema, table, truthValue, null);
    }

    public static ComposedPredicate generateAcceptancePredicate(Schema schema, Constraint ignoreConstraint) {
        return generateAcceptancePredicate(schema, ignoreConstraint.getTable(), true, ignoreConstraint);
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
                predicate = generateCheckConstraintPredicate(constraint, truthValue);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                predicate = generateForeignKeyConstraintPredicate(constraint, truthValue);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                predicate = generateNotNullConstraintPredicate(constraint, truthValue);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                predicate = generatePrimaryKeyConstraintPredicate(constraint, truthValue);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                predicate = generateUniqueConstraintPredicate(constraint, truthValue);
            }
        }.generateConditionPredicate(constraint, truthValue);
    }

    public static Predicate generateCheckConstraintPredicate(CheckConstraint checkConstraint, boolean truthValue) {
        ComposedPredicate composedPredicate;
        ExpressionPredicate expressionPredicate =
                new ExpressionPredicate(
                        checkConstraint.getTable(),
                        checkConstraint.getExpression(),
                        truthValue);
        boolean nullStatus;

        if (truthValue) {
            composedPredicate = new OrPredicate();
            nullStatus = true;
        } else {
            composedPredicate = new AndPredicate();
            nullStatus = false;
        }

        composedPredicate.addPredicate(expressionPredicate);
        addNullPredicates(
                composedPredicate,
                checkConstraint.getTable(),
                checkConstraint.getExpression().getColumnsInvolved(),
                nullStatus);

        return composedPredicate;
    }

    public static Predicate generateForeignKeyConstraintPredicate(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        ComposedPredicate composedPredicate;
        MatchPredicate matchPredicate;
        boolean nullStatus;

        if (truthValue) {
            composedPredicate = new OrPredicate();
            matchPredicate = generateAndMatch(
                    foreignKeyConstraint.getTable(),
                    foreignKeyConstraint.getColumns(),
                    foreignKeyConstraint.getReferenceTable(),
                    foreignKeyConstraint.getReferenceColumns());
            nullStatus = true;
        } else {
            composedPredicate = new AndPredicate();
            matchPredicate = generateOrNonMatch(
                    foreignKeyConstraint.getTable(),
                    foreignKeyConstraint.getColumns(),
                    foreignKeyConstraint.getReferenceTable(),
                    foreignKeyConstraint.getReferenceColumns());
            nullStatus = false;
        }

        composedPredicate.addPredicate(matchPredicate);
        addNullPredicates(
                composedPredicate,
                foreignKeyConstraint.getTable(),
                foreignKeyConstraint.getColumns(),
                nullStatus);
        return composedPredicate;
    }

    public static Predicate generateNotNullConstraintPredicate(NotNullConstraint notNullConstraint, boolean truthValue) {
        return new NullPredicate(notNullConstraint.getTable(), notNullConstraint.getColumn(), !truthValue);
    }

    public static Predicate generatePrimaryKeyConstraintPredicate(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        ComposedPredicate composedPredicate;
        MatchPredicate matchPredicate;
        boolean nullStatus;

        if (truthValue) {
            composedPredicate = new AndPredicate();
            matchPredicate = generateOrNonMatch(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns());
            nullStatus = false;
        } else {
            composedPredicate = new OrPredicate();
            matchPredicate = generateAndMatch(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns());
            nullStatus = true;
        }

        composedPredicate.addPredicate(matchPredicate);
        addNullPredicates(
                composedPredicate,
                primaryKeyConstraint.getTable(),
                primaryKeyConstraint.getColumns(),
                nullStatus);

        return composedPredicate;
    }

    public static Predicate generateUniqueConstraintPredicate(UniqueConstraint uniqueConstraint, boolean truthValue) {
        ComposedPredicate composedPredicate;
        MatchPredicate matchPredicate;
        boolean nullStatus;

        if (truthValue) {
            composedPredicate = new OrPredicate();
            matchPredicate = generateOrNonMatch(uniqueConstraint.getTable(), uniqueConstraint.getColumns());
            nullStatus = true;
        } else {
            composedPredicate = new AndPredicate();
            matchPredicate = generateAndMatch(uniqueConstraint.getTable(), uniqueConstraint.getColumns());
            nullStatus = false;
        }

        composedPredicate.addPredicate(matchPredicate);
        addNullPredicates(
                composedPredicate,
                uniqueConstraint.getTable(),
                uniqueConstraint.getColumns(),
                nullStatus);

        return composedPredicate;
    }

    public static ComposedPredicate addNullPredicates(ComposedPredicate composedPredicate, Table table, List<Column> columns, boolean truthValue) {
        for (Column column : columns) {
            composedPredicate.addPredicate(new NullPredicate(table, column, truthValue));
        }
        return composedPredicate;
    }

    public static MatchPredicate generateOrNonMatch(Table table, List<Column> columns) {
        return new MatchPredicate(
                table,
                MatchPredicate.EMPTY_COLUMN_LIST,
                columns,
                MatchPredicate.Mode.OR);
    }

    public static MatchPredicate generateOrNonMatch(Table table, List<Column> columns, Table refTable, List<Column> refColumns) {
        return new MatchPredicate(
                table,
                MatchPredicate.EMPTY_COLUMN_LIST,
                columns,
                refTable,
                MatchPredicate.EMPTY_COLUMN_LIST,
                refColumns,
                MatchPredicate.Mode.OR);
    }

    public static MatchPredicate generateAndMatch(Table table, List<Column> columns) {
        return new MatchPredicate(
                table,
                columns,
                MatchPredicate.EMPTY_COLUMN_LIST,
                MatchPredicate.Mode.AND);
    }

    public static MatchPredicate generateAndMatch(Table table, List<Column> columns, Table refTable, List<Column> refColumns) {
        return new MatchPredicate(
                table,
                columns,
                MatchPredicate.EMPTY_COLUMN_LIST,
                refTable,
                refColumns,
                MatchPredicate.EMPTY_COLUMN_LIST,
                MatchPredicate.Mode.AND);
    }
}
