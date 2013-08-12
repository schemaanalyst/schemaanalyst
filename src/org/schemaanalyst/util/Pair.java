package org.schemaanalyst.util;

public class Pair<T> {

    protected T first, second;
    
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    
    public T getFirst() {
        return first;
    }
    
    public T getSecond() {
        return second;
    }  
}
