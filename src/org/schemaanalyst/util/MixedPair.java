package org.schemaanalyst.util;

public class MixedPair<S, T> {

    protected S first;
    protected T second;
    
    public MixedPair(S first, T second) {
        this.first = first;
        this.second = second;
    }
    
    public S getFirst() {
        return first;
    }
    
    public T getSecond() {
        return second;
    }  
}
