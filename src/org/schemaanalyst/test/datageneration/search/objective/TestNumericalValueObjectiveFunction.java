package org.schemaanalyst.test.datageneration.search.objective;

import java.math.BigDecimal;

import org.junit.Test;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.NumericValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;

import static org.schemaanalyst.datageneration.search.objective.relationalpredicate.NumericValueObjectiveFunction.K;
import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.GREATER_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.LESS;
import static org.schemaanalyst.logic.RelationalOperator.LESS_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;
import static org.schemaanalyst.test.junit.BigDecimalAssert.assertEquals;
import static org.schemaanalyst.test.junit.BigDecimalAssert.assertZero;

public class TestNumericalValueObjectiveFunction {
	
	static BigDecimal computeDistance(BigDecimal lhsValue, RelationalOperator op, BigDecimal rhsValue) {
		
		class MockNumericalValueObjectiveFunction extends NumericValueObjectiveFunction {
			
			public BigDecimal computeDistance(BigDecimal lhs, RelationalOperator op, BigDecimal rhs) {
				return super.computeDistance(lhs, op, rhs);
			}
		}
		
		return new MockNumericalValueObjectiveFunction().computeDistance(lhsValue, op, rhsValue);
	}
	
	@Test
	public void equals() {
		assertZero("True distance should be zero", 
				   computeDistance(new BigDecimal("1000.1"), EQUALS, new BigDecimal("1000.1")));
		
		assertEquals("False distance should be non-zero",
					 new BigDecimal("0.1").add(K), 
					 computeDistance(new BigDecimal("1000.0"), EQUALS, new BigDecimal("1000.1")));
	}
	
	@Test
	public void notEquals() {
		assertZero("True distance should be zero",
				   computeDistance(new BigDecimal("1000.0"),  NOT_EQUALS, new BigDecimal("1000.1")));
		
		assertEquals("False distance should be non-zero",
					 K, 
					 computeDistance(new BigDecimal("1000.1"), NOT_EQUALS, new BigDecimal("1000.1")));
	}	
	
	@Test
	public void lessDistance() {
		assertZero("True distance should be zero", 
				   computeDistance(new BigDecimal("500"), LESS, new BigDecimal("999.999")));
		
		assertEquals("False distance should be non-zero",
					 new BigDecimal("200.1").add(K), 
					 computeDistance(new BigDecimal("700.1"),  LESS, new BigDecimal("500")));
	}		
	
	@Test
	public void lessOrEqualsDistance() {
		assertZero("True distance should be zero", 
				   computeDistance(new BigDecimal("500"),  LESS_OR_EQUALS, new BigDecimal("500")));
		
		assertEquals("False distance should be non-zero",
					 new BigDecimal("210.1").add(K), 
					 computeDistance(new BigDecimal("710.1"), LESS_OR_EQUALS, new BigDecimal("500")));
	}			

	@Test
	public void greaterDistance() {
		assertZero("True distance should be zero", 
				   computeDistance(new BigDecimal("750"), GREATER, new BigDecimal("600")));
		
		assertEquals("False distance should be non-zero",
					 new BigDecimal("100").add(K), 
					 computeDistance(new BigDecimal("600"),  GREATER, new BigDecimal("700")));
	}		
	
	@Test
	public void greaterOrEqualsDistance() {
		assertZero("True distance should be zero", 
				   computeDistance(new BigDecimal("600.1"), GREATER_OR_EQUALS, new BigDecimal("600")));
		
		assertEquals("False distance should be non-zero",
					 new BigDecimal("0.1").add(K), 
					 computeDistance(new BigDecimal("600"),  GREATER_OR_EQUALS, new BigDecimal("600.1")));
	}		
}
