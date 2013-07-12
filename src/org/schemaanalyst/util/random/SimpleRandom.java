package org.schemaanalyst.util.random;

public class SimpleRandom extends Random {

    protected long seed;
    protected java.util.Random random;

    public SimpleRandom(long seed) {
        super(seed);
        this.random = new java.util.Random(seed);
    }

    public boolean nextBoolean() {
        return nextDouble() > 0 ? true : false;
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int ceiling) {
        return random.nextInt(ceiling);
    }
}
