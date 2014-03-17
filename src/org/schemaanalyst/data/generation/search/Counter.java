package org.schemaanalyst.data.generation.search;

public class Counter {

    private String name;
    private int counter;

    public Counter(String name) {
        this.name = name;
        reset();
    }

    public void reset() {
        counter = 0;
    }

    public void decrement() {
        counter--;
    }

    public void increment() {
        counter++;
    }

    public int getValue() {
        return counter;
    }

    @Override
    public String toString() {
        return name + ": " + counter;
    }
}
