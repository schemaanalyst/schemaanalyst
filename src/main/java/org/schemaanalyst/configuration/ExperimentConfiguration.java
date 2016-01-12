/*
 */

package org.schemaanalyst.configuration;

/**
 * Contains the properties describing how to connect to the experimental 
 * results database.
 *
 * @author Chris J. Wright
 */
public class ExperimentConfiguration extends Configuration {

    private final static String PROPERTIES_LOCATION = "config/experiment.properties";
    private String databaseUrl;
    private String tableName;
    private String username;
    private String password;

    /**
     * @return the databaseUrl
     */
    public String getDatabaseUrl() {
        return databaseUrl;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public ExperimentConfiguration() {
        load(PROPERTIES_LOCATION, this);
    }

    public ExperimentConfiguration(String path) {
        load(path, this);
    }
}
