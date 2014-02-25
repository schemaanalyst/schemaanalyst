package org.schemaanalyst.test.faultlocalization;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.schemaanalyst.faultlocalization.Calculator;
import org.schemaanalyst.faultlocalization.OchiaiResultMatrixRow;

public class OchiaiTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test1() {
		OchiaiResultMatrixRow r = new OchiaiResultMatrixRow("first Constraint" , "first mutant" ,2 , 3, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		Assert.assertEquals(0.63, score);
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void test2() {
		OchiaiResultMatrixRow r = new OchiaiResultMatrixRow("second Constraint" , "first mutant" ,2 , 5, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		Assert.assertEquals(0.53, score);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test3() {
		OchiaiResultMatrixRow r = new OchiaiResultMatrixRow("first Constraint" , "first mutant" ,2 , 4, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		Assert.assertEquals(0.58, score);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test4() {
		OchiaiResultMatrixRow r = new OchiaiResultMatrixRow("first Constraint" , "first mutant" ,2 , 0, 2, false);
		Calculator.calculate(r);
		double score = (double) Math.round(r.getScore() * 100)/100;
		Assert.assertEquals(1.0, score);
	}
}
