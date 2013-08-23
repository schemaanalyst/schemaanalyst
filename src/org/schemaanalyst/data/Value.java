package org.schemaanalyst.data;

import java.io.Serializable;

import org.schemaanalyst.util.Duplicable;

public abstract class Value implements Comparable<Value>, Duplicable<Value>,
        Serializable {

    private static final long serialVersionUID = -5756271284942346822L;

    public abstract void accept(ValueVisitor visitor);

    @Override
    public abstract Value duplicate();

    @Override
    public abstract int compareTo(Value v);
}
