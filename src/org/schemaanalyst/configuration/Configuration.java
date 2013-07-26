/*
 */
package org.schemaanalyst.configuration;

import java.io.File;
import org.schemaanalyst.util.PropertiesParser;

/**
 *
 * @author Chris J. Wright
 */
class Configuration {
    protected <T> T load(String location, T t) {
        if (new File(location + ".local").exists()) {
            location += ".local";
        }
        return PropertiesParser.parse(location, t);
    }
}
