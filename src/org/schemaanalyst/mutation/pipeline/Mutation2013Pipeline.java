/*
 */
package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.mutation.redundancy.MutantEquivalentToMutantRemover;
import org.schemaanalyst.mutation.redundancy.MutantEquivalentToOriginalRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnsUniqueRemover;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * 
 * 
 * @author Chris J. Wright
 */
public class Mutation2013Pipeline extends MutationPipeline<Schema> {
    
    public Mutation2013Pipeline(Schema schema) {
        addProducer(new CCNullifier(schema));
		addProducer(new PKCColumnARE(schema));
		addProducer(new NNCAR(schema));
		addProducer(new UCColumnARE(schema));
		
		addRemover(new PrimaryKeyColumnNotNullRemover());
		addRemover(new PrimaryKeyColumnsUniqueRemover());
		addRemover(new PostgresRemover());
        addRemover(new MutantEquivalentToOriginalRemover<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new MutantEquivalentToMutantRemover<>(new SchemaEquivalenceChecker()));
    }
    
}
