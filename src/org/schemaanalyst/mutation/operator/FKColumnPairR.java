package org.schemaanalyst.mutation.operator;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyColumnsSupplier;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.Pair;

public class FKColumnPairR extends MutationPipeline<Schema> {

    private Schema schema;

    public FKColumnPairR(Schema schema) {
        this.schema = schema;
    }

    public List<Mutant<Schema>> mutate() {

        Supplier<Schema, List<Pair<Column>>> supplier = SupplyChain.chain(
                new ForeignKeyConstraintSupplier(),
                new ForeignKeyColumnsSupplier());
        
        supplier.initialise(schema);
        ListElementRemover<Schema, Pair<Column>> mutator = new ListElementRemover<>(
                supplier);
        return mutator.mutate();
    }

}
