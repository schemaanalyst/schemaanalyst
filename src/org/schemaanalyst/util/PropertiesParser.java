/*
 */
package org.schemaanalyst.util;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Parses a Java Properties file into a POJO, ignoring when a Properties item 
 * is not in the POJO and ignoring when a field in the POJO is not in the 
 * Properties file. If a field exists in both, a default value can be specified 
 * in the class. The value will be used, unless the Properties file specifies 
 * a value, which replaces this.
 * 
 * For example, if 'a' and 'b' are fields of the class and 'b' and 'c' are 
 * present in the Properties file, then only 'b' will be instantiated. If a 
 * field is given a value by default, then this is included provided it is not 
 * overridden by the properties file.
 * 
 * Fields in the POJO may either be of type String or any primitive type, 
 * excluding byte, or their corresponding wrapper objects.
 * 
 * @author Chris J. Wright
 */
public class PropertiesParser {

    /**
     * Parses a file at the given path to an instance of the given class.
     * 
     * @param <T> The type of the POJO (usually inferred).
     * @param filepath The path to the Properties file.
     * @param clazz The class of the POJO.
     * @return The instantiated POJO.
     */
    public static <T> T parse(String filepath, Class clazz) {
        return parse(new File(filepath), clazz);
    }

    /**
     * Parse the file to an instance of the given class.
     * 
     * @param <T> The type of the POJO (usually inferred).
     * @param file The Properties file.
     * @param clazz The class of the POJO.
     * @return The instantiated POJO.
     */
    public static <T> T parse(File file, Class clazz) {
        try (FileReader reader = new FileReader(file)) {
            Properties p = new Properties();
            p.load(reader);
            return reflectFields(p, clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse properties file to object", ex);
        }
    }

    /**
     * Process the items from the Properties into the class.
     * 
     * @param <T> The type of the POJO.
     * @param p The Properties object.
     * @param clazz The class of the POJO.
     * @return The instantiated POJO.
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException 
     */
    private static <T> T reflectFields(Properties p, Class clazz) throws IllegalAccessException, InstantiationException, SecurityException {
        T obj = (T) clazz.newInstance();
        for (String prop : p.stringPropertyNames()) {
            try {
                Field f = clazz.getDeclaredField(prop);
                Class type = f.getType();
                if (type.equals(int.class) || type.equals(Integer.class) ) {
                    f.set(obj, Integer.parseInt(p.getProperty(prop)));
                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    f.set(obj, Boolean.parseBoolean(p.getProperty(prop)));
                } else if (type.equals(long.class) || type.equals(Long.class)) {
                    f.set(obj, Long.parseLong(p.getProperty(prop)));
                } else if (type.equals(double.class) || type.equals(Double.class)) {
                    f.set(obj, Double.parseDouble(p.getProperty(prop)));
                } else if (type.equals(float.class) || type.equals(Float.class)) {
                    f.set(obj, Float.parseFloat(p.getProperty(prop)));
                } else {
                    f.set(obj, p.getProperty(prop));
                }
            } catch (NoSuchFieldException ex) {
                // Ignore this field
            }
        }
        return obj;
    }
}
