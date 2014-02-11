package org.schemaanalyst.configuration;

import org.schemaanalyst.util.DataCapturer;

/**
 * Contains the properties describing which data capturers should be enabled.
 * 
 * @author Chris J. Wright
 */
public class DataCapturerConfiguration extends Configuration {

    private final static String PROPERTIES_LOCATION = "config/capture.properties";

    private String enabledCapturers;
    private String output;

    public DataCapturerConfiguration() {
        this(PROPERTIES_LOCATION);
    }

    public DataCapturerConfiguration(String location) {
        load(location, this);
        DataCapturer.instantiate(this);
    }

    public String getEnabledCapturers() {
        return enabledCapturers;
    }

    public String getOutput() {
        return output;
    }

}
