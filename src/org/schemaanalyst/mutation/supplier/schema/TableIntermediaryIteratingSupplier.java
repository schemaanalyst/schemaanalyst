package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IntermediaryIteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public abstract class TableIntermediaryIteratingSupplier<C> extends IntermediaryIteratingSupplier<Schema, Table, C> {
	
    public TableIntermediaryIteratingSupplier() {
        super(new Schema.Duplicator());
    }
    
    protected List<Table> getIntermediaries(Schema schema) {
        return schema.getTables();
    }    
}
