package org.schemaanalyst.test.mutation.supplier;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.CheckExpressionSupplier;
import org.schemaanalyst.mutation.supplier.InExpressionRHSListExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

public class TestInExpressionRHSListExpressionSupplier {
	
	ListExpression listExpression = new ListExpression(
			new ConstantExpression(new NumericValue(2)), 
		    new ConstantExpression(new NumericValue(3))); 
	
	InExpression inExpression = new InExpression(
			new ConstantExpression(new NumericValue(1)),
			listExpression,
			false);		
	
	Schema schema = new Schema("schema");
	Table table = schema.createTable("table");
	CheckConstraint checkConstraint = schema.createCheckConstraint(table, inExpression);	
	
	@Test
	public void testDuplication() {

		InExpressionRHSListExpressionSupplier supplier =
				new InExpressionRHSListExpressionSupplier();
		
		supplier.initialise(inExpression);
		
		assertSame(
				"The original artefact should be the same as that passed to initialise",
				inExpression, supplier.getOriginalArtefact());
		
		assertTrue(supplier.hasNext());
		
		List<Expression> component = supplier.getNextComponent();		
		assertEquals(listExpression.getSubexpressions().get(0), component.get(0));	
		assertSame(listExpression.getSubexpressions().get(0), component.get(0));
		assertEquals(listExpression.getSubexpressions().get(1), component.get(1));	
		assertSame(listExpression.getSubexpressions().get(1), component.get(1));
		
		Expression duplicate = supplier.makeDuplicate(); 
		List<Expression> duplicateComponent = supplier.getDuplicateComponent();
		assertNotSame(inExpression, duplicate);
		assertEquals(inExpression, duplicate);
		assertEquals(listExpression.getSubexpressions().get(0), duplicateComponent.get(0));	
		assertNotSame(listExpression.getSubexpressions().get(0), duplicateComponent.get(0));
		assertEquals(listExpression.getSubexpressions().get(1), duplicateComponent.get(1));	
		assertNotSame(listExpression.getSubexpressions().get(1), duplicateComponent.get(1));
		
		// now mutate the list 
		List<Expression> mutantSubexpressions =  Collections.singletonList((Expression) new ConstantExpression(new NumericValue(5)));
		supplier.putComponentBackInDuplicate(mutantSubexpressions);		
		//System.out.println(duplicate);
		//System.out.println(duplicateComponent);
		assertEquals(duplicateComponent.get(0), mutantSubexpressions.get(0));   
        //assertNotSame(duplicateComponent.get(0), mutantSubexpressions.get(0));
		assertFalse(supplier.hasNext());
		
		
	}	
	
	@Test
	public void testDuplicationInChainedSupplier() {
		
		CheckExpressionSupplier topLevelSupplier = 
				new CheckExpressionSupplier(); 
				
		InExpressionRHSListExpressionSupplier bottomLevelSupplier =
				new InExpressionRHSListExpressionSupplier();
		
		ChainedSupplier<Schema, Expression, List<Expression>> supplier = 
				new ChainedSupplier<>(topLevelSupplier, bottomLevelSupplier);
		
		supplier.initialise(schema);
		
		assertSame(
				"The original artefact should be the same as that passed to initialise",
				schema, supplier.getOriginalArtefact());
		assertTrue(
				"The supplier should have another component",
				supplier.hasNext());
		
		List<Expression> component = supplier.getNextComponent();		
		assertEquals(listExpression.getSubexpressions().get(0), component.get(0));	
		assertSame(listExpression.getSubexpressions().get(0), component.get(0));
		assertEquals(listExpression.getSubexpressions().get(1), component.get(1));	
		assertSame(listExpression.getSubexpressions().get(1), component.get(1));
		
		Schema duplicate = supplier.makeDuplicate(); 
		assertNotSame(
				"The duplicate should not be the same as the original artifact",
				schema, duplicate);
		assertEquals(schema, duplicate);
		
		List<Expression> duplicateComponent = supplier.getDuplicateComponent();
		assertEquals(listExpression.getSubexpressions().get(0), duplicateComponent.get(0));	
		assertNotSame(listExpression.getSubexpressions().get(0), duplicateComponent.get(0));
		assertEquals(listExpression.getSubexpressions().get(1), duplicateComponent.get(1));	
		assertNotSame(listExpression.getSubexpressions().get(1), duplicateComponent.get(1));
		
		// now mutate the list 
		List<Expression> mutantSubexpressions =  Collections.singletonList((Expression) new ConstantExpression(new NumericValue(5)));
		supplier.putComponentBackInDuplicate(mutantSubexpressions);		
		assertSame(listExpression.getSubexpressions(), mutantSubexpressions);		
		
		assertFalse(supplier.hasNext());				
	}
}
