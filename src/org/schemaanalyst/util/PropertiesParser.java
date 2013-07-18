/*
 */
package org.schemaanalyst.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public static <T> T parse(String filepath, Class<?> clazz) {
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
    @SuppressWarnings("unchecked")
    public static <T> T parse(File file, Class<?> clazz) {
        try {
            T obj = (T) clazz.newInstance();
            return parse(file, obj);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Failed to parse properties file to object", ex);
        }
    }
    
    /**
     * Parses a file at the given path into an existing object.
     * 
     * @param filepath The path to the Properties file.
     * @param object The POJO to parse values into.
     * @return The POJO.
     */
    public static <T> T parse(String filepath, T object) {
        return parse(new File(filepath), object);
    }
    
    /**
     * Parses a file at the given path into an existing object.
     * 
     * @param file The properties file.
     * @param object The POJO to parse values into.
     * @return The POJO.
     */
    public static <T> T parse(File file, T object) {
        try (FileReader reader = new FileReader(file)) {
            Properties p = new Properties();
            p.load(reader);
            return reflectFields(p, object);
        } catch (IOException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException("Failed to parse properties file to object", ex);
        }
    }
    
    /**
     * Process the items from the Properties into the POJO.
     * 
     * @param <T> The type of the POJO.
     * @param p The Properties object.
     * @param object The POJO.
     * @return The populated POJO.
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException 
     */
    private static <T> T reflectFields(Properties p, T object)throws IllegalAccessException, InstantiationException, SecurityException {
        for (String prop : p.stringPropertyNames()) {
            try {
                Field f = object.getClass().getDeclaredField(prop);
                boolean wasAccessible = f.isAccessible();
                if (!wasAccessible) {
                    f.setAccessible(true);
                }
                Class<?> type = f.getType();
                if (type.equals(int.class) || type.equals(Integer.class) ) {
                    f.set(object, Integer.parseInt(p.getProperty(prop)));
                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    f.set(object, Boolean.parseBoolean(p.getProperty(prop)));
                } else if (type.equals(long.class) || type.equals(Long.class)) {
                    f.set(object, Long.parseLong(p.getProperty(prop)));
                } else if (type.equals(double.class) || type.equals(Double.class)) {
                    f.set(object, Double.parseDouble(p.getProperty(prop)));
                } else if (type.equals(float.class) || type.equals(Float.class)) {
                    f.set(object, Float.parseFloat(p.getProperty(prop)));
                } else {
                    f.set(object, p.getProperty(prop));
                }
                if (!wasAccessible) {
                    f.setAccessible(false);
                }
            } catch (NoSuchFieldException ex) {
                // Ignore this field
            }
        }
        return object;
    }
}
