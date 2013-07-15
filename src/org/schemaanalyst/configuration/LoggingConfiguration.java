/*
 */
package org.schemaanalyst.configuration;

import java.io.File;
import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the parameters relating to debugging/logging, loaded automatically
 * on demand. Makes use of FolderConfiguration to determine the location of the
 * properties file.
 *
 * @author Chris J. Wright
 */
public class LoggingConfiguration {

    private final static String PROPERTIES_LOCATION = FolderConfiguration.config_dir + File.separator + "logging.properties";
    /**
     * 
     */
    private static String level;

    static {
        PropertiesParser.parse(PROPERTIES_LOCATION, LoggingConfiguration.class);
    }
}
