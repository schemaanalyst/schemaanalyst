package org.schemaanalyst.test.mutation.supplier;

import java.util.List;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.expression.InExpressionRHSListExpressionSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

import static org.junit.Assert.*;

public class TestChainedSupplier {

	ListExpression listExpression1 = new ListExpression(
			new ConstantExpression(new NumericValue(2)), 
		    new ConstantExpression(new NumericValue(3))); 
	
	InExpression inExpression1 = new InExpression(
			new ConstantExpression(new NumericValue(1)),
			listExpression1,
			false);

	ListExpression listExpression2 = new ListExpression(
			new ConstantExpression(new NumericValue(12)), 
		    new ConstantExpression(new NumericValue(13)));
	
	InExpression inExpression2 = new InExpression(
			new ConstantExpression(new NumericValue(11)),
			listExpression2,
			false);			
			
	@Test
	public void testOneConstraint() {
		Schema schema = new Schema("schema");
		Table table = schema.createTable("table");
		schema.createCheckConstraint(table, inExpression1);
		
		ChainedSupplier<Schema, Expression, List<Expression>> supplier =
				new ChainedSupplier<>(
						new CheckExpressionSupplier(),
						new InExpressionRHSListExpressionSupplier());
		
		supplier.initialise(schema);
		assertSame(
				"The original artefact should be the same as that passed to initialise",
				schema, supplier.getOriginalArtefact());
		
		assertTrue(supplier.hasNext());
		assertEquals(listExpression1.getSubexpressions(), supplier.getNextComponent());	
		assertFalse(supplier.hasNext());
	}

	@Test
	public void testTwoConstraintsOneTable() {
		Schema schema = new Schema("schema");
		Table table = schema.createTable("table");
		schema.createCheckConstraint(table, inExpression1);
		schema.createCheckConstraint(table, inExpression2);
		
		ChainedSupplier<Schema, Expression, List<Expression>> supplier =
				new ChainedSupplier<>(
						new CheckExpressionSupplier(),
						new InExpressionRHSListExpressionSupplier());
		
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());	
		assertEquals(listExpression1.getSubexpressions(), supplier.getNextComponent());	
		
		assertTrue(supplier.hasNext());
		assertEquals(listExpression2.getSubexpressions(), supplier.getNextComponent());
		
		assertFalse(supplier.hasNext());
	}	
	
	@Test
	public void testTwoConstraintsTwoTables() {
		Schema schema = new Schema("schema");
		Table table1 = schema.createTable("table1");
		Table table2 = schema.createTable("table2");
		schema.createCheckConstraint(table1, inExpression1);
		schema.createCheckConstraint(table2, inExpression2);
		
		ChainedSupplier<Schema, Expression, List<Expression>> supplier =
				new ChainedSupplier<>(
						new CheckExpressionSupplier(),
						new InExpressionRHSListExpressionSupplier());
		
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());	
		assertEquals(listExpression1.getSubexpressions(), supplier.getNextComponent());	
		
		assertTrue(supplier.hasNext());
		assertEquals(listExpression2.getSubexpressions(), supplier.getNextComponent());
		
		assertFalse(supplier.hasNext());
	}
	
	@Test
	public void testReinitialise() {
		Schema schema = new Schema("schema");
		Table table1 = schema.createTable("table1");
		Table table2 = schema.createTable("table2");
		schema.createCheckConstraint(table1, inExpression1);
		schema.createCheckConstraint(table2, inExpression2);
		
		ChainedSupplier<Schema, Expression, List<Expression>> supplier =
				new ChainedSupplier<>(
						new CheckExpressionSupplier(),
						new InExpressionRHSListExpressionSupplier());
		
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());	
		assertEquals(listExpression1.getSubexpressions(), supplier.getNextComponent());	
		
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());
		assertEquals(listExpression1.getSubexpressions(), supplier.getNextComponent());
		
		assertTrue(supplier.hasNext());
		assertEquals(listExpression2.getSubexpressions(), supplier.getNextComponent());		
		
		assertFalse(supplier.hasNext());
	}	
}
