/*
 */
package org.schemaanalyst.configuration;

import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the properties describing the layout of the project directories,
 * loaded automatically on demand. This includes the hard-coded path to the
 * 'config' directory, which should be used to bootstrap the loading of other
 * configuration files.
 *
 * @author Chris J. Wright
 */
public class FolderConfiguration {

    private final static String PROPERTIES_LOCATION = "config/folders.properties";
    /**
     * The libraries folder.
     */
    public static String lib_dir;
    /**
     * The Java sources folder.
     */
    public static String src_dir;
    /**
     * The build folder containing compiled classes.
     */
    public static String build_dir;
    /**
     * The distribution folder containing the JAR file.
     */
    public static String dist_dir;
    /**
     * The name of the JAR file in the distribution folder.
     */
    public static String dist_name;
    /**
     * The configuration folder.
     */
    public static String config_dir;
    /**
     * The run folder.
     */
    public static String run_dir;
    /**
     * The databases folder, for storing database files.
     */
    public static String database_dir;
    /**
     * The results folder, for storing experiment results.
     */
    public static String results_dir;
    /**
     * The folder where the original SQL for each schema is located.
     */
    public static String schema_src_dir;
    /**
     * The folder where we write Java code corresponding to each parsed schema.
     */
    public static String casestudy_src_dir;

    static {
        PropertiesParser.parse(PROPERTIES_LOCATION, FolderConfiguration.class);
    }
}
