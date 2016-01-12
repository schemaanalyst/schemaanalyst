package org.schemaanalyst.unittest.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionPath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestExpressionPath {

	@Test
	public void testDuplication() {
		ExpressionPath path = new ExpressionPath(1, 2);
		ExpressionPath duplicate = path.duplicate();
		
		assertEquals(new ExpressionPath(1, 2), duplicate);
		assertNotSame(path, duplicate);
		assertEquals(path, duplicate);
	}	
	
	@Test
	public void testAddIndex() {
		ExpressionPath path = new ExpressionPath(1, 2);
		path.add(3);
		
		assertEquals(new ExpressionPath(1, 2, 3), path);
	}		
	
}
