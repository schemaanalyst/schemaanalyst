package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public abstract class TableIteratingSupplier<C> extends IntermediaryIteratingSupplier<Schema, Table, C> {
	
    protected List<Table> getIntermediaries(Schema schema) {
        return schema.getTables();
    }    
}
