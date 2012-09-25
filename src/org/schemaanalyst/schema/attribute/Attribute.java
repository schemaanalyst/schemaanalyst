package org.schemaanalyst.schema.attribute;

public abstract class Attribute {

	public abstract void accept(AttributeVisitor attributeVisitor);	
}
