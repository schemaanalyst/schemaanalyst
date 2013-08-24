package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class TableSupplier extends IteratingSupplier<Schema, Table> {

	@Override
	public void putComponentBackInDuplicate(Table component) {
		// Nothing to do here at the moment,
		// as we do not mutate the tables themselves		
	}

	@Override
	protected List<Table> getComponents(Schema schema) {
		return schema.getTables();
	}
}
