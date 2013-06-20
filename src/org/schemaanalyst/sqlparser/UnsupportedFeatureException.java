package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class UnsupportedFeatureException extends RuntimeException {

	public UnsupportedFeatureException(String message) {
		super(message);
	}
	
	public UnsupportedFeatureException(TConstraint node) {
		super("Constraint \"" + node + "\" is not supported [GSP EConstraintType: " +  node.getConstraint_type() + "]");
	}	
	
	public UnsupportedFeatureException(TExpression node) {
		super("Expression: \"" + node + "\" is not supported [GSP EExpressionType: " + node.getExpressionType() + "]");
	}	
	
	public UnsupportedFeatureException(TTypeName dataType) {
		super("Data type: " + dataType + " is not supported [GSP EDataType: "+dataType.getDataType() + "]");
	}	
	
}
