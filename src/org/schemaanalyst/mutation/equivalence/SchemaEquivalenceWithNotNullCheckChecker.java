/*
 */
package org.schemaanalyst.mutation.equivalence;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.*;

/**
 * <p>
 * An {@link EquivalenceChecker} that compares two {@link Schema} objects to
 * determine if they are equivalent, allowing {@link NotNullConstraint}s and
 * {@link Expression}s with {@link NullExpression}s to be equivalent. Otherwise,
 * this class is equivalent to {@link SchemaEquivalenceChecker}.
 * </p>
 *
 * @author Chris J. Wright
 */
public class SchemaEquivalenceWithNotNullCheckChecker extends SchemaEquivalenceChecker {

    public SchemaEquivalenceWithNotNullCheckChecker() {
        super();
    }

    public SchemaEquivalenceWithNotNullCheckChecker(TableEquivalenceChecker tableEquivalenceChecker, ColumnEquivalenceChecker columnEquivalenceChecker, PrimaryKeyEquivalenceChecker primaryKeyEquivalenceChecker, ForeignKeyEquivalenceChecker foreignKeyEquivalenceChecker, UniqueEquivalenceChecker uniqueEquivalenceChecker, CheckEquivalenceChecker checkEquivalenceChecker, NotNullEquivalenceChecker notNullEquivalenceChecker) {
        super(tableEquivalenceChecker, columnEquivalenceChecker, primaryKeyEquivalenceChecker, foreignKeyEquivalenceChecker, uniqueEquivalenceChecker, checkEquivalenceChecker, notNullEquivalenceChecker);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean areEquivalent(Schema a, Schema b) {
        if (super.areEquivalent(a, b)) {
            return true;
        } else if (!a.getIdentifier().equals(b.getIdentifier())) {
            return false;
        } else if (a.getTables().size() != b.getTables().size()) {
            return false;
        } else if (!tableEquivalenceChecker.areEquivalent(a.getTablesInOrder(), b.getTablesInOrder())) {
            return false;
        } else if (!primaryKeyEquivalenceChecker.areEquivalent(a.getPrimaryKeyConstraints(), b.getPrimaryKeyConstraints())) {
            return false;
        } else if (!foreignKeyEquivalenceChecker.areEquivalent(a.getForeignKeyConstraints(), b.getForeignKeyConstraints())) {
            return false;
        } else if (!uniqueEquivalenceChecker.areEquivalent(a.getUniqueConstraints(), b.getUniqueConstraints())) {
            return false;
        } else if (!checkEquivalenceChecker.areEquivalent(a.getCheckConstraints(), b.getCheckConstraints())
                && !hasEquivalentNotNull(a, b)) {
            return false;
        } else return notNullEquivalenceChecker.areEquivalent(a.getNotNullConstraints(), b.getNotNullConstraints()) || hasEquivalentCheck(a, b);
    }

    /**
     * Finds if there exists a NOT NULL that is equivalent to each excess CHECK 
     * constraint present in either schema.
     * 
     * @param a Schema a
     * @param b Schema b
     * @return If all excess CHECKs have equivalent NOT NULLs
     */
    private boolean hasEquivalentNotNull(Schema a, Schema b) {
        // Find which direction the difference is in
        List<CheckConstraint> aNotb = checkEquivalenceChecker.subtract(a.getCheckConstraints(), b.getCheckConstraints());
        List<CheckConstraint> bNota = checkEquivalenceChecker.subtract(b.getCheckConstraints(), a.getCheckConstraints());
        if (aNotb.size() > 0 && bNota.isEmpty()) {
            List<CheckConstraint> restOfA = a.getCheckConstraints();
            restOfA.removeAll(aNotb);
            return findMatchingNotNull(aNotb, b.getNotNullConstraints()) && checkEquivalenceChecker.areEquivalent(restOfA, b.getCheckConstraints());
        } else if (bNota.size() > 0 && aNotb.isEmpty()) {
            List<CheckConstraint> restOfB = b.getCheckConstraints();
            restOfB.removeAll(bNota);
            return findMatchingNotNull(bNota, a.getNotNullConstraints()) && checkEquivalenceChecker.areEquivalent(restOfB, a.getCheckConstraints());
        } else {
            return false;
        }
    }

    /**
     * Finds if there exists a CHECK that is equivalent to each excess NOT NULL 
     * constraint present in either schema.
     * 
     * @param a Schema a
     * @param b Schema b
     * @return If all excess NOT NULLs have equivalent CHECKs
     */
    private boolean hasEquivalentCheck(Schema a, Schema b) {
        // Find which direction the difference is in
        List<NotNullConstraint> aNotb = notNullEquivalenceChecker.subtract(a.getNotNullConstraints(), b.getNotNullConstraints());
        List<NotNullConstraint> bNota = notNullEquivalenceChecker.subtract(b.getNotNullConstraints(), a.getNotNullConstraints());
        if (aNotb.size() > 0 && bNota.isEmpty()) {
            List<NotNullConstraint> restOfA = a.getNotNullConstraints();
            restOfA.removeAll(aNotb);
            return findMatchingCheck(aNotb, b.getCheckConstraints()) && notNullEquivalenceChecker.areEquivalent(restOfA, b.getNotNullConstraints());
        } else if (bNota.size() > 0 && aNotb.isEmpty()) {
            List<NotNullConstraint> restOfB = b.getNotNullConstraints();
            restOfB.removeAll(bNota);
            return findMatchingCheck(bNota, a.getCheckConstraints()) && notNullEquivalenceChecker.areEquivalent(restOfB, a.getNotNullConstraints());
        } else {
            return false;
        }
    }

    /**
     * Finds if all given CHECKs have an equivalent NOT NULL
     * 
     * @param checks The CHECKs
     * @param notNulls The NOT NULLs
     * @return If all given CHECKs have an equivalent NOT NULL
     */
    private boolean findMatchingNotNull(List<CheckConstraint> checks, List<NotNullConstraint> notNulls) {
        // Extract the expressions
        List<Expression> exprs = new ArrayList<>();
        for (CheckConstraint check : checks) {
            exprs.add(check.getExpression());
        }
        // Simplify the expressions
        exprs = flatten(exprs);
        // Check we're only looking not NOT NULLs
        for (Expression expr : exprs) {
            if (!(expr instanceof NullExpression)) {
                return false;
            }
        }
        // Search for NOT NULLs
        for (Expression expr : exprs) {
            NullExpression nExpr = (NullExpression) expr;
            // Cannot match to IS NULL
            if (!nExpr.isNotNull()) {
                return false;
            }
            ColumnExpression colExpr = (ColumnExpression) nExpr.getSubexpression(NullExpression.SUBEXPRESSION);
            Column column = colExpr.getColumn();
            Table table = colExpr.getTable();
            boolean found = false;
            for (NotNullConstraint notNull : notNulls) {
                if (notNull.getColumn().getIdentifier().equals(column.getIdentifier())
                        && notNull.getTable().getIdentifier().equals(table.getIdentifier())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds if all given NOT NULLs have an equivalent CHECK
     * 
     * @param notNulls The NOT NULLs
     * @param checks The CHECKS
     * @return If all given NOT NULLs have an equivalent CHECK
     */
    private boolean findMatchingCheck(List<NotNullConstraint> notNulls, List<CheckConstraint> checks) {
        // Copy the original list
        notNulls = new ArrayList<>(notNulls);
        // Extract the expressions
        List<Expression> exprs = new ArrayList<>();
        for (CheckConstraint check : checks) {
            exprs.add(check.getExpression());
        }
        // Simplify the expressions
        exprs = flatten(exprs);
        // Check we're only looking not NOT NULLs
        for (Iterator<Expression> iter = exprs.iterator(); iter.hasNext();) {
            Expression expr = iter.next();
            if (!(expr instanceof NullExpression)) {
                iter.remove();
            } else {
                // Cannot match to IS NULL
                if (!((NullExpression) expr).isNotNull()) {
                    iter.remove();
                }
            }
        }
        // Search for checks
        for (NotNullConstraint notNull : notNulls) {
            Table table = notNull.getTable();
            Column column = notNull.getColumn();
            boolean found = false;
            for (int i = 0; i < exprs.size(); i++) {
                NullExpression nExpr = (NullExpression) exprs.get(i);
                ColumnExpression colExpr = (ColumnExpression) nExpr.getSubexpression(NullExpression.SUBEXPRESSION);
                Column eColumn = colExpr.getColumn();
                Table eTable = colExpr.getTable();
                if (table.getIdentifier().equals(eTable.getIdentifier())
                        && column.getIdentifier().equals(eColumn.getIdentifier())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Splits an AND into its two subexpressions, or returns a list of
     * containing the original expression.
     *
     * @param expr The expression
     * @return The subexpressions or the original expression
     */
    private List<Expression> splitAnd(Expression expr) {
        if (expr instanceof AndExpression) {
            return expr.getSubexpressions();
        } else {
            return Arrays.asList(expr);
        }
    }

    /**
     * Extracts the expression from inside a parenthesised expression, or
     * returns the expression if it is not parenthesised.
     *
     * @param expr The expression
     * @return The subexpression or the original expression
     */
    private Expression removeParens(Expression expr) {
        if (expr instanceof ParenthesisedExpression) {
            return expr.getSubexpression(ParenthesisedExpression.SUBEXPRESSION);
        } else {
            return expr;
        }
    }

    /**
     * Simplifies nested expressions into a list of expressions that are
     * equivalent. Extracts subexpressions from ANDs and removes parenthesis, as
     * well as removing any duplicates.
     *
     * @param expr The expression to flatten
     * @return The simplified expression
     */
    public List<Expression> flatten(Expression expr) {
        List<Expression> exprs = new ArrayList<>();
        exprs.add(expr);
        return flatten(exprs);
    }

    /**
     * Simplifies nested expressions into a list of expressions that are
     * equivalent. Extracts subexpressions from ANDs and removes parenthesis, as
     * well as removing any duplicates.
     *
     * @param exprs The expressions to flatten
     * @return The simplified expressions
     */
    public List<Expression> flatten(List<Expression> exprs) {
        Set<Expression> initial;
        Set<Expression> temp;
        Set<Expression> flattened = new HashSet<>(exprs);
        // Loop until there are no changes
        do {
            initial = flattened;
            flattened = new HashSet<>();
            // Remove ANDs
            for (Expression expr : initial) {
                flattened.addAll(splitAnd(expr));
            }
            temp = flattened;
            flattened = new HashSet<>();
            // Remove parens
            for (Expression expr : temp) {
                flattened.add(removeParens(expr));
            }
        } while (!initial.containsAll(flattened));
        return new ArrayList<>(flattened);
    }
}
