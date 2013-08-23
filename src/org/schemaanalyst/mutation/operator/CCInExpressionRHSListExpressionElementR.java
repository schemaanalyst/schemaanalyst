package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.expression.InExpressionRHSListExpressionSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class CCInExpressionRHSListExpressionElementR extends MutationPipeline<Schema> {
    
    private Schema schema;
    
    public CCInExpressionRHSListExpressionElementR(Schema schema) {
        this.schema = schema;
    }
    
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        
		ChainedSupplier<Schema, Expression, List<Expression>> supplier =
				new ChainedSupplier<>(
						new CheckExpressionSupplier(),
						new InExpressionRHSListExpressionSupplier());
        
        supplier.initialise(schema);
        
        ListElementRemover<Schema, Expression> inExpressionListElementRemover = 
                new ListElementRemover<>(supplier);        
        mutants.addAll(inExpressionListElementRemover.mutate());        
        
        return mutants;
    }
}