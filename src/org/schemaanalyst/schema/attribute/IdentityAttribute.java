package org.schemaanalyst.schema.attribute;

public class IdentityAttribute extends Attribute {
	
	private long seed;
	private long increment;
	
	public IdentityAttribute() {
		this(1, 1);
	}
	
	public IdentityAttribute(long seed, long increment) {
		this.seed = seed;
		this.increment = increment;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public long getIncrement() {
		return increment;
	}
	
	public void accept(AttributeVisitor visitor) {
		visitor.visit(this);
	}	
}
