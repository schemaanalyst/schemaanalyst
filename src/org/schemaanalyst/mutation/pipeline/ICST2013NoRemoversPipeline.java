package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * <p>
 * A {@link MutationPipeline} that implements the pipeline used in the original 
 * ICST 2013 paper, without the removers for removing equivalent mutants.
 * </p>
 */
public class ICST2013NoRemoversPipeline extends MutationPipeline<Schema> {

	public ICST2013NoRemoversPipeline(Schema schema) {		
		addProducer(new CCNullifier(schema));
		addProducer(new FKCColumnPairR(schema));
		addProducer(new PKCColumnARE(schema));
		addProducer(new NNCAR(schema));
		addProducer(new UCColumnARE(schema));
	}	
}
