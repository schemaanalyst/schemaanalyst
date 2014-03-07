package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.List;

/**
 * Supplies
 * {@link org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint}s from
 * a {@link org.schemaanalyst.sqlrepresentation.Schema}
 * 
 * @author Phil McMinn
 * 
 */
public class UniqueConstraintSupplier extends
		IteratingSupplier<Schema, UniqueConstraint> {

	/**
	 * Constructor, which instantiates its own
	 * {@link org.schemaanalyst.sqlrepresentation.Schema.Duplicator}
	 */
	public UniqueConstraintSupplier() {
		super(new Schema.Duplicator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(UniqueConstraint uniqueConstraint) {
		if (uniqueConstraint.getNumColumns() != 0) {
            currentDuplicate.addUniqueConstraint(uniqueConstraint);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<UniqueConstraint> getComponents(Schema schema) {
        return schema.getUniqueConstraints();
	}

    // ONLY CALL ONCE
    @Override
    public UniqueConstraint getDuplicateComponent() {
        UniqueConstraint cons = super.getDuplicateComponent();
        currentDuplicate.removeUniqueConstraint(cons);
        return super.getDuplicateComponent();
    }
}
