package org.schemaanalyst.schema;

public interface CheckPredicateVisitor {
	
	public void visit(BetweenCheckPredicate predicate);
	
	public void visit(InCheckPredicate predicate);
	
	public void visit(RelationalCheckPredicate predicate);
}
