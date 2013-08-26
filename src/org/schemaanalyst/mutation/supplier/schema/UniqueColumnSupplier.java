package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

public class UniqueColumnSupplier extends SolitaryComponentSupplier<UniqueConstraint, List<Column>> {
    
    @Override
    public void putComponentBackInDuplicate(List<Column> columns) {
        currentDuplicate.setColumns(columns);        
    }

    @Override
    protected List<Column> getComponent(UniqueConstraint uniqueConstraint) {
        return uniqueConstraint.getColumns();
    }
}
