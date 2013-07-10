package org.schemaanalyst.sqlrepresentation.checkcondition;

/**
 * @deprecated
 */
public interface CheckConditionVisitor {
	
	public void visit(BetweenCheckCondition checkCondition);
	
	public void visit(InCheckCondition checkCondition);	
	
	public void visit(RelationalCheckCondition checkCondition);
}
