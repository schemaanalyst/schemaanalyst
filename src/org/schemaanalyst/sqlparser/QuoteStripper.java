package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TObjectName;

public class QuoteStripper {

	static final String[] quoteChars = {"\"", "'", "`"};
	
	static boolean isQuoted(String string) {
		for (String quoteChar : quoteChars) {
    		if (string.startsWith(quoteChar) && string.endsWith(quoteChar)) {
    			return true;
    		}
		}
		return false;
	}
	
	static String stripQuotes(String string) {
		return (isQuoted(string)) ? string.substring(1, string.length()-1) : string;
	}
	
	static String stripQuotes(TObjectName name) {
		if (name == null) {
			return null;
		}
		return stripQuotes(name.toString());		
	}	
}
