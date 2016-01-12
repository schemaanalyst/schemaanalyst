package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Supplies {@link org.schemaanalyst.sqlrepresentation.Column}s
 * from a {@link org.schemaanalyst.sqlrepresentation.Table}.
 * @author Phil McMinn
 *
 */
public class ColumnSupplier extends IteratingSupplier<Table, Column> {

    /**
     * {@inheritDoc}
     */
	@Override
	public void putComponentBackInDuplicate(Column component) {
		// Nothing to do here, as we do not mutate columns		
	}

    /**
     * {@inheritDoc}
     */
	@Override
	protected List<Column> getComponents(Table table) {
		return table.getColumns();
	}
}
