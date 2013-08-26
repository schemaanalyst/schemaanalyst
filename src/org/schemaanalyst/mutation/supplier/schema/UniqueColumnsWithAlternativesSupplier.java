package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.util.Pair;

public class UniqueColumnsWithAlternativesSupplier extends
        SolitaryComponentSupplier<UniqueConstraint, Pair<List<Column>>> {

    @Override
    public void putComponentBackInDuplicate(Pair<List<Column>> columnListPair) {
        List<Column> columns = columnListPair.getFirst();
        currentDuplicate.setColumns(columns);
    }

    @Override
    protected Pair<List<Column>> getComponent(
            UniqueConstraint uniqueConstraint) {
        List<Column> columns = uniqueConstraint.getColumns();
        List<Column> alternatives = new ArrayList<Column>();
        
        for (Column column : uniqueConstraint.getTable().getColumns()) {
            if (!columns.contains(column)) {
                alternatives.add(column);
            }
        }        
        
        return new Pair<>(columns, alternatives);
    }

}
