/*
 */
package org.schemaanalyst.mutation.mutators.expression;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionAdapter;

/**
 *
 * @author Chris J. Wright
 */
public abstract class AbstractMutator extends ExpressionAdapter {
    protected List<Expression> mutants = new ArrayList<>();
    
    public List<Expression> getMutants() {
        return mutants;
    }
    
    public void resetMutants() {
        mutants = new ArrayList<>();
    }
}
