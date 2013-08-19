package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.util.Duplicable;

/**
 * <p>An {@link ExpressionPath} represents the path to a subexpression of 
 * an overall {@link Expression}.  It can be used to traverse back to a subexpression
 * in an {@link ExpressionTree} of {@link Expression} instances.</p>
 * 
 * <p>It is implemented by storing a list of integers.  Each integer corresponding 
 * to the subexpression number that should be traversed at each node of an 
 * {@link Expression} in an {@link ExpressionTree}.</p> 
 * 
 * @author Phil McMinn
 *
 */
public class ExpressionPath implements Duplicable<ExpressionPath> {

	/**
	 * The indices corresponding to the path to an expression.
	 */
    private List<Integer> indices;
    
	/**
	 * Constructor.
	 */
    public ExpressionPath() {
        this.indices = new ArrayList<>();
    }
    
	/**
	 * Constructor.
	 * @param indices a list of integers denoting the path to
	 * an expression.
	 */    
    public ExpressionPath(List<Integer> indices) {
        this();
        for (Integer index : indices) {
            this.indices.add(new Integer(index));
        }        
    }
    
    /**
     * Add a decision point, denoted by an integer, to this path.
     * @param index
     */
    public void add(Integer index) {
        indices.add(index);
    }
    
    /**
     * Get the raw list of indices for this path.
     * @return
     */
    public List<Integer> getIndices() {
        return indices;
    }
    
    @Override
    /**
	 * Performs a deep copy of the current {@link ExpressionPath}
	 * instance and returns it.
     */
    public ExpressionPath duplicate() {
        return new ExpressionPath(indices);
    }
}
