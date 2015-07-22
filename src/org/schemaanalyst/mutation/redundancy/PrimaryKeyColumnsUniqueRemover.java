package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;


/**
 * <p>
 * A {@link MutantRemover} that removes any {@link UniqueConstraint}
 * that has been added to a column already part of a
 * {@link PrimaryKeyConstraint}, where the {@code UNIQUE} constraint is implied
 * in most DBMSs.
 * </p>
 *
 * <p>
 * The implied {@code UNIQUE} constraint on {@code PRIMARY KEY} fields means
 * that mutants with such a {@link UniqueConstraint} will be impossible to kill,
 * and therefore can be discarded.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class PrimaryKeyColumnsUniqueRemover extends PrimaryKeyUniqueOverlapDetector {

    /**
     * {@inheritDoc }
     */
    @Override
    protected void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it, PrimaryKeyConstraint primaryKey) {
        Schema schema = mutant.getMutatedArtefact();
        // The loop here is required in case there is more than one overlapping UC
        for (UniqueConstraint uc : schema.getUniqueConstraints(primaryKey.getTable())) {
            if (uc.getColumns().equals(primaryKey.getColumns())) {
                schema.removeUniqueConstraint(uc);
            }
        }
    }

}
