package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.InExpressionListSupplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class InExpressionListElementRemovalOperator extends MutationPipeline<Expression> {
    
    private Expression expression;
    
    public InExpressionListElementRemovalOperator(Expression expression) {
        this.expression = expression;
    }
    
    public List<Mutant<Expression>> mutate() {
        List<Mutant<Expression>> mutants = new ArrayList<>();
        
        InExpressionListSupplier supplier = new InExpressionListSupplier(expression);
        ListElementRemover<Expression, Expression> inExpressionListElementRemover = 
                new ListElementRemover<>(supplier);        
        mutants.addAll(inExpressionListElementRemover.mutate());        
        
        return mutants;
    }
}