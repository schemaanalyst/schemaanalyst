package org.schemaanalyst.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String repeat(String toRepeat, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < times; i++) {
            sb.append(toRepeat);
        }
        return sb.toString();
    }
    
    public static <T> String implode(Iterable<T> items, String sep) {
        if (items == null) {
            return "";
        }
        
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
        if (items == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();        
        for (int i=0; i < items.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(items[i].toString());
        }
        return sb.toString();
    }
    
    public static List<String> explode(String str, String sep) {
        String[] splitStrings = str.split(sep);
        List<String> list = new ArrayList<>();
        for (String s : splitStrings) {
            s = s.trim();
            if (s.length() > 0) {
                list.add(s);
            }
        }
        return list;
    }
}
