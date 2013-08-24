package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;

public class CheckConstraintSupplier extends IteratingSupplier<Schema, CheckConstraint> {

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
