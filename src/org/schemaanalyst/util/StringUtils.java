package org.schemaanalyst.util;

public class StringUtils {

    public static String repeat(String toRepeat, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < times; i++) {
            sb.append(toRepeat);
        }
        return sb.toString();
    }
    
    public static <T> String implode(Iterable<T> items, String sep) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (T item : items) {
            if (first) {
                first = false;
            } else {
                sb.append(sep);
            }
            sb.append(item.toString());
        }
        return sb.toString();
    }
    
    public static <T> String implode(T[] items, String sep) {
        StringBuilder sb = new StringBuilder();        
        for (int i=0; i < items.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(items[i].toString());
        }
        return sb.toString();
    }
}
