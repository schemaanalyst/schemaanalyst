package org.schemaanalyst.mutation.supplier;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.Pair;

public class ForeignKeyColumnsSupplier extends IteratingSupplier<Schema, ForeignKeyConstraint, List<Pair<Column>>> {

    public ForeignKeyColumnsSupplier(Schema schema) {
        super(schema);        
    }

    @Override
    protected List<ForeignKeyConstraint> getIntermediaries(Schema schema) {
        return schema.getAllForeignKeyConstraints();
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
    public void putComponentBackInDuplicate(List<Pair<Column>> columnPairs) {
        List<Column> columns = new ArrayList<>();
        List<Column> referenceColumns = new ArrayList<>();        
        
        for (Pair<Column> columnPair : columnPairs) {
            columns.add(columnPair.getFirst());
            referenceColumns.add(columnPair.getSecond());
        }
        
        ForeignKeyConstraint foreignKeyConstraint = getDuplicateIntermediary();        
        foreignKeyConstraint.setColumns(columns);
        foreignKeyConstraint.setReferenceColumns(referenceColumns);
    }
}
