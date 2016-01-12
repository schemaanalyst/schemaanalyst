/*
 */
package org.schemaanalyst.configuration;

import org.schemaanalyst.util.PropertiesParser;

import java.io.File;

/**
 *
 * @author Chris J. Wright
 */
class Configuration {
    /**
     * <p>
     * Loads the contents of a configuration file into a given instance.
     * </p>
     * 
     * <p>
     * <i>
     * <b>
     * Note:
     * </b>
     * To allow for non-version controlled local copies of 
     * configuration files, if there exists a file at {@code location + 
     * ".local"} this will be loaded instead of the file at {@code location}.
     * </i>
     * </p>
     * 
     * @param <T> The {@link Configuration} type
     * @param location The relative path to the file
     * @param t The {@link Configuration} instance
     * @return The {@link Configuration} instance with content loaded
     */
    protected <T> T load(String location, T t) {
        if (new File(location + ".local").exists()) {
            location += ".local";
        }
        return PropertiesParser.parse(location, t);
    }
}
