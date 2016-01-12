package org.schemaanalyst.util;

import org.schemaanalyst.configuration.DataCapturerConfiguration;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris J. Wright
 */
public class DataCapturer {

    private static final Logger LOGGER = Logger.getLogger(DataCapturer.class.getName());
    private static DataCapturer instance;

    private final CSVFileWriter writer;
    private final Set<String> capturers;

    public DataCapturer(DataCapturerConfiguration configuration) {
        writer = new CSVFileWriter(configuration.getOutput());
        capturers = new HashSet<>(Arrays.asList(configuration.getEnabledCapturers().split(",")));
    }

    public static void instantiate(DataCapturerConfiguration configuration) {
        instance = new DataCapturer(configuration);
    }

    public static void capture(String source, String key, String value) {
        if (instance != null) {
            writeResult(source, key, value);
        } else {
            LOGGER.log(Level.FINE, MessageFormat.format("DataCapturer has not been instantiated and cannot be used to capture value: ''{0},{1},{2}''", source, key, value));
        }
    }

    private static void writeResult(String source, String key, String value) {
        if (instance.capturers.contains(source)) {
            CSVResult result = new CSVResult();
            result.addValue("source", source);
            result.addValue("key", key);
            result.addValue("value", value);
            instance.writer.write(result);
        }
    }
}
