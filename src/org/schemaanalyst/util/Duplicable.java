package org.schemaanalyst.util;

public interface Duplicable<T extends Duplicable<T>> {

    public T duplicate();
}
