package org.schemaanalyst.mutation.quasimutant;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;

/**
 *
 * @author Chris J. Wright
 */
public abstract class StaticDBMSDetector extends MutantRemover<Schema> {

    private static final Logger LOGGER = Logger.getLogger(StaticDBMSDetector.class.getName());
    
    protected Map<Class<?>, Set<Class<?>>> compatibleTypes;

    public StaticDBMSDetector() {
        initializeCompatibleTypes();
    }

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            for (ForeignKeyConstraint fkey : schema.getForeignKeyConstraints()) {
                boolean fUnique = isUnique(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                boolean fPrimary = isPrimary(schema, fkey.getReferenceTable(), fkey.getReferenceColumns());
                boolean compatibleTypes = areCompatible(fkey.getColumns(), fkey.getReferenceColumns());

                if ((!fUnique && !fPrimary) || !compatibleTypes) {
                    LOGGER.log(Level.INFO, "Quasi mutant:\n{0}\n", new Object[]{mutant.getDescription()});
                    process(mutant, it);
                    break;
                }
            }
        }
        return mutants;
    }
    
    public abstract void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it);

    protected boolean isUnique(Schema schema, Table table, List<Column> columns) {
        return schema.isUnique(table, columns.toArray(new Column[columns.size()]));
    }

    protected boolean isPrimary(Schema schema, Table table, List<Column> columns) {
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

    protected boolean areCompatible(List<Column> columns, List<Column> referenceColumns) {

        Iterator<Column> columnsIterator = columns.iterator();
        Iterator<Column> referenceColumnsIterator = referenceColumns.iterator();

        while (columnsIterator.hasNext()) {
            Column column = columnsIterator.next();
            Column referenceColumn = referenceColumnsIterator.next();

            if (!compatibleTypes(column.getDataType(), referenceColumn.getDataType())) {
                return false;
            }
        }

        return true;
    }

    protected boolean compatibleTypes(DataType dataType1, DataType dataType2) {
        return compatibleTypes.get(dataType1.getClass()).contains(dataType2.getClass());
    }

    protected abstract void initializeCompatibleTypes();
}

