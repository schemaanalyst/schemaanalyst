package org.schemaanalyst.test.mutation;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import org.schemaanalyst.mutation.MutationReportScore;
import org.schemaanalyst.mutation.MutationReportScores;

public class TestMutationScoreCalculation {
	
    @Test 
    public void checkInitialStateOfAMutationScore() {
	MutationReportScore score = new MutationReportScore();
	assertEquals(0, score.getNumerator());
	assertEquals(0, score.getDenominator());
	assertEquals(Double.NaN, score.computeScore(), 0);
    }

    @Test 
    public void checkSimpleMutationScoreCalculation() {
	MutationReportScore score = new MutationReportScore();
	score.setNumerator(5);
	score.setDenominator(10);
	assertEquals((double).5, score.computeScore(), 0);
    }

    // mutationScore, (10 / 15 = 0.6666666666666666
    @Test 
    public void checkRealMutationScoreCalculation() {
	MutationReportScore score = new MutationReportScore();
	score.setNumerator(10);
	score.setDenominator(15);
	assertEquals((double).6666666666666666, score.computeScore(), .01);
    }

    // weightedMutationScore, (18 / 165 = 0.10909090909090909
    @Test 
    public void checkRealWeightedMutationScoreCalculation() {
	MutationReportScore score = new MutationReportScore();
	score.setNumerator(18);
	score.setDenominator(165);
	assertEquals((double).10909090909090909, score.computeScore(), .01);
    }

    @Test
    public void testAddAndGetMutationScore() {
	MutationReportScore score = new MutationReportScore("score", 10, 15);
	MutationReportScores scores = new MutationReportScores();
	scores.add(score);
	assertTrue(!(scores.get("score")==null));
	assertTrue((scores.get("scoreNotFound")==null));
	assertEquals((double).6666666666666666, scores.get("score").computeScore(), .01);
    }
    
}

