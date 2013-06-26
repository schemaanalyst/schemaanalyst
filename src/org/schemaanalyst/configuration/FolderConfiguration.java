/*
 */
package org.schemaanalyst.configuration;

import uk.co.chrisjameswright.proptoobj.PropertiesParser;

/**
 *
 * @author Chris J. Wright
 */
public class FolderConfiguration {
    
    private final static String PROPERTIES_LOCATION = "config/folders.properties";
    
    /** The libraries folder. */
    public static String lib_dir;
    
    /** The Java sources folder. */
    public static String src_dir;
    
    /** The build folder containing compiled classes. */
    public static String build_dir;
    
    /** The distribution folder containing the JAR file. */
    public static String dist_dir;
    
    /** The name of the JAR file in the distribution folder. */
    public static String dist_name;
    
    /** The configuration folder. */
    public static String config_dir;
    
    /** The run folder. */
    public static String run_dir;
    
    /** The databases folder, for storing database files. */
    public static String database_dir;
    
    /** The results folder, for storing experiment results. */
    public static String results_dir;
    
    static {
        PropertiesParser.parse(PROPERTIES_LOCATION, FolderConfiguration.class);
    }
    
}
