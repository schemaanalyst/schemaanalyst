package org.schemaanalyst.mutation.redundancy;

import java.util.Iterator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.PKCColumnA;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.PKCColumnE;
import org.schemaanalyst.mutation.operator.UCColumnA;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnE;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * <p>
 * A {@link MutantRemover} that removes any {@link UniqueConstraint} that has
 * been added to a column already part of a {@link PrimaryKeyConstraint}, where
 * the {@code UNIQUE} constraint is implied in most DBMSs.
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
public class PrimaryKeyUniqueOverlapConstraintRemover extends PrimaryKeyUniqueOverlapDetector {

    /**
     * Whether to treat Unique and Primary Key constraints as equivalent
     */
    private final boolean uniqueAndPrimaryKeyEquivalent;

    public PrimaryKeyUniqueOverlapConstraintRemover() {
        uniqueAndPrimaryKeyEquivalent = false;
    }

    public PrimaryKeyUniqueOverlapConstraintRemover(boolean uniqueAndPrimaryKeyEquivalent) {
        this.uniqueAndPrimaryKeyEquivalent = uniqueAndPrimaryKeyEquivalent;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it, PrimaryKeyConstraint primaryKey) {
        Schema schema = mutant.getMutatedArtefact();
        Class mutantProducer = mutant.getMutantProducer().getClass();
        if (!uniqueAndPrimaryKeyEquivalent) {
            // If Unique != Primary Key, then remove the unique
            // The loop here is required in case there is more than one overlapping UC
            for (UniqueConstraint uc : schema.getUniqueConstraints(primaryKey.getTable())) {
                if (uc.getColumns().equals(primaryKey.getColumns())) {
                    schema.removeUniqueConstraint(uc);
                }
            }
        } else {
            // If Unique == Primary Key, then remove whichever was added by a mutation
            if (mutantProducer.equals(PKCColumnA.class) || mutantProducer.equals(PKCColumnE.class) || mutantProducer.equals(PKCColumnARE.class)) {
                // Remove the offending PK
                mutant.getMutatedArtefact().removePrimaryKeyConstraint(primaryKey.getTable());
            } else if (mutantProducer.equals(UCColumnA.class) || mutantProducer.equals(UCColumnE.class) || mutantProducer.equals(UCColumnARE.class)) {
                // Remove the offending UC
                // The loop here is required in case there is more than one overlapping UC
                for (UniqueConstraint uc : schema.getUniqueConstraints(primaryKey.getTable())) {
                    if (uc.getColumns().equals(primaryKey.getColumns())) {
                        schema.removeUniqueConstraint(uc);
                    }
                }
            } else {
                throw new RuntimeException("Could not determine mutation responsible for Primary key / Unique overlap (mutantProducer = " + mutantProducer + ")");
            }
        }
    }

}
