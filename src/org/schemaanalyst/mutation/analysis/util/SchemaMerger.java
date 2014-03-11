package org.schemaanalyst.mutation.analysis.util;

import java.util.Collection;
import java.util.HashSet;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * <p>Merges pairs of schemas together, to produce single schemas containing 
 * all tables from both schema objects. This allows for the creation of 
 * 'schemata' schema objects.</p>
 * 
 * @author Chris J. Wright
 */
public class SchemaMerger {

    /**
     * Merges any tables that exist in <code>b</code> but not in <code>a</code>
     * into a new "schemata" schema, based on <code>a</code>. That is, if a
     * table exists in <code>a</code> and <code>b</code> (matched by name) the
     * copy in <code>a</code> will be kept.
     *
     * @param a First schema
     * @param b Second schema
     * @return The merged schema
     */
    public static Schema merge(Schema a, Schema b) {
        Schema duplicate = a.duplicate();
        Collection<String> aTableNames = new HashSet<>();
        for (Table table : a.getTablesInOrder()) {
            aTableNames.add(table.getIdentifier().get());
        }
        for (Table bTable : b.getTablesInOrder()) {
            if (!aTableNames.contains(bTable.getIdentifier().get())) {
                duplicate.addTable(bTable.duplicate()); 
                for (CheckConstraint checkConstraint : b.getCheckConstraints(bTable)) {
                    duplicate.addCheckConstraint(checkConstraint.duplicate());
                }
                for (NotNullConstraint notNullConstraint : b.getNotNullConstraints(bTable)) {
                    duplicate.addNotNullConstraint(notNullConstraint.duplicate());
                }
                PrimaryKeyConstraint pk = b.getPrimaryKeyConstraint(bTable);
                if (pk != null) {
                    duplicate.setPrimaryKeyConstraint(pk.duplicate());
                }
                for (UniqueConstraint uniqueConstraint : b.getUniqueConstraints(bTable)) {
                    duplicate.addUniqueConstraint(uniqueConstraint.duplicate());
                }
                for (ForeignKeyConstraint foreignKeyConstraint : b.getForeignKeyConstraints(bTable)) {
                    duplicate.addForeignKeyConstraint(foreignKeyConstraint.duplicate());
                }
            }
        }
        return duplicate;
    }

}
