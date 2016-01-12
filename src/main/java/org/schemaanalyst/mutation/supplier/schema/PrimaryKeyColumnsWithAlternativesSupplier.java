package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Supplies columns from from a
 * {@link org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint}
 * along with alternative columns from the same table (not used in the
 * <tt>PRIMARY KEY</tt>) on which the constraint is defined.
 * 
 * @author Phil McMinn
 * 
 */
public class PrimaryKeyColumnsWithAlternativesSupplier extends
		SolitaryComponentSupplier<PrimaryKeyConstraint, Pair<List<Column>>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(Pair<List<Column>> columnListPair) {
		List<Column> columns = columnListPair.getFirst();
		currentDuplicate.setColumns(columns);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Pair<List<Column>> getComponent(
			PrimaryKeyConstraint primaryKeyConstraint) {
		List<Column> columns = primaryKeyConstraint.getColumns();
		List<Column> alternatives = new ArrayList<>();

		for (Column column : primaryKeyConstraint.getTable().getColumns()) {
			if (!columns.contains(column)) {
				alternatives.add(column);
			}
		}

		return new Pair<>(columns, alternatives);
	}

}
