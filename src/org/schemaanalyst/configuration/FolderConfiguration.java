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
    
    public static String lib_dir;
    public static String src_dir;
    public static String build_dir;
    public static String dist_dir;
    public static String dist_name;
    public static String config_dir;
    public static String run_dir;
    public static String database_dir;
    
    static {
        initialise();
    }
    
    public static void initialise() {
        PropertiesParser.parse(PROPERTIES_LOCATION, FolderConfiguration.class);
    }
    
}
