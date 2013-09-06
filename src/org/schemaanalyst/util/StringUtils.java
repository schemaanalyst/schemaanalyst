package org.schemaanalyst.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for String operations.
 * @deprecated Please use Apache Commons library instead.
 */
@Deprecated
public class StringUtils {

    /**
     * Repeats a String a specified number of times.
     * 
     * @param toRepeat The String to repeat
     * @param times The number of times
     * @return The String repeated
     */
    public static String repeat(String toRepeat, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < times; i++) {
            sb.append(toRepeat);
        }
        return sb.toString();
    }
    
    /**
     * Implodes an Iterable object into a String, using ", " as a separator.
     * 
     * @param <T> The generic class
     * @param items The iterable object to implode
     * @return The String representation created
     */
    public static <T> String implode(Iterable<T> items) {
    	return implode(items, ", ");
    }
    
    /**
     * Implodes an Iterable object into a String, using a given separator.
     * 
     * @param <T> The generic class
     * @param items The iterable object to implode
     * @param sep The separator to use
     * @return The String representation created
     */
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
            sb.append((item == null) ? "null" : item.toString());
        }
        return sb.toString();
    }
    
    /**
     * Implodes an Array into a String, using ", " as a separator.
     * 
     * @param <T> The generic class
     * @param items The Array to implode
     * @return The String representation created
     */
    public static <T> String implode(T[] items) {
        return implode(Arrays.asList(items), ", ");
    }    
    
    /**
     * Implodes an Array into a String, using a given separator.
     * 
     * @param <T> The generic class
     * @param items The Array to implode
     * @param sep The separator to use
     * @return The String representation created
     */
    public static <T> String implode(T[] items, String sep) {
        return implode(Arrays.asList(items), sep);
    }
    
    /**
     * Explodes a String into a List, using "," as a separator, with ArrayList as 
     * the backing List implementation.  Each exploded string element is trimmed.
     * 
     * @param str The String to explode
     * @return The List of values created
     */
    public static List<String> explode(String str) {
        return explode(str, ",");
    }    
    
    /**
     * Explodes a String into a List, based on a separator, with ArrayList as 
     * the backing List implementation.  Each exploded string element is trimmed.
     * 
     * @param str The String to explode
     * @param sep The separator delimiting values
     * @return The List of values created
     */
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
