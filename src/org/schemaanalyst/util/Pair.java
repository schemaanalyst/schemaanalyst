package org.schemaanalyst.util;

public class Pair<T> {

    protected T first, second;
    
    public Pair(T lhs, T rhs) {
        this.first = lhs;
        this.second = rhs;
    }
    
    public T getFirst() {
        return first;
    }
    
    public T getSecond() {
        return second;
    }
   
}
