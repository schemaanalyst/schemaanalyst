/*
 */
package org.schemaanalyst.configuration;

import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the parameters relating to debugging/logging.
 *
 * @author Chris J. Wright
 */
public class LoggingConfiguration {

    private final static String PROPERTIES_LOCATION = "config/logging.properties";
    
    /**
     * The logging level to use.
     */
    private String log_level;

    /**
     * Construct using the default Properties file location.
     */
    public LoggingConfiguration() {
        PropertiesParser.parse(PROPERTIES_LOCATION, this);
    }

    /**
     * Construct using the provided Properties file location.
     * @param path The path to Properties file.
     */
    public LoggingConfiguration(String path) {
        PropertiesParser.parse(path, this);
    }

    /**
     * The logging level to use.
     * @return the log_level
     */
    public String getLogLevel() {
        return log_level;
    }
}
