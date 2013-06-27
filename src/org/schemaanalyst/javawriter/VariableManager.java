package org.schemaanalyst.javawriter;

import org.schemaanalyst.sqlrepresentation.Table;

public class VariableManager {

	// variable name prefixes
	static final String TABLE_VAR_PREFIX = "table";
	
	public VariableManager() {
		
	}

	public String getMethodCallCode(Table table, String methodName, String... args) {
		return getVariableName(table) + "." + methodName + "(" + args + ")";
	}
		
	public String getVariableName(Table table) {
		return getJavaVariableName(TABLE_VAR_PREFIX, table.getName());
	}
		
	protected String getJavaVariableName(String prefix, String originalIdentifier) {
		// TODO - camel case the original identifier to make it neater ...
		return prefix + originalIdentifier;
	}	
}
