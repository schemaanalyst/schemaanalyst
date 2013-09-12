/*
 */
package org.schemaanalyst.mutation.equivalence;

import java.util.List;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;

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
public class SchemaEquivalanceWithNotNullCheckChecker extends SchemaEquivalenceChecker {

    public SchemaEquivalanceWithNotNullCheckChecker() {
        super();
    }
    
    public SchemaEquivalanceWithNotNullCheckChecker(TableEquivalenceChecker tableEquivalenceChecker, ColumnEquivalenceChecker columnEquivalenceChecker, PrimaryKeyEquivalenceChecker primaryKeyEquivalenceChecker, ForeignKeyEquivalenceChecker foreignKeyEquivalenceChecker, UniqueEquivalenceChecker uniqueEquivalenceChecker, CheckEquivalenceChecker checkEquivalenceChecker, NotNullEquivalenceChecker notNullEquivalenceChecker) {
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
        } else if (!notNullEquivalenceChecker.areEquivalent(a.getNotNullConstraints(), b.getNotNullConstraints())
                && !hasEquivalentCheck(a, b)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean hasEquivalentNotNull(Schema a, Schema b) {
        if (a.getCheckConstraints().size() == b.getCheckConstraints().size()) {
            // There has to be exactly one 'odd' constraint
            return false;
        } else {
            // Find which direction the difference is in
            List<CheckConstraint> aNotb = checkEquivalenceChecker.subtract(a.getCheckConstraints(), b.getCheckConstraints());
            List<CheckConstraint> bNota = checkEquivalenceChecker.subtract(b.getCheckConstraints(), a.getCheckConstraints());
            if (aNotb.size() == 1 && bNota.isEmpty()) {
                List<CheckConstraint> restOfA = a.getCheckConstraints();
                restOfA.removeAll(aNotb);
                return hasMatchingNotNull(aNotb.get(0), b) && checkEquivalenceChecker.areEquivalent(restOfA, b.getCheckConstraints());
            } else if (bNota.size() == 1 && aNotb.isEmpty()) {
                List<CheckConstraint> restOfB = b.getCheckConstraints();
                restOfB.removeAll(bNota);
                return hasMatchingNotNull(bNota.get(0), a) && checkEquivalenceChecker.areEquivalent(restOfB, a.getCheckConstraints());
            } else {
                return false;
            }
        }
    }

    private boolean hasEquivalentCheck(Schema a, Schema b) {
        if (a.getNotNullConstraints().size() == b.getNotNullConstraints().size()) {
            // There has to be exactly one 'odd' constraint
            return false;
        } else {
            // Find which direction the difference is in
            List<NotNullConstraint> aNotb = notNullEquivalenceChecker.subtract(a.getNotNullConstraints(), b.getNotNullConstraints());
            List<NotNullConstraint> bNota = notNullEquivalenceChecker.subtract(b.getNotNullConstraints(), a.getNotNullConstraints());
            if (aNotb.size() == 1 && bNota.isEmpty()) {
                List<NotNullConstraint> restOfA = a.getNotNullConstraints();
                restOfA.removeAll(aNotb);
                return hasMatchingCheck(aNotb.get(0), b) && notNullEquivalenceChecker.areEquivalent(restOfA, b.getNotNullConstraints());
            } else if (bNota.size() == 1 && aNotb.isEmpty()) {
                List<NotNullConstraint> restOfB = b.getNotNullConstraints();
                restOfB.removeAll(bNota);
                return hasMatchingCheck(bNota.get(0), a) && notNullEquivalenceChecker.areEquivalent(restOfB, a.getNotNullConstraints());
            } else {
                return false;
            }
        }
    }

    private boolean hasMatchingNotNull(CheckConstraint check, Schema s) {
        Expression e = check.getExpression();
        // Simplify away parens
        while (e.getClass().equals(ParenthesisedExpression.class)) {
            e = e.getSubexpression(0);
        }
        // Traverse down to the NULL part
        if (!e.getClass().equals(NullExpression.class)) {
            return false;
        } else {
            NullExpression ne = (NullExpression) e;
            // Check it is a NOT NULL
            if (!ne.isNotNull()) {
                return false;
            } else {
                // Check it refers to a column
                Expression lhs = ne.getSubexpression(0);
                if (!lhs.getClass().equals(ColumnExpression.class)) {
                    return false;
                } else {
                    // Get the table and column it refers to
                    ColumnExpression ce = (ColumnExpression) lhs;
                    Table table = ce.getTable();
                    Column column = ce.getColumn();
                    // Search for an equivalent NOT NULL constraint
                    for (NotNullConstraint notNull : s.getNotNullConstraints()) {
                        if (notNull.getTable().getIdentifier().equals(table.getIdentifier())
                                && notNull.getColumn().getIdentifier().equals(column.getIdentifier())) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }

    }

    private boolean hasMatchingCheck(NotNullConstraint notNull, Schema a) {
        Table table = notNull.getTable();
        Column column = notNull.getColumn();
        for (CheckConstraint cc : a.getCheckConstraints()) {
            if (cc.getTable().getIdentifier().equals(table.getIdentifier())) {
                Expression e = cc.getExpression();
                // Simplify away parens
                while (e.getClass().equals(ParenthesisedExpression.class)) {
                    e = e.getSubexpression(0);
                }
                if (e.getClass().equals(NullExpression.class)) {
                    NullExpression ne = (NullExpression) e;
                    // Check it is a NOT NULL
                    if (ne.isNotNull()) {
                        // Check it refers to a column
                        Expression lhs = ne.getSubexpression(0);
                        if (lhs.getClass().equals(ColumnExpression.class)) {
                            ColumnExpression ce = (ColumnExpression) lhs;
                            if (column.getIdentifier().equals(ce.getColumn().getIdentifier())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
