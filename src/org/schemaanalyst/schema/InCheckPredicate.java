package org.schemaanalyst.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.Value;

public class InCheckPredicate implements CheckPredicate {

	private static final long serialVersionUID = 7854975706131599964L;

	private Column column;
	private List<Value> values;
	
	public InCheckPredicate(Column column, Value... values) {
		this.column = column;
		this.values = Arrays.asList(values);
	}

	public InCheckPredicate(Column column, int... values) {
		this.column = column;
		this.values = new ArrayList<Value>();
		for (int value : values) {
			this.values.add(new NumericValue(value));
		}
	}	
	
	public InCheckPredicate(Column column, String... values) {
		this.column = column;
		this.values = new ArrayList<Value>();
		for (String value : values) {
			this.values.add(new StringValue(value));
		}
	}
	
	public Column getColumn() {
		return column;
	}	
	
	public List<Value> getValues() {
		return Collections.unmodifiableList(values);
	}
	
	public void accept(CheckPredicateVisitor visitor) {
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
		
		InCheckPredicate other = (InCheckPredicate) obj;
		if (column == null) { 
			if (other.column != null) {
				return false;
			}
		} else if (!column.equals(other.column)) {
			return false;
		}
		
		if (values == null) {
			if (other.values != null) {
				return false;
			}
		} else {
			Iterator<Value> it = values.iterator();
			Iterator<Value> otherIt = other.values.iterator();
			
			while (it.hasNext() || otherIt.hasNext()) {
				if (it.hasNext() && otherIt.hasNext()) {
					if (!it.next().equals(otherIt.next())) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(column);
		sb.append(" IN ");
		boolean first = true;
		for (Value value : values) {
			if (first) first = false;
			else sb.append(",");
			sb.append(value);
		}
		return sb.toString();
	}	
}
