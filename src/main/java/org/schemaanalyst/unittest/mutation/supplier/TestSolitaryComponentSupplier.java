package org.schemaanalyst.unittest.mutation.supplier;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.junit.Assert.*;

public class TestSolitaryComponentSupplier {

	@Test
	public void testNormalUsage() {
		Expression expression = new ConstantExpression(new NumericValue(1));
		CheckConstraint checkConstraint = new CheckConstraint(null, expression);

		CheckExpressionSupplier supplier = new CheckExpressionSupplier();
		supplier.initialise(checkConstraint);

		assertTrue(
				"SolitaryComponentSuppliers have at least one component, so the first call to hasNext() should be true",
				supplier.hasNext());

		assertSame(
				"The solitary component requested should be the CheckConstraint's expression",
				expression, supplier.getNextComponent());

		supplier.setDuplicate(checkConstraint.duplicate());
		Expression duplicateExpression = supplier.getDuplicateComponent();
		
		assertEquals(
				"The duplicate component should equal (identical) to the CheckConstraint's expression",
				expression, duplicateExpression);		

		assertNotSame(
				"The duplicate component should not be the same as the solitary component",
				expression, duplicateExpression);		
		
		assertTrue(
				"Once the solitary component has been obtained, haveCurrent() should return true",
				supplier.hasCurrent());

		assertFalse(
				"Once the solitary component has been obtained, hasNext() should return false",
				supplier.hasNext());

		assertNull(
				"Once the solitary component has been obtained, getNextComponent() should return null",
				supplier.getNextComponent());

		assertFalse(
				"Once getNextComponent() has been called for the second time, haveCurrent() should return false",
				supplier.hasCurrent());
	}
	
	@Test(expected=MutationException.class)
	public void testDuplicateComponentButNoDuplicateCreated() {
		Expression expression = new ConstantExpression(new NumericValue(1));
		CheckConstraint checkConstraint = new CheckConstraint(null, expression);

		CheckExpressionSupplier supplier = new CheckExpressionSupplier();
		supplier.initialise(checkConstraint);
		supplier.getNextComponent();
		supplier.getDuplicateComponent();	
	}
}
