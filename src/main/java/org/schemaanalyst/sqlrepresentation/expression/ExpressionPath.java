package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class ExpressionPath {

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
	 * @param indices an series of integers denoting the path to
	 * an expression.
	 */    
    public ExpressionPath(Integer... indices) {
        this(Arrays.asList(indices));        
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
     * @return a raw list of indices for this path.
     */
    public List<Integer> getIndices() {
        return new ArrayList<>(indices);
    }
    
    /**
	 * Performs a deep copy of the current {@link ExpressionPath}
	 * instance and returns it.
     */
    public ExpressionPath duplicate() {
        return new ExpressionPath(indices);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indices == null) ? 0 : indices.hashCode());
		return result;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpressionPath other = (ExpressionPath) obj;
		if (indices == null) {
			if (other.indices != null)
				return false;
		} else if (!indices.equals(other.indices))
			return false;
		return true;
	}

    /**
     * {@inheritDoc}
     */
	@Override
    public String toString() {
    	return indices.toString();
    }
}
