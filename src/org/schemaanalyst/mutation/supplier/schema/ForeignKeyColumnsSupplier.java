package org.schemaanalyst.mutation.supplier.schema;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.supplier.IntermediaryIteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.Pair;

public class ForeignKeyColumnsSupplier extends IntermediaryIteratingSupplier<Schema, ForeignKeyConstraint, List<Pair<Column>>> {

    @Override
    protected List<ForeignKeyConstraint> getIntermediaries(Schema schema) {
        return schema.getForeignKeyConstraints();
    }
    
    @Override
    protected List<Pair<Column>> getComponentFromIntermediary(Schema schema, ForeignKeyConstraint foreignKeyConstraint) {
        List<Column> columns = foreignKeyConstraint.getColumns();
        List<Column> referenceColumns = foreignKeyConstraint.getReferenceColumns();

        List<Pair<Column>> pairedColumns = new ArrayList<>();
        for (int i=0; i < columns.size(); i++) {
            pairedColumns.add(new Pair<Column>(columns.get(i), referenceColumns.get(i)));
        }
        
        return pairedColumns;
    }
    
    @Override
    public void putComponentBackInIntermediary(ForeignKeyConstraint foreignKeyConstraint, List<Pair<Column>> columnPairs) {
        List<Column> columns = new ArrayList<>();
        List<Column> referenceColumns = new ArrayList<>();        
        
        for (Pair<Column> columnPair : columnPairs) {
            columns.add(columnPair.getFirst());
            referenceColumns.add(columnPair.getSecond());
        }
        
        foreignKeyConstraint.setColumns(columns);
        foreignKeyConstraint.setReferenceColumns(referenceColumns);
    }
}
