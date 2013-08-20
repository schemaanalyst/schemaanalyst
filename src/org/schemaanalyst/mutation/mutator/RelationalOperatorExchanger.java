/*
 */
package org.schemaanalyst.mutation.mutator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 *
 * @author Chris J. Wright
 */
public class RelationalOperatorExchanger extends Mutator<Expression, RelationalOperator>{

    private RelationalOperator componentFromOriginalArtefact;
    private Iterator<RelationalOperator> iterator;
    private String description;

    public RelationalOperatorExchanger(Supplier<Expression, RelationalOperator> supplier) {
        super(supplier);
    }
    
    @Override
    protected void initialise(RelationalOperator componentFromOriginalArtefact) {
        this.componentFromOriginalArtefact = componentFromOriginalArtefact;
        List<RelationalOperator> alternatives = new ArrayList<>();
        for (RelationalOperator op : RelationalOperator.values()) {
            if (!op.equals(componentFromOriginalArtefact)) {
                alternatives.add(op);
            }
        }
        iterator = alternatives.iterator();
    }

    @Override
    protected boolean isMoreMutationToDo() {
        return iterator.hasNext();
    }

    @Override
    protected RelationalOperator performMutation(RelationalOperator componentToMutate) {
        RelationalOperator next = iterator.next();
        description = componentFromOriginalArtefact + " replaced with " + next;
        return next;
    }

    @Override
    protected String getDescriptionOfLastMutation() {
        return description;
    }
    
}
