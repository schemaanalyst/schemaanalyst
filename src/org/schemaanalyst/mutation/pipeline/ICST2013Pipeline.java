package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.sqlrepresentation.Schema;

public class ICST2013Pipeline extends MutationPipeline<Schema> {

	public ICST2013Pipeline(Schema schema) {		
		add(new CCNullifier(schema));
		add(new FKCColumnPairR(schema));
		add(new PKCColumnARE(schema));
		add(new NNCAR(schema));
		add(new UCColumnARE(schema));
	}	
}
