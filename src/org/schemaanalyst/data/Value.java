package org.schemaanalyst.data;

import java.io.Serializable;

import org.schemaanalyst.util.Duplicable;

public abstract class Value implements Comparable<Value>, Duplicable<Value>,
        Serializable {

    private static final long serialVersionUID = -5756271284942346822L;

    public void increment() {
    }

    public void decrement() {
    }

    public abstract void accept(ValueVisitor visitor);

    @Override
    public abstract Value duplicate();

    @Override
    public abstract int compareTo(Value v);

    @Deprecated
    public static Integer compareTo3VL(Value v1, Value v2) {
        return null;
    }

    @Deprecated
    public static Boolean equals3VL(Value v1, Value v2) {
        if (v1 == null || v2 == null) {
            return null;
        } else {
            return v1.equals(v2);
        }
    }
}
