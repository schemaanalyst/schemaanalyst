package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.DepthFirstSubExpressionVisitor;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 18/07/2014.
 */
public class PredicateGenerator {

    public static ComposedPredicate generatePredicate(List<Constraint> constraints) {
        return generatePredicate(constraints, true, null);
    }

    public static ComposedPredicate generatePredicate(List<Constraint> constraints, boolean truthValue) {
        return generatePredicate(constraints, truthValue, null);
    }

    public static ComposedPredicate generatePredicate(List<Constraint> constraints, Constraint ignoreConstraint) {
        return generatePredicate(constraints, true, ignoreConstraint);
    }

    public static ComposedPredicate generatePredicate(List<Constraint> constraints, boolean truthValue, Constraint ignoreConstraint) {
        ComposedPredicate ap = truthValue ? new AndPredicate() : new OrPredicate();
        for (Constraint constraint : constraints) {
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
        boolean nullStatus = truthValue;
        return generateCheckConstraintConditionPredicate(checkConstraint, truthValue, nullStatus);
    }

    public static Predicate generateForeignKeyConstraintPredicate(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        boolean match = truthValue;
        boolean nullStatus = truthValue;
        return generateMultiColumnConstraintConditionPredicate(foreignKeyConstraint, match, nullStatus);
    }

    public static Predicate generateNotNullConstraintPredicate(NotNullConstraint notNullConstraint, boolean truthValue) {
        return new NullPredicate(notNullConstraint.getTable(), notNullConstraint.getColumn(), !truthValue);
    }

    public static Predicate generatePrimaryKeyConstraintPredicate(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        boolean match = !truthValue;
        boolean nullStatus = !truthValue;
        return generateMultiColumnConstraintConditionPredicate(primaryKeyConstraint, match, nullStatus);
    }

    public static Predicate generateUniqueConstraintPredicate(UniqueConstraint uniqueConstraint, boolean truthValue) {
        boolean match = !truthValue;
        boolean nullStatus = truthValue;
        return generateMultiColumnConstraintConditionPredicate(uniqueConstraint, match, nullStatus);
    }

    public static Predicate generateCheckConstraintConditionPredicate(CheckConstraint constraint, Boolean truthValue, boolean nullStatus) {
        Table table = constraint.getTable();
        Expression expression = constraint.getExpression();

        ComposedPredicate predicate = (nullStatus)
                ? new OrPredicate()
                : new AndPredicate();

        if (truthValue != null) {
            ExpressionPredicate expressionPredicate = new ExpressionPredicate(table, expression, truthValue);
            predicate.addPredicate(expressionPredicate);
        }

        addNullPredicates(
                predicate,
                table,
                columnsNotInNullExpressions(expression),
                nullStatus);

        if (predicate.numSubPredicates() > 0) {
            return predicate;
        } else {
            // The predicate has no sub clauses. This means that it is
            // impossible to generate the predicate (this can happen with "x IS NOT NULL" == unknown),
            // so we generate an infeasible predicate for accounting purposes
            return generateDummyInfeasiblePredicate(table);
        }
    }

    protected static List<Column> columnsNotInNullExpressions(Expression expression) {
        List<Column> nullColumns = new DepthFirstSubExpressionVisitor() {
            List<Column> nullColumns;

            public List<Column> getNullColumns(Expression expression) {
                nullColumns = new ArrayList<>();
                expression.accept(this);
                return nullColumns;
            }

            public void visit(NullExpression nullExpression) {
                nullColumns.addAll(nullExpression.getColumnsInvolved());
            }
        }.getNullColumns(expression);
        List<Column> columns = expression.getColumnsInvolved();
        columns.removeAll(nullColumns);
        return columns;
    }

    public static Predicate generateMultiColumnConstraintConditionPredicate(MultiColumnConstraint constraint, Boolean match, boolean nullStatus) {
        Table table = constraint.getTable();
        List<Column> columns = constraint.getColumns();

        ComposedPredicate predicate = (nullStatus)
                ? new OrPredicate()
                : new AndPredicate();

        if (match != null) {
            Table refTable = table;
            List<Column> refColumns = columns;

            if (constraint instanceof ForeignKeyConstraint) {
                ForeignKeyConstraint fkConstraint = (ForeignKeyConstraint) constraint;
                refTable = fkConstraint.getReferenceTable();
                refColumns = fkConstraint.getReferenceColumns();
            }

            MatchPredicate matchPredicate = (match)
                    ? generateAndMatchPredicate(table, columns, refTable, refColumns)
                    : generateOrNonMatchPredicate(table, columns, refTable, refColumns);

            predicate.addPredicate(matchPredicate);
        }

        addNullPredicates(predicate, table, columns, nullStatus);

        return predicate;
    }

    public static MatchPredicate generateOrNonMatchPredicate(Table table, List<Column> columns, Table refTable, List<Column> refColumns) {
        return new MatchPredicate(
                table,
                MatchPredicate.EMPTY_COLUMN_LIST,
                columns,
                refTable,
                MatchPredicate.EMPTY_COLUMN_LIST,
                refColumns,
                MatchPredicate.Mode.OR);
    }

    public static MatchPredicate generateAndMatchPredicate(Table table, List<Column> columns, Table refTable, List<Column> refColumns) {
        return new MatchPredicate(
                table,
                columns,
                MatchPredicate.EMPTY_COLUMN_LIST,
                refTable,
                refColumns,
                MatchPredicate.EMPTY_COLUMN_LIST,
                MatchPredicate.Mode.AND);
    }

    public static ComposedPredicate addNullPredicate(ComposedPredicate composedPredicate, Table table, Column column, boolean truthValue) {
        return addNullPredicates(composedPredicate, table, Arrays.asList(column), truthValue);
    }

    public static ComposedPredicate addNullPredicates(ComposedPredicate composedPredicate, Table table, List<Column> columns, boolean truthValue) {
        for (Column column : columns) {
            composedPredicate.addPredicate(new NullPredicate(table, column, truthValue));
        }
        return composedPredicate;
    }

    // sometimes a dummy infeasible predicates are needed when the real (infeasible) predicate is impossible to generate
    public static AndPredicate generateDummyInfeasiblePredicate(Table table) {
        Column col = table.getColumns().get(0);
        AndPredicate andPredicate = new AndPredicate();
        andPredicate.addPredicate(new NullPredicate(table, col, true));
        andPredicate.addPredicate(new NullPredicate(table, col, false));
        return andPredicate;
    }
}
