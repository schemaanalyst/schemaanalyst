package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class UnsupportedFeatureException extends RuntimeException {

	public UnsupportedFeatureException(String message) {
		super(message);
	}
	
	public UnsupportedFeatureException(TConstraint constraint) {
		super("Constraint \"" + constraint + "\" is not supported [GSP EConstraintType: " +  constraint.getConstraint_type() + "]");
	}	
	
	public UnsupportedFeatureException(TExpression expression) {
		super("Expression: \"" + expression + "\" is not supported [GSP EExpressionType: " + expression.getExpressionType() + "]");
	}	
	
	public UnsupportedFeatureException(TTypeName dataType) {
		super("Data type: " + dataType + " is not supported [GSP EDataType: "+dataType.getDataType() + "]");
	}	
	
	public UnsupportedFeatureException(TAlterTableOption alterTableOption) {
		super("Alter table option: \"" + alterTableOption + "\" is not supported [GSP EAlterTableOptionType: "+alterTableOption.getOptionType() + "]");
	}
}
