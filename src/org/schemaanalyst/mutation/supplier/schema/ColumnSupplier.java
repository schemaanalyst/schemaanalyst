package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

public class ColumnSupplier extends IteratingSupplier<Table, Column> {

	@Override
	public void putComponentBackInDuplicate(Column component) {
		// Nothing to do here, as we do not mutate columns		
	}

	@Override
	protected List<Column> getComponents(Table table) {
		return table.getColumns();
	}
}
