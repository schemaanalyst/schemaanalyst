package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.mutator.ListElementExchanger;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyColumnsSupplier;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyColumnsWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.Pair;

public class PKColumnARE extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public PKColumnARE(Schema schema) {
        this.schema = schema;
    }
    
    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        
        Supplier<Schema, List<Column>> columnsSupplier 
            = new ChainedSupplier<>(
                    new PrimaryKeyConstraintSupplier(),
                    new PrimaryKeyColumnsSupplier());
        columnsSupplier.initialise(schema);
        ListElementRemover<Schema, Column> columnRemover = new ListElementRemover<>(columnsSupplier);
        mutants.addAll(columnRemover.mutate());

        Supplier<Schema, Pair<List<Column>>> columnsWithAlternativesSupplier 
        = new ChainedSupplier<>(
                new PrimaryKeyConstraintSupplier(),
                new PrimaryKeyColumnsWithAlternativesSupplier());
        columnsWithAlternativesSupplier.initialise(schema);
        ListElementAdder<Schema, Column> columnAdder = new ListElementAdder<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnAdder.mutate());
        
        columnsWithAlternativesSupplier.initialise(schema); // restart the process
        ListElementExchanger<Schema, Column> columnExchanger = new ListElementExchanger<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnExchanger.mutate());
        
        return mutants;
    }
}
