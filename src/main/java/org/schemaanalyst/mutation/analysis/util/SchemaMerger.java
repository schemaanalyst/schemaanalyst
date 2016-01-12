package org.schemaanalyst.mutation.analysis.util;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.Collection;
import java.util.HashSet;

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
    
    /**
     * Calls merge(a,b) for each pair of schemas in sequence.
     * 
     * @param schemas The schemas to merge
     * @return The merged schema
     */
    public static Schema merge(Schema... schemas) {
        Schema merge = null;
        for (Schema schema : schemas) {
            if (merge == null) {
                merge = schema;
            } else {
                merge = merge(merge, schema);
            }
        }
        return merge;
    }

}
