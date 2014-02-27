package org.schemaanalyst.test.faultlocalization;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.schemaanalyst.faultlocalization.ProcessMatrix;
import org.schemaanalyst.faultlocalization.ResultMatrix;
import org.schemaanalyst.faultlocalization.ResultMatrixRow;

public class TestProcessMatrix {

	@Test
	public void testRankingsSame() {
		ResultMatrixRow r1 = new ResultMatrixRow("first constraint" , "first mutant", 2, 5, 5, 2, false);
		ResultMatrixRow r2 = new ResultMatrixRow("second constraint" , "second mutant", 2, 5, 4, 2, false);
		ResultMatrixRow r3 = new ResultMatrixRow("third constraint" , "third mutant", 2, 5, 3, 2, false);
		
		ArrayList<ResultMatrixRow> rows = new ArrayList<>();
		rows.add(r1);
		rows.add(r2);
		rows.add(r3);
		
		ResultMatrix matrix = new ResultMatrix(rows);
		ProcessMatrix.Process(matrix);
		ResultMatrix ochiai = ProcessMatrix.OchiaiRanked;
		ResultMatrix tarantula = ProcessMatrix.TarantulaRanked;
		ResultMatrix jaccard = ProcessMatrix.JaccardRanked;
		
		assertEquals(0.63, ochiai.getRow(0).getOchiaiScore(), 0.01);
		assertEquals(0.577, ochiai.getRow(1).getOchiaiScore(), 0.01);
		assertEquals(0.53, ochiai.getRow(2).getOchiaiScore(), 0.01);
		
		assertTrue(ochiai.getRow(0).getDBConstraint().equals("third constraint"));
		assertTrue(ochiai.getRow(1).getDBConstraint().equals("second constraint"));
		assertTrue(ochiai.getRow(2).getDBConstraint().equals("first constraint"));
		
		
		assertEquals(0.625, tarantula.getRow(0).getTarantulaScore(), 0.01);
		assertEquals(0.555, tarantula.getRow(1).getTarantulaScore(), 0.01);
		assertEquals(0.5, tarantula.getRow(2).getTarantulaScore(), 0.01);
		
		assertTrue(tarantula.getRow(0).getDBConstraint().equals("third constraint"));
		assertTrue(tarantula.getRow(1).getDBConstraint().equals("second constraint"));
		assertTrue(tarantula.getRow(2).getDBConstraint().equals("first constraint"));
		
		assertEquals(0.666, jaccard.getRow(0).getJaccardScore(), 0.01);
		assertEquals(0.5, jaccard.getRow(1).getJaccardScore(), 0.01);
		assertEquals(0.4, jaccard.getRow(2).getJaccardScore(), 0.01);
		
		assertTrue(jaccard.getRow(0).getDBConstraint().equals("third constraint"));
		assertTrue(jaccard.getRow(1).getDBConstraint().equals("second constraint"));
		assertTrue(jaccard.getRow(2).getDBConstraint().equals("first constraint"));
	}
	
	@Test
	public void testRankingsDifferent(){
		
		ResultMatrixRow r1 = new ResultMatrixRow("first constraint" , "first mutant", 5, 13, 10, 4, false);
		ResultMatrixRow r2 = new ResultMatrixRow("second constraint" , "second mutant", 5, 13, 4, 2, false);
		ResultMatrixRow r3 = new ResultMatrixRow("third constraint" , "third mutant", 5, 13, 8, 1, false);
		
		ArrayList<ResultMatrixRow> rows = new ArrayList<>();
		rows.add(r1);
		rows.add(r2);
		rows.add(r3);
		
		ResultMatrix matrix = new ResultMatrix(rows);
		ProcessMatrix.Process(matrix);
		ResultMatrix ochiai = ProcessMatrix.OchiaiRanked;
		ResultMatrix tarantula = ProcessMatrix.TarantulaRanked;
		ResultMatrix jaccard = ProcessMatrix.JaccardRanked;
		
		assertEquals(0.478, ochiai.getRow(0).getOchiaiScore(), 0.01);
		assertEquals(0.365, ochiai.getRow(1).getOchiaiScore(), 0.01);
		assertEquals(0.149, ochiai.getRow(2).getOchiaiScore(), 0.01);
		
		assertTrue(ochiai.getRow(0).getDBConstraint().equals("first constraint"));
		assertTrue(ochiai.getRow(1).getDBConstraint().equals("second constraint"));
		assertTrue(ochiai.getRow(2).getDBConstraint().equals("third constraint"));
		
		
		assertEquals(0.565, tarantula.getRow(0).getTarantulaScore(), 0.01);
		assertEquals(0.5098, tarantula.getRow(1).getTarantulaScore(), 0.01);
		assertEquals(0.245, tarantula.getRow(2).getTarantulaScore(), 0.01);
		
		assertTrue(tarantula.getRow(0).getDBConstraint().equals("second constraint"));
		assertTrue(tarantula.getRow(1).getDBConstraint().equals("first constraint"));
		assertTrue(tarantula.getRow(2).getDBConstraint().equals("third constraint"));
		
		assertEquals(0.36, jaccard.getRow(0).getJaccardScore(), 0.01);
		assertEquals(0.286, jaccard.getRow(1).getJaccardScore(), 0.01);
		assertEquals(0.083, jaccard.getRow(2).getJaccardScore(), 0.01);
		
		assertTrue(jaccard.getRow(0).getDBConstraint().equals("first constraint"));
		assertTrue(jaccard.getRow(1).getDBConstraint().equals("second constraint"));
		assertTrue(jaccard.getRow(2).getDBConstraint().equals("third constraint"));
		
		
		
	}

}
