package org.schemaanalyst.datageneration;

public abstract class DataGenerator<E> {

    public abstract TestSuite<E> generate();
}
