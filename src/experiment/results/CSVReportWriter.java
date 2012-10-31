/*
 */
package experiment.results;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

/**
 * A ReportWriter that uses CSV as the output format.
 *
 * @author chris
 */
public class CSVReportWriter extends ReportWriter {

    private final String separator;
    @XStreamOmitField // Regrettably needed to prevent serialisation problems
    private PrintWriter writer = null;
    private Iterable<String> header;
    private WriteOption writeOption;

    /**
     * Constructor that provides the values required to write data in the CSV
     * format, with a comma and space used as a separator.
     *
     * @param path The path to write to
     * @param overwrite Whether to overwrite an existing file, if found
     */
    public CSVReportWriter(String path, WriteOption writeOption) {
        this(path, ", ", writeOption);
    }

    /**
     * Constructor that provides the values required to write data in the CSV
     * format, with the provided parameter used as a separator.
     *
     * @param path The path to write to
     * @param separator The separator to use
     * @param overwrite Whether to overwrite an existing file, if found
     */
    public CSVReportWriter(String path, String separator, WriteOption writeOption) {
        super(path);
        this.separator = separator;
        this.writeOption = writeOption;
    }

    @Override
    protected void writeHeader(Report report) {
        header = report.getHeader();
        String csvData = ReportUtilities.convertIterableToCSV(header, separator);
        print(csvData);
    }

    @Override
    protected void writeData(Report report) {
        ArrayList<String> values = new ArrayList<>();
        Map<String, String> data = report.getValues();
        for (String column : header) {
            String value = data.get(column);
            if (value == null) {
                throw new IllegalArgumentException("Missing column '" + column
                        + "' in row when writing CSV data. Interupting execution"
                        + " to avoid output of corrupt or misleading data.");
            } else {
                values.add(value);
            }
        }
        String csvData = ReportUtilities.convertIterableToCSV(values, separator);
        print(csvData);
    }

    /**
     * Initialises the output writer, if necessary. The writer is configured to
     * append to the existing file.
     */
    private void initialiseWriter() {
        if (writer == null) {
            checkWriteOption();
            try {
                writer = new PrintWriter(new BufferedWriter(
                        new FileWriter(path, true)), false); // Append enabled
            } catch (IOException ioEx) {
                throw new RuntimeException("Failed to initialise the output"
                        + "writer", ioEx);
            }
        }
    }

    /**
     * Makes checks relating to the write option of the writer.
     */
    private void checkWriteOption() {
        File target = new File(path);
        switch (writeOption) {
            case NEW_FILE:
                if (target.isFile() && target.exists()) {
                    throw new RuntimeException("File '" + path + "' already exists, "
                            + "and writer is configured to only write to a new "
                            + "file. Remove this file, or change the configuration");
                }
                break;
            case REPLACE:
                if (target.isFile() && target.exists()) {
                    try {
                        Files.delete(target.toPath());
                    } catch (IOException ioEx) {
                        throw new RuntimeException("File '" + path + "' already "
                                + "exists and writer is configured to replace "
                                + "this, but it could not be deleted. Resolve "
                                + "this problem, or change the configuration", ioEx);
                    }
                }
                break;
        }
    }

    /**
     * Writes the given data to the output writer, including an EOL character,
     * and flushes.
     *
     * @param data The line to write to file
     */
    private void print(String data) {
        initialiseWriter();
        writer.println(data);
        writer.flush();
    }

    @Override
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
