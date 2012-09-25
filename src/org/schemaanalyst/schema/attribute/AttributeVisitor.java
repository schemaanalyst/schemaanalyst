package org.schemaanalyst.schema.attribute;

public interface AttributeVisitor {

	public void visit(AutoIncrementAttribute attribute);
	
	public void visit(DefaultAttribute attribute);
	
	public void visit(IdentityAttribute attribute);			
}
