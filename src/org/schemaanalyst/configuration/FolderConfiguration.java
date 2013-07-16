/*
 */
package org.schemaanalyst.configuration;

import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the properties describing the layout of the project directories.
 *
 * @author Chris J. Wright
 */
public class FolderConfiguration {

    private final static String PROPERTIES_LOCATION = "config/folders.properties";
    /**
     * The libraries folder.
     */
    public String lib_dir;
    /**
     * The Java sources folder.
     */
    public String src_dir;
    /**
     * The build folder containing compiled classes.
     */
    public String build_dir;
    /**
     * The distribution folder containing the JAR file.
     */
    public String dist_dir;
    /**
     * The name of the JAR file in the distribution folder.
     */
    public String dist_name;
    /**
     * The configuration folder.
     */
    public String config_dir;
    /**
     * The run folder.
     */
    public String run_dir;
    /**
     * The databases folder, for storing database files.
     */
    public String database_dir;
    /**
     * The results folder, for storing experiment results.
     */
    public String results_dir;
    /**
     * The folder where the original SQL for each schema is located.
     */
    public String schema_src_dir;
    /**
     * The folder where we write Java code corresponding to each parsed schema.
     */
    public String casestudy_src_dir;
    
    /**
     * Construct using the default Properties file location.
     */
    public FolderConfiguration() {
        PropertiesParser.parse(PROPERTIES_LOCATION, this);
    }
    
    /**
     * Construct using the provided Properties file location.
     * @param path The path to Properties file.
     */
    public FolderConfiguration(String path) {
        PropertiesParser.parse(path, this);
    }
}
