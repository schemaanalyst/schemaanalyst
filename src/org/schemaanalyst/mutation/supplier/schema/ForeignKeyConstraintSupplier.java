package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

public class ForeignKeyConstraintSupplier extends IteratingSupplier<Schema, ForeignKeyConstraint> {

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
    
    @Override
    public void putComponentBackInDuplicate(ForeignKeyConstraint foreignKeyConstraint) {
        if (foreignKeyConstraint.getNumColumns() > 0) {
            currentDuplicate.addForeignKeyConstraint(foreignKeyConstraint);
        }
    }

    @Override
    protected List<ForeignKeyConstraint> getComponents(Schema schema) {
        return schema.getForeignKeyConstraints();
    }    
}
