package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TAlterTableOption;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TParseTreeNode;
import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class UnsupportedFeatureException extends SQLParseException {

	public UnsupportedFeatureException(String message) {
		super(message);
	}
	
	public UnsupportedFeatureException(String message, TParseTreeNode node) {
		super(message + " at \"" + node + "\", input line " + node.getLineNo() + ", column " + node.getColumnNo());
	}
	
	public UnsupportedFeatureException(TConstraint constraint) {
		this("Unsupported constraint (GSP EConstraintType: " +  constraint.getConstraint_type() + ")", constraint);
	}	
	
	public UnsupportedFeatureException(TExpression expression) {
		this("Unsupported expression (GSP EExpressionType: " + expression.getExpressionType() + ")", expression);
	}	
	
	public UnsupportedFeatureException(TTypeName dataType, TParseTreeNode node) {
		this("Unsupported datatype (GSP EDataType: " + dataType.getDataType() + ")", node);
	}	
	
	public UnsupportedFeatureException(TAlterTableOption alterTableOption) {
		this("Unsupported alter table option (GSP EAlterTableOptionType: "+alterTableOption.getOptionType() + ")", alterTableOption);
	}
}
