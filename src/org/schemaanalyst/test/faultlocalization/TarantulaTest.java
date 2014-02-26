package org.schemaanalyst.test.faultlocalization;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.faultlocalization.Calculator;
import org.schemaanalyst.faultlocalization.TarantulaResultMatrixRow;

public class TarantulaTest {

	@Test
	public void test1() {
		TarantulaResultMatrixRow r = new TarantulaResultMatrixRow("first Constraint" , "first mutant", 2, 5, 5, 2, false);
		Calculator.calculate(r);
//		System.out.println("Score is: " + r.getScore());
		double score = (double) Math.round(r.getScore() * 100)/100;
		assertEquals(0.5, score);
	}

	@Test
	public void test2() {
		TarantulaResultMatrixRow r = new TarantulaResultMatrixRow("second Constraint" , "second mutant", 2, 5, 4, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		assertEquals(0.56, score);
	}

	@Test
	public void test3() {
		TarantulaResultMatrixRow r = new TarantulaResultMatrixRow("first Constraint" , "first mutant", 2, 5, 3, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		assertEquals(0.63, score);
	}


}
