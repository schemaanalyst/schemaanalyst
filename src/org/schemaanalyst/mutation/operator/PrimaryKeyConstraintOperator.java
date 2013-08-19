package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.artefactsupplier.PrimaryKeyColumnsSupplier;
import org.schemaanalyst.mutation.artefactsupplier.PrimaryKeyColumnsWithAlternativesSupplier;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;

public class PrimaryKeyConstraintOperator extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public PrimaryKeyConstraintOperator(Schema schema) {
        this.schema = schema;
    }
    
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        
        PrimaryKeyColumnsSupplier columnsSupplier = new PrimaryKeyColumnsSupplier(schema);
        ListElementRemover<Schema, Column> columnRemover = new ListElementRemover<>(columnsSupplier);
        mutants.addAll(columnRemover.mutate());
        
        PrimaryKeyColumnsWithAlternativesSupplier columnsWithAlternativesSupplier = new PrimaryKeyColumnsWithAlternativesSupplier(schema);
        ListElementAdder<Schema, Column> columnAdder = new ListElementAdder<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnAdder.mutate());
        
        columnsWithAlternativesSupplier = new PrimaryKeyColumnsWithAlternativesSupplier(schema);
        ListElementAdder<Schema, Column> columnExchanger = new ListElementAdder<>(columnsWithAlternativesSupplier);
        mutants.addAll(columnExchanger.mutate());
        
        return mutants;
    }
}
