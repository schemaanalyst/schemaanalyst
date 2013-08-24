package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

public class PrimaryKeyConstraintSupplier extends IteratingSupplier<Schema, PrimaryKeyConstraint>{

    public PrimaryKeyConstraintSupplier() {
        super(new Schema.Duplicator());
    }
    
    @Override
    public void putComponentBackInDuplicate(PrimaryKeyConstraint primaryKeyConstraint) {
        if (primaryKeyConstraint.getNumColumns() == 0) {
            currentDuplicate.removePrimaryKeyConstraint(primaryKeyConstraint.getTable());
        } else {
            currentDuplicate.setPrimaryKeyConstraint(primaryKeyConstraint);
        }
    }

    @Override
    protected List<PrimaryKeyConstraint> getComponents(Schema schema) {
        List<PrimaryKeyConstraint> primaryKeyConstraints = new ArrayList<>();
        for (Table table : schema.getTables()) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            if (primaryKeyConstraint == null) {
                primaryKeyConstraint = new PrimaryKeyConstraint(table);
            }
            primaryKeyConstraints.add(primaryKeyConstraint);
        }
        return primaryKeyConstraints;
    }
}
