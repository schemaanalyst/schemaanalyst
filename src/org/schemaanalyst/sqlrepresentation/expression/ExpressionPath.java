package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.util.Duplicable;

public class ExpressionPath implements Duplicable<ExpressionPath> {

    private List<Integer> indices;
    
    public ExpressionPath() {
        this.indices = new ArrayList<>();
    }
    
    public ExpressionPath(List<Integer> trace) {
        this();
        for (Integer index : trace) {
            this.indices.add(new Integer(index));
        }        
    }
    
    public void add(Integer index) {
        indices.add(index);
    }
    
    public List<Integer> getIndices() {
        return indices;
    }
    
    @Override
    public ExpressionPath duplicate() {
        return new ExpressionPath(indices);
    }
}
