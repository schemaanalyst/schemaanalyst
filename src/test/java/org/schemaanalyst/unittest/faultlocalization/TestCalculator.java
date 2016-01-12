package org.schemaanalyst.unittest.faultlocalization;

import org.junit.Test;
import org.schemaanalyst.faultlocalization.Calculator;
import org.schemaanalyst.faultlocalization.ResultMatrixRow;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import static org.junit.Assert.assertEquals;

public class TestCalculator {

	Constraint c = null;
	@Test
	public void ochiaiTest1() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 2, 0, false);
		Calculator.calculateOchiai(r);
		assertEquals(0.0, r.getOchiaiScore(), 0.01);
		
	}

	@Test
	public void ochiaiTest2() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 3, 2, false);
		Calculator.calculateOchiai(r);
		assertEquals(0.63, r.getOchiaiScore(), 0.01);
		
	}
	
	@Test
	public void ochiaiTest3() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 0, 2, false);
		Calculator.calculateOchiai(r);
		assertEquals(1.0, r.getOchiaiScore(), 0.01);
		
	}

	
	@Test
	public void ochiaiTest4() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 6, 40, 20, 2, false);
		Calculator.calculateOchiai(r);
		assertEquals(0.174, r.getOchiaiScore(), 0.01);
		
	}
	
	@Test
	public void tarantulaTest1() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 5, 2, false);
		Calculator.calculateTarantula(r);
		assertEquals(0.5, r.getTarantulaScore(), 0.01);
		
	}
	
	@Test
	public void tarantulaTest2() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 4, 2, false);
		Calculator.calculateTarantula(r);
		assertEquals(0.55, r.getTarantulaScore(), 0.01);
		
	}
	@Test
	public void tarantulaTest3() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 3, 2, false);
		Calculator.calculateTarantula(r);
		assertEquals(0.625, r.getTarantulaScore(), 0.01);
		
	}
	@Test
	public void tarantulaTest4() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 0, 2, false);
		Calculator.calculateTarantula(r);
		assertEquals(1.0, r.getTarantulaScore(), 0.01);
		
	}
	@Test
	public void tarantulaTest5() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 5, 0, false);
		Calculator.calculateTarantula(r);
		assertEquals(0.0, r.getTarantulaScore(), 0.01);
		
	}
	
	@Test
	public void JaccardTest1() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 5, 2, false);
		Calculator.calculateJaccard(r);
		assertEquals(0.4, r.getJaccardScore(), 0.01);
		
	}
	
	@Test
	public void JaccardTest2() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 4, 2, false);
		Calculator.calculateJaccard(r);
		assertEquals(0.5, r.getJaccardScore(), 0.01);
		
	}
	
	@Test
	public void JaccardTest3() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 5, 0, false);
		Calculator.calculateJaccard(r);
		assertEquals(0.0, r.getJaccardScore(), 0.01);
		
	}
	@Test
	public void JaccardTest4() {
		ResultMatrixRow r = new ResultMatrixRow(c, "first mutant", 2, 5, 2, 2, false);
		Calculator.calculateJaccard(r);
		assertEquals(1.0, r.getJaccardScore(), 0.01);
		
	}





}
