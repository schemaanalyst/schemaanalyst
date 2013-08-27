package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

/**
 * Supplies columns from a
 * {@link org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint}.
 * 
 * @author Phil McMinn
 * 
 */
public class PrimaryKeyColumnSupplier extends
		SolitaryComponentSupplier<PrimaryKeyConstraint, List<Column>> {

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
	protected List<Column> getComponent(
			PrimaryKeyConstraint primaryKeyConstraint) {
		return primaryKeyConstraint.getColumns();
	}
}
