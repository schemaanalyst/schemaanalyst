package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.List;

/**
 * <p>
 * A {@link RedundantMutantRemover} that removes any {@link NotNullConstraint}
 * that has been added to a column already part of a
 * {@link PrimaryKeyConstraint}, where the {@code NOT NULL} constraint is
 * implied in most DBMSs.
 * </p>
 *
 * <p>
 * The implied {@code NOT NULL} constraint on {@code PRIMARY KEY} fields means
 * that mutants with such a {@link NotNullConstraint} will be impossible to
 * kill, and therefore can be discarded.
 * </p>
 *
 * @author Phil McMinn
 *
 */
public class PrimaryKeyColumnNotNullRemover extends MutantRemover<Schema> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {

        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<PrimaryKeyConstraint> primaryKeyConstraints = schema.getPrimaryKeyConstraints();
            for (PrimaryKeyConstraint primaryKey : primaryKeyConstraints) {
                Table table = primaryKey.getTable();
                for (Column column : primaryKey.getColumns()) {
                    schema.removeNotNullConstraint(new NotNullConstraint(table, column));
                }
            }
            mutant.addRemoverApplied(this);
        }
        return mutants;
    }
}
