package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TObjectName;

class QuoteStripper {

    static final String[] QUOTE_CHARS = {"\"", "'", "`"};

    static boolean isQuoted(String string) {
        for (String quoteChar : QUOTE_CHARS) {
            if (string.startsWith(quoteChar) && string.endsWith(quoteChar)) {
                return true;
            }
        }
        return false;
    }

    static String stripQuotes(String string) {
        return (isQuoted(string)) ? string.substring(1, string.length() - 1) : string;
    }

    static String stripQuotes(TObjectName name) {
        if (name == null) {
            return null;
        }
        return stripQuotes(name.toString());
    }
}
