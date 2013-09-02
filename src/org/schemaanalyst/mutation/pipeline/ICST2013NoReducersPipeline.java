package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * {@link ICST2013NoReducersPipeline} is an implementation of the mutation
 * pipeline used in the original ICST 2013 paper, without the reducers for 
 * removing equivalent mutants.
 * 
 *
 */
public class ICST2013NoReducersPipeline extends MutationPipeline<Schema> {

	public ICST2013NoReducersPipeline(Schema schema) {		
		addProducer(new CCNullifier(schema));
		addProducer(new FKCColumnPairR(schema));
		addProducer(new PKCColumnARE(schema));
		addProducer(new NNCAR(schema));
		addProducer(new UCColumnARE(schema));
	}	
}
