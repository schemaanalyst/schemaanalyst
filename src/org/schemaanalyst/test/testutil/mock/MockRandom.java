package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.util.random.Random;

public class MockRandom extends Random {

    protected int[] nextInts = {0};
    protected int nextIntsIndex = 0;

    public MockRandom() {
        super(0);
    }

    public MockRandom(int... nextInts) {
        super(0);
        setNextInts(nextInts);
    }

    public void setNextInts(int... nextInts) {
        this.nextInts = nextInts;
    }

    @Override
    public int nextInt() {
        int next = nextInts[nextIntsIndex];
        nextIntsIndex++;
        return next;
    }

    @Override
    public int nextInt(int upperBound) {
        int next = nextInt();
        if (next > upperBound) {
            next = upperBound;
        }
        return next;
    }

    @Override
    public boolean nextBoolean() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double nextDouble() {
        // TODO Auto-generated method stub
        return 0;
    }
}
