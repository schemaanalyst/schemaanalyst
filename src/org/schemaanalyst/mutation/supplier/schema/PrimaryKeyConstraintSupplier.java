package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Supplies
 * {@link org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint}s
 * from a {@link org.schemaanalyst.sqlrepresentation.Schema}
 * 
 * @author Phil McMinn
 * 
 */
public class PrimaryKeyConstraintSupplier extends
		IteratingSupplier<Schema, PrimaryKeyConstraint> {

	/**
	 * Constructor, which instantiates its own
	 * {@link org.schemaanalyst.sqlrepresentation.Schema.Duplicator}
	 */
	public PrimaryKeyConstraintSupplier() {
		super(new Schema.Duplicator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(
			PrimaryKeyConstraint primaryKeyConstraint) {
		if (primaryKeyConstraint.getNumColumns() == 0) {
			currentDuplicate.removePrimaryKeyConstraint(primaryKeyConstraint
					.getTable());
		} else {
			currentDuplicate.setPrimaryKeyConstraint(primaryKeyConstraint);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<PrimaryKeyConstraint> getComponents(Schema schema) {
		List<PrimaryKeyConstraint> primaryKeyConstraints = new ArrayList<>();
		for (Table table : schema.getTables()) {
			PrimaryKeyConstraint primaryKeyConstraint = schema
					.getPrimaryKeyConstraint(table);
			if (primaryKeyConstraint == null) {
				primaryKeyConstraint = new PrimaryKeyConstraint(table);
			}
			primaryKeyConstraints.add(primaryKeyConstraint);
		}
		return primaryKeyConstraints;
	}
}
