package org.schemaanalyst.datageneration;

public abstract class DataGenerator<G> {

    public abstract TestSuite<G> generate();
}
