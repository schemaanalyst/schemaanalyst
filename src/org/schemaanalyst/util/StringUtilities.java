package org.schemaanalyst.util;

public class StringUtilities {

    public static String repeat(String toRepeat, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < times; i++) {
            sb.append(toRepeat);
        }
        return sb.toString();
    }
    
    public static String implode(Iterable<String> items, String sep) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : items) {
            if (first) {
                first = false;
            } else {
                sb.append(sep);
            }
            sb.append(item);
        }
        return sb.toString();
    }
    
    public static String implode(String[] items, String sep) {
        StringBuilder sb = new StringBuilder();        
        for (int i=0; i < items.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(items[i]);
        }
        return sb.toString();
    }
}
