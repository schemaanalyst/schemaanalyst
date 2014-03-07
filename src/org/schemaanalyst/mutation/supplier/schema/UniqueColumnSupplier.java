package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.List;

/**
 * Supplies columns from a
 * {@link org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint}.
 * 
 * @author Phil McMinn
 * 
 */
public class UniqueColumnSupplier extends
		SolitaryComponentSupplier<UniqueConstraint, List<Column>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(List<Column> columns) {
		currentDuplicate.setColumns(columns);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Column> getComponent(UniqueConstraint uniqueConstraint) {
		return uniqueConstraint.getColumns();
	}
}
