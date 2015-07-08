package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.List;

/**
 * <p>
 * A {@link RedundantMutantRemover} that removes any {@link UniqueConstraint}
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
 * @author Phil McMinn
 *
 */
public class PrimaryKeyColumnsUniqueRemover extends MutantRemover<Schema> {

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {

        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<PrimaryKeyConstraint> primaryKeyConstraints = schema.getPrimaryKeyConstraints();
            int startCount = schema.getUniqueConstraints().size();
            for (PrimaryKeyConstraint primaryKey : primaryKeyConstraints) {
                schema.removeUniqueConstraint(
                        new UniqueConstraint(primaryKey.getTable(), primaryKey.getColumns()));
            }
//            if (startCount != schema.getUniqueConstraints().size()) {
                mutant.addRemoverApplied(this);
//            }
        }

        return mutants;
    }

}
