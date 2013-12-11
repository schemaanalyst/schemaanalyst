/*
 */
package org.schemaanalyst.mutation.quasimutant;

import java.util.Iterator;
import java.util.List;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.util.DataCapturer;

/**
 * Removes those mutant schemas that will be rejected by the SQLite DBMS, but
 * may be accepted by other DBMSs.
 *
 * @author Chris J. Wright
 */
public class SQLiteRemover extends MutantRemover<Schema> {

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            for (ForeignKeyConstraint fkey : schema.getForeignKeyConstraints()) {
                boolean fUnique = isUnique(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                boolean fPrimary = isPrimary(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                if (!fUnique && !fPrimary) {
                    DataCapturer.capture("removedmutants", "quasimutant", schema + "-" + mutant.getSimpleDescription());
                    it.remove();
                    break;
                }
            }
        }
        return mutants;
    }

    private boolean isUnique(Schema schema, Table table, List<Column> columns) {
        return schema.isUnique(table, columns.toArray(new Column[columns.size()]));
    }

    private boolean isPrimary(Schema schema, Table table, List<Column> columns) {
        PrimaryKeyConstraint pkey = schema.getPrimaryKeyConstraint(table);
        if (pkey != null) {
            List<Column> pkeyColumns = pkey.getColumns();
            if (columns.size() == pkeyColumns.size()) {
                return pkeyColumns.containsAll(columns);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
