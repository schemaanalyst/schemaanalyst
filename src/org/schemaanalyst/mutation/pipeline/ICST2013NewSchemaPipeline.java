/*
 */

package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceWithNotNullCheckChecker;
import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.mutation.redundancy.MutantEquivalentToMutantRemover;
import org.schemaanalyst.mutation.redundancy.MutantEquivalentToOriginalRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnsUniqueRemover;
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
        addRemover(new PrimaryKeyColumnsUniqueRemover());
        addRemover(new MutantEquivalentToOriginalRemover<>(new SchemaEquivalenceWithNotNullCheckChecker(), schema));
        addRemover(new MutantEquivalentToMutantRemover<>(new SchemaEquivalenceWithNotNullCheckChecker()));
    }
}
