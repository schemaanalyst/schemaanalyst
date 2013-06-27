package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.Table;

public class VariableNameManager {

	// variable name prefixes
	static final String TABLE_VAR_PREFIX = "table";
	
	public VariableNameManager() {
		
	}
	
	public String getVariableName(Table table) {
		return getJavaVariableName(TABLE_VAR_PREFIX, table.getName());
	}
	
	protected String getJavaVariableName(String prefix, String originalIdentifier) {
		// TODO - camel case the original identifier to make it neater ...
		return prefix + originalIdentifier;
	}	
}
