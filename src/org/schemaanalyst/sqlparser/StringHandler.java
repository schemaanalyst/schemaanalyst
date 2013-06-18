package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TObjectName;

public class StringHandler {

	static final String[] quoteChars = {"\"", "'", "`"};
	
	static boolean isSQLString(String string) {
		for (String quoteChar : quoteChars) {
    		if (string.startsWith(quoteChar) && string.endsWith(quoteChar)) {
    			return true;
    		}
		}
		return false;
	}
	
	static String sanitize(String string) {
		return (isSQLString(string)) ? string.substring(1, string.length()-1) : string;
	}
	
	static String sanitize(TObjectName name) {
		return sanitize(name.toString());		
	}	
}
