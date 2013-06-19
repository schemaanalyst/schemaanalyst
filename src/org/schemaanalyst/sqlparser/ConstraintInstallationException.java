package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TConstraint;

@SuppressWarnings("serial")
public class ConstraintInstallationException extends RuntimeException {

	public ConstraintInstallationException(TConstraint node) {
		super("Constraint \"" + node + "\" is not supported [GSP EConstraintType: " +  node.getConstraint_type() + "]");
	}	
	
	public ConstraintInstallationException(String message) {
		super(message);
	}
}
