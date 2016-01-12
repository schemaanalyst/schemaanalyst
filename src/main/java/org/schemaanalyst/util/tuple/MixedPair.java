package org.schemaanalyst.util.tuple;

import java.util.Objects;

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
    
    @Override
    public String toString() {
        return "MixedPair(" + first + ", " + second + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.first);
        hash = 61 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MixedPair<?, ?> other = (MixedPair<?, ?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }
    
    
}
