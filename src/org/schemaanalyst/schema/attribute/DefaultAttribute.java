package org.schemaanalyst.schema.attribute;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.schema.Operand;

public class DefaultAttribute extends Attribute {

	private Operand value;
	
	public DefaultAttribute() {
		this.value = null;
	}
	
	public DefaultAttribute(Operand value) {
		this.value = value;
	}
	
	public DefaultAttribute(int value) {
		this.value = new NumericValue(value);
	}
	
	public DefaultAttribute(String value) {
		this.value = new StringValue(value);
	}
	
	public Operand getValue() {
		return value;
	}

	public void accept(AttributeVisitor visitor) {
		visitor.visit(this);
	}		
}
