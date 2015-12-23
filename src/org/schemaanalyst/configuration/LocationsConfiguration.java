/*
 */
package org.schemaanalyst.configuration;

/**
 * Contains the properties describing the layout of the project directories.
 *
 * @author Chris J. Wright
 */
public class LocationsConfiguration extends Configuration {

    private final static String PROPERTIES_LOCATION = "config/locations.properties";
    /**
     * The libraries folder.
     */
    private String lib_dir;
    /**
     * The Java sources folder.
     */
    private String src_dir;
    /**
     * The build folder containing compiled classes.
     */
    private String build_dir;
    /**
     * The distribution folder containing the JAR file.
     */
    private String dist_dir;
    /**
     * The name of the JAR file in the distribution folder.
     */
    private String dist_name;
    /**
     * The configuration folder.
     */
    private String config_dir;
    /**
     * The run folder.
     */
    private String run_dir;
    /**
     * The databases folder, for storing database files.
     */
    private String database_dir;
    /**
     * The results folder, for storing experiment results.
     */
    private String results_dir;
    /**
     * The folder where the original SQL for each schema is located.
     */
    private String schema_src_dir;
    /**
     * The folder where we write Java code corresponding to each parsed schema.
     */
    private String case_study_src_dir;
    /**
     * The package where we write Java code corresponding to each parsed schema.
     */
    private String case_study_package;
    /**
     * The folder where we write Java code corresponding to the complete test suite.
     */
    private String test_src_dir;
    /**
     * The package where we write Java code corresponding to the complete test suite.
     */
    private String test_package;
    /**
     * Construct using the default Properties file location.
     */
    public LocationsConfiguration() {
        load(PROPERTIES_LOCATION, this);
    }

    /**
     * Construct using the provided Properties file location.
     *
     * @param path The path to Properties file.
     */
    public LocationsConfiguration(String path) {
        load(path, this);
    }

    /**
     * The libraries folder.
     *
     * @return the lib_dir
     */
    public String getLibDir() {
        return lib_dir;
    }

    /**
     * The Java sources folder.
     *
     * @return the src_dir
     */
    public String getSrcDir() {
        return src_dir;
    }

    /**
     * The build folder containing compiled classes.
     *
     * @return the build_dir
     */
    public String getBuildDir() {
        return build_dir;
    }

    /**
     * The distribution folder containing the JAR file.
     *
     * @return the dist_dir
     */
    public String getDistDir() {
        return dist_dir;
    }

    /**
     * The name of the JAR file in the distribution folder.
     *
     * @return the dist_name
     */
    public String getDistName() {
        return dist_name;
    }

    /**
     * The configuration folder.
     *
     * @return the config_dir
     */
    public String getConfigDir() {
        return config_dir;
    }

    /**
     * The run folder.
     *
     * @return the run_dir
     */
    public String getRunDir() {
        return run_dir;
    }

    /**
     * The databases folder, for storing database files.
     *
     * @return the database_dir
     */
    public String getDatabaseDir() {
        return database_dir;
    }

    /**
     * The results folder, for storing experiment results.
     *
     * @return the results_dir
     */
    public String getResultsDir() {
        return results_dir;
    }

    /**
     * The folder where the original SQL for each schema is located.
     *
     * @return the schema_src_dir
     */
    public String getSchemaSrcDir() {
        return schema_src_dir;
    }

    /**
     * The folder where we write Java code corresponding to each parsed schema.
     *
     * @return the case_study_src_dir
     */
    public String getCaseStudySrcDir() {
        return case_study_src_dir;
    }

    /**
     * The package where we write Java code corresponding to each parsed schema.
     *
     * @return the case_study_package
     */
    public String getCaseStudyPackage() {
        return case_study_package;
    }
    
    /**
     * The folder where we write Java code corresponding to each parsed schema.
     *
     * @return the test_src_dir
     */
    public String getTestSrcDir() {
        return test_src_dir;
    }

    /**
     * The package where we write Java code corresponding to each parsed schema.
     *
     * @return the test_package
     */
    public String getTestPackage() {
        return test_package;
    }    
}
