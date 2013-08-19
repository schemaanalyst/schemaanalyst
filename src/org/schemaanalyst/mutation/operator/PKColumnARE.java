package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.PrimaryKeyColumnsSupplier;
import org.schemaanalyst.mutation.supplier.PrimaryKeyColumnsWithAlternativesSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;

public class PKColumnARE extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public PKColumnARE(Schema schema) {
        this.schema = schema;
    }
    
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        
        PrimaryKeyColumnsSupplier columnsSupplier = new PrimaryKeyColumnsSupplier();
        columnsSupplier.initialise(schema);
        ListElementRemover<Schema, Column> columnRemover = new ListElementRemover<>(columnsSupplier);
        mutants.addAll(columnRemover.mutate());
        
        PrimaryKeyColumnsWithAlternativesSupplier columnsWithAlternativesSupplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        columnsWithAlternativesSupplier.initialise(schema);
        ListElementAdder<Schema, Column> columnAdder = new ListElementAdder<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnAdder.mutate());
        
        columnsWithAlternativesSupplier.initialise(schema); // restart the process
        ListElementAdder<Schema, Column> columnExchanger = new ListElementAdder<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnExchanger.mutate());
        
        return mutants;
    }
}
