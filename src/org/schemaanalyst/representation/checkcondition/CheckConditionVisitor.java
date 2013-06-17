package org.schemaanalyst.representation.checkcondition;

public interface CheckConditionVisitor {
	
	public void visit(BetweenCheckCondition checkCondition);
	
	public void visit(InCheckCondition checkCondition);	
	
	public void visit(RelationalCheckCondition checkCondition);
}
