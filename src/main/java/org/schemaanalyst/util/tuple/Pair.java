package org.schemaanalyst.util.tuple;

public class Pair<T> extends MixedPair<T, T> {

    public Pair(T first, T second) {
        super(first, second);
    }  

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }
}
