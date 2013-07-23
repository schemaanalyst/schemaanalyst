package org.schemaanalyst.test.mutation;


import org.junit.Test;
import static org.junit.Assert.*;

import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.mutation.MutationReportScores;

public class TestMutationScoreCalculation {

    @Test
    public void checkInitialStateOfAMutationScore() {
        MutationReportScore score = new MutationReportScore(null, 0, 0);
        assertEquals(0, score.getNumerator());
        assertEquals(0, score.getDenominator());
        assertEquals(Double.NaN, score.getScore(), 0);
    }

    @Test
    public void checkSimpleMutationScoreCalculation() {
        MutationReportScore score = new MutationReportScore(null, 5, 10);
        assertEquals((double) .5, score.getScore(), 0);
    }

    // mutationScore, (10 / 15 = 0.6666666666666666
    @Test
    public void checkRealMutationScoreCalculation() {
        MutationReportScore score = new MutationReportScore(null, 10, 15);
        assertEquals((double) .6666666666666666, score.getScore(), .01);
    }

    // weightedMutationScore, (18 / 165 = 0.10909090909090909
    @Test
    public void checkRealWeightedMutationScoreCalculation() {
        MutationReportScore score = new MutationReportScore(null, 18, 165);
        assertEquals((double) .10909090909090909, score.getScore(), .01);
    }

    @Test
    public void testAddAndGetMutationScore() {
        MutationReportScore score = new MutationReportScore("score", 10, 15);
        MutationReportScores scores = new MutationReportScores();
        scores.add(score);
        assertTrue(!(scores.get("score") == null));
        assertTrue((scores.get("scoreNotFound") == null));
        assertEquals((double) .6666666666666666, scores.get("score").getScore(), .01);
    }
}
