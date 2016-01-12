package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

import java.util.List;

/**
 * Supplies {@link org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint}s
 * from a {@link org.schemaanalyst.sqlrepresentation.Schema}

 * @author Phil McMinn
 *
 */
public class ForeignKeyConstraintSupplier extends IteratingSupplier<Schema, ForeignKeyConstraint> {

	/**
	 * Constructor, which instantiates its own
	 * {@link org.schemaanalyst.sqlrepresentation.Schema.Duplicator}
	 */
    public ForeignKeyConstraintSupplier() {
        super(new Schema.Duplicator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ForeignKeyConstraint getDuplicateComponent() {
        ForeignKeyConstraint duplicateComponent = super.getDuplicateComponent();
        // always remove the foreign key (we'll put one back later if 
        // the mutated version has columns)
        currentDuplicate.removeForeignKeyConstraint(duplicateComponent);
        return duplicateComponent;
    } 
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void putComponentBackInDuplicate(ForeignKeyConstraint foreignKeyConstraint) {
        if (foreignKeyConstraint.getNumColumns() > 0) {
            currentDuplicate.addForeignKeyConstraint(foreignKeyConstraint);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<ForeignKeyConstraint> getComponents(Schema schema) {
        return schema.getForeignKeyConstraints();
    }    
}
