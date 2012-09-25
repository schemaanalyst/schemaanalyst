package org.schemaanalyst.data;

public class BooleanValue extends Value {

	private static final long serialVersionUID = 2243548983716445823L;

	protected boolean value;
		
	public BooleanValue() {
	}

	public BooleanValue(boolean value) {
		this.value = value;
	}
	
	public boolean get() {
		return value;
	}		
	
	public void set(boolean value) {
		this.value = value;
	}
	
	public void accept(ValueVisitor valueVisitor) {
		valueVisitor.visit(this);
	}

	public BooleanValue duplicate() {
		BooleanValue duplicate = new BooleanValue();
		duplicate.value = value;
		return duplicate;
	}	
	
	public int compareTo(Value other) {
		throw new DataException("Cannot compare BooleanValues");
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
		
		BooleanValue other = (BooleanValue) obj;
		return value == other.value;
	}	
	
	public String toString() {
		return value ? "TRUE" : "FALSE";
	}
}
