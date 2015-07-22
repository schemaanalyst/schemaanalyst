package org.schemaanalyst.mutation.quasimutant;

import org.schemaanalyst.mutation.redundancy.*;
import java.util.Iterator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.util.DataCapturer;


/**
 * <p>
 * A {@link MutantRemover} that removes any mutant where a {@link UniqueConstraint}
 * that has been added to a column already is part of a
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
public class PrimaryKeyUniqueOverlapMutantRemover extends PrimaryKeyUniqueOverlapDetector {

    /**
     * {@inheritDoc }
     */
    @Override
    protected void process(Mutant<Schema> mutant, Iterator<Mutant<Schema>> it, PrimaryKeyConstraint primaryKey) {
        DataCapturer.capture("removedmutants", "quasimutant", mutant.getMutatedArtefact() + "-" + mutant.getSimpleDescription());
        it.remove();
    }

}
