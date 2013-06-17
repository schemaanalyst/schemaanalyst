package org.schemaanalyst.representation.checkcondition;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.representation.Column;

public class BetweenCheckCondition implements CheckCondition {
	
	private static final long serialVersionUID = -1051694082593826861L;
	
	private Column column;
	private Operand lower;
	private Operand upper;
	
	public BetweenCheckCondition(Column column, Operand lower, Operand upper) {
		this.column = column;
		this.lower = lower;
		this.upper = upper;
	}

	public BetweenCheckCondition(Column column, int lower, int upper) {
		this.column = column;
		this.lower = new NumericValue(lower);
		this.upper = new NumericValue(upper);
	}	
	
	public Column getColumn() {
		return column;
	}
	
	public Operand getLower() {
		return lower;
	}
	
	public Operand getUpper() {
		return upper;
	}

	public void accept(CheckConditionVisitor visitor) {
		visitor.visit(this);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		BetweenCheckCondition other = (BetweenCheckCondition) obj;
		if (column == null) {
			if (other.column != null) {
				return false;
			}
		} else if (!column.equals(other.column)) {
			return false;
		}
		
		if (lower == null) {
			if (other.lower != null) {
				return false;
			}
		} else if (!lower.equals(other.lower)) {
			return false;
		}
		
		if (upper == null) {
			if (other.upper != null) {
				return false;
			}
		} else if (!upper.equals(other.upper)) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return column + " BETWEEN " + lower + " AND " + upper;
	}
	
}
