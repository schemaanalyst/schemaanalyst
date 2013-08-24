package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ElementNullifier;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.schema.CheckConstraintSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class CCNullifier extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public CCNullifier(Schema schema) {
        this.schema = schema;
    }
    
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        
        //CheckExpressionSupplier supplier = new CheckExpressionSupplier();
        
        Supplier<Schema, Expression> supplier = 
                new ChainedSupplier<>(
                        new CheckConstraintSupplier(), 
                        new CheckExpressionSupplier());
        
        supplier.initialise(schema);
        
        ElementNullifier<Schema, Expression> nullifier = 
                new ElementNullifier<>(supplier);        
        mutants.addAll(nullifier.mutate());        
        
        return mutants;
    }
}
