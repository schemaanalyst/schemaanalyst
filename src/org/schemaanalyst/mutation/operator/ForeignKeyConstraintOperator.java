package org.schemaanalyst.mutation.operator;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.artefactsupplier.ForeignKeyColumnsSupplier;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.Pair;

public class ForeignKeyConstraintOperator extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public ForeignKeyConstraintOperator(Schema schema) {
        this.schema = schema;
    }
    
    public List<Mutant<Schema>> mutate() {
        ForeignKeyColumnsSupplier supplier = new ForeignKeyColumnsSupplier(schema);
        ListElementRemover<Schema, Pair<Column>> mutator = new ListElementRemover<>(supplier);
        return mutator.mutate();
    }

}
