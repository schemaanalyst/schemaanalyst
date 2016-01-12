package org.schemaanalyst.util.random;

public abstract class Random {

    protected long seed;

    public Random(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public abstract boolean nextBoolean();

    public abstract double nextDouble();

    public abstract int nextInt();

    public abstract int nextInt(int ceiling);

    public abstract long nextLong();
}
