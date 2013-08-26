package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

public class UniqueConstraintSupplier extends IteratingSupplier<Schema, UniqueConstraint>{

    public UniqueConstraintSupplier() {
        super(new Schema.Duplicator());
    }
    
    @Override
    public void putComponentBackInDuplicate(UniqueConstraint uniqueConstraint) {
        if (uniqueConstraint.getNumColumns() == 0) {
            currentDuplicate.removeUniqueConstraint(uniqueConstraint);
        } else {
            currentDuplicate.addUniqueConstraint(uniqueConstraint);
        }
    }

    @Override
    protected List<UniqueConstraint> getComponents(Schema schema) {        
        return schema.getUniqueConstraints();
    }
}
