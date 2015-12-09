package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.dbms.postgres.*;
import org.schemaanalyst.mutation.equivalence.SchemaNormaliser;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

/**
 *
 * @author Chris J. Wright
 */
public class HyperSQLSchemaNormaliser extends SchemaNormaliser {

    @Override
    public Schema normalise(Schema schema) {
        Schema duplicate = schema.duplicate();
        normalisePrimaryKeys(duplicate);
        normaliseCheckNotNull(duplicate);
        return duplicate;
    }
    
    /**
     * Replace each Primary Key with a Unique and not null
     * @param schema The schema to normalise
     */
    private void normalisePrimaryKeys(Schema schema) {
        for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
            // Add UNIQUE if does not exist
            UniqueConstraint uc = new UniqueConstraint(pk.getTable(), pk.getColumns());
            if (!schema.getUniqueConstraints(pk.getTable()).contains(uc)) {
                schema.addUniqueConstraint(uc);
            }
            // Add NOT NULL if does not exist
            for (Column col : pk.getColumns()) {
                NotNullConstraint nn = new NotNullConstraint(pk.getTable(), col);
                if (!schema.getNotNullConstraints(pk.getTable()).contains(nn)) {
                    schema.addNotNullConstraint(nn);
                }
            }
            schema.removePrimaryKeyConstraint(pk.getTable());
        }
    }
    
    /**
     * Replace each Check is not null with a not null
     * @param schema The schema to normalise
     */
    private void normaliseCheckNotNull(Schema schema) {
        for (CheckConstraint check : schema.getCheckConstraints()) {
            Expression expr = check.getExpression();
            // Only apply if it is "NULL" expression
            if (expr instanceof NullExpression) {
                 NullExpression nullExp = (NullExpression) expr;
                 // Only apply if it is "NOT NULL" variant
                 if (nullExp.isNotNull()) {
                     Expression subexpression = nullExp.getSubexpression();
                     // Only apply if constraint refers to a column
                     if (subexpression instanceof ColumnExpression) {
                         ColumnExpression colExpr = (ColumnExpression) subexpression;
                         Column col = colExpr.getColumn();
                         // Remove the check constraint
                         schema.removeCheckConstraint(check);
                         // Create an equivalent not null, if one doesn't already exist
                         NotNullConstraint constraint = new NotNullConstraint(check.getTable(), col);
                         if (!schema.getNotNullConstraints(check.getTable()).contains(constraint)) {
                             schema.addNotNullConstraint(constraint);
                         }
                     }
                 }
                 
            }
        }
    }
    
}
