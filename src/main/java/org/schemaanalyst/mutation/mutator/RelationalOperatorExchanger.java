/*
 */
package org.schemaanalyst.mutation.mutator;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.Supplier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Given a {@link RelationalOperator}, this mutator returns mutants of each 
 * alternative possible {@link RelationalOperator}.
 * </p>
 * 
 * @author Chris J. Wright
 * @param <A> The artefact type
 */
public class RelationalOperatorExchanger<A> extends Mutator<A, RelationalOperator>{

    private RelationalOperator componentFromOriginalArtefact;
    private Iterator<RelationalOperator> iterator;
    private String description;

    public RelationalOperatorExchanger(Supplier<A, RelationalOperator> supplier) {
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
