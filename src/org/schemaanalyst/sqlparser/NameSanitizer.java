package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TObjectName;

public class NameSanitizer {

	static String sanitize(TObjectName name) {
		String string = name.toString();
		
    	String[] quoteChars = {"\"", "'"};    	
    	for (String quoteChar : quoteChars) {
    		if (string.startsWith(quoteChar) && string.endsWith(quoteChar)) {
    			return string.substring(1, string.length()-1);
    		}
    	}
    	
    	return string;		
	}	
}
