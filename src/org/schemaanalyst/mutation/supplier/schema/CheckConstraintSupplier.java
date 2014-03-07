package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;

import java.util.List;

/**
 * Supplies {@link org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint}s
 * from a {@link org.schemaanalyst.sqlrepresentation.Schema}
 * 
 * @author Phil McMinn
 *
 */
public class CheckConstraintSupplier extends IteratingSupplier<Schema, CheckConstraint> {

	/**
	 * Constructor, which instantiates its own
	 * {@link org.schemaanalyst.sqlrepresentation.Schema.Duplicator}
	 */
    public CheckConstraintSupplier() {
        super(new Schema.Duplicator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CheckConstraint getDuplicateComponent() {
        CheckConstraint duplicateComponent = super.getDuplicateComponent();
        // always remove the foreign key (we'll put one back later if 
        // the mutated version has columns)
        currentDuplicate.removeCheckConstraint(duplicateComponent);
        return duplicateComponent;
    }     
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void putComponentBackInDuplicate(CheckConstraint checkConstraint) {
        if (checkConstraint.getExpression() != null) {
            currentDuplicate.addCheckConstraint(getDuplicateComponent());
        }        
    }

    @Override
    protected List<CheckConstraint> getComponents(Schema schema) {
        return schema.getCheckConstraints();
    }
}
