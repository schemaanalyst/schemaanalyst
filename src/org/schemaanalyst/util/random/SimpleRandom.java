package org.schemaanalyst.util.random;

public class SimpleRandom extends Random {

    protected java.util.Random random;

    public SimpleRandom(long seed) {
        super(seed);
        this.random = new java.util.Random(seed);
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int ceiling) {
        return random.nextInt(ceiling);
    }

    @Override
    public long nextLong() {
        return random.nextLong();
    }
}
