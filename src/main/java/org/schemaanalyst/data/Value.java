package org.schemaanalyst.data;

import java.io.Serializable;

public abstract class Value implements Comparable<Value>, Serializable {

    private static final long serialVersionUID = -5756271284942346822L;

    public abstract void accept(ValueVisitor visitor);

    public abstract Value duplicate();

    @Override
    public abstract int compareTo(Value v);
}
