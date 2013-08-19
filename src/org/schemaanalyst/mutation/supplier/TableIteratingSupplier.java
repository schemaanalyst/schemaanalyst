package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public abstract class TableIteratingSupplier<C> extends IteratingSupplier<Schema, Table, C> {

    public TableIteratingSupplier(Schema schema) {
        super(schema);
    }

    protected List<Table> getIntermediaries(Schema schema) {
        return schema.getTables();
    }
    
    protected abstract C getComponentFromIntermediary(Schema schema, Table table);
    
    public abstract void putComponentBackInDuplicate(C component);    
}
