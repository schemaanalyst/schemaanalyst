package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * <p>
 * A detector that identifies when a Primary Key and Unique constraint are 
 * defined on the same set of columns.
 * </p>
 * 
 * @author Chris J. Wright
 */
public abstract class PrimaryKeyUniqueOverlapDetector extends MutantRemover<Schema> {
    
    private static final Logger LOGGER = Logger.getLogger(PrimaryKeyUniqueOverlapDetector.class.getName());

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            List<PrimaryKeyConstraint> primaryKeyConstraints = schema.getPrimaryKeyConstraints();
            for (PrimaryKeyConstraint primaryKey : primaryKeyConstraints) {
                if (hasOverlappingUnique(schema, primaryKey)) {
                    LOGGER.log(Level.INFO, "Primary key and unique columns overlap:\n{0}\n", new Object[]{mutant.getDescription()});
                    process(mutant, it, primaryKey);
                }
            }
        }
        return mutants;
    }

    private boolean hasOverlappingUnique(Schema schema, PrimaryKeyConstraint primaryKey) {
        for (UniqueConstraint uc : schema.getUniqueConstraints(primaryKey.getTable())) {
            if (uc.getColumns().equals(primaryKey.getColumns())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Process the mutant which has an identified PK/UC column overlap
     * 
     * @param mutant The mutant
     * @param it The iterator over the list of mutants
     * @param primaryKey The primary key that has an overlap
     */
    protected abstract void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it, PrimaryKeyConstraint primaryKey);
    
}
