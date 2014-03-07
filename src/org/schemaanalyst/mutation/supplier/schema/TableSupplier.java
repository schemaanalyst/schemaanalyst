package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Supplies {@link org.schemaanalyst.sqlrepresentation.Table}s from a
 * {@link org.schemaanalyst.sqlrepresentation.Schema}.
 * 
 * 
 * @author Phil McMinn.
 * 
 */
public class TableSupplier extends IteratingSupplier<Schema, Table> {

	/**
	 * Constructor, which instantiates its own
	 * {@link org.schemaanalyst.sqlrepresentation.Schema.Duplicator}
	 */
	public TableSupplier() {
		super(new Schema.Duplicator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(Table component) {
		// Nothing to do here at the moment,
		// as we do not mutate the tables themselves
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Table> getComponents(Schema schema) {
		return schema.getTables();
	}
}
