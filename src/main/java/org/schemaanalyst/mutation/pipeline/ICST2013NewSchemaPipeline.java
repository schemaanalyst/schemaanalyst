/*
 */

package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceWithNotNullCheckChecker;
import org.schemaanalyst.mutation.operator.*;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyUniqueOverlapConstraintRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chris J. Wright
 */
public class ICST2013NewSchemaPipeline extends MutationPipeline<Schema> {

    public ICST2013NewSchemaPipeline(Schema schema) {
        addProducer(new CCNullifier(schema));
        addProducer(new FKCColumnPairR(schema));
        addProducer(new PKCColumnARE(schema));
        addProducer(new NNCAR(schema));
        addProducer(new UCColumnARE(schema));

        addRemover(new PrimaryKeyColumnNotNullRemover());
        addRemover(new PrimaryKeyUniqueOverlapConstraintRemover());
        addRemover(new EquivalentMutantRemover<>(new SchemaEquivalenceWithNotNullCheckChecker(), schema));
        addRemover(new RedundantMutantRemover<>(new SchemaEquivalenceWithNotNullCheckChecker()));
    }
}
