/*
 */
package experiment.results;

/**
 * An abstract class for possible types of report writer, which assumes all
 * subclasses will involve a 'header' row followed by one or more data rows. It
 * also assumes to all subclasses will be writing to some type of file, and
 * hence will require a path to the write location.
 *
 * @author chris
 */
public abstract class ReportWriter {

    protected String path;
    private boolean wroteHeader;

    /**
     * Constructor to provide parameters important to all types of ReportWriter.
     *
     * @param path The path to write to
     */
    public ReportWriter(String path) {
        this.path = path;
    }

    /**
     * Write the header, if necessary, and data to the target location.
     *
     * @param report The report to write
     */
    public void write(Report report) {
        if (!wroteHeader) {
            wroteHeader = true;
            writeHeader(report);
        }
        writeData(report);
    }

    /**
     * Close any resources the writer may have open, such as file handles.
     */
    public abstract void close();

    /**
     * Write the header data for the report.
     *
     * @param report The report
     */
    protected abstract void writeHeader(Report report);

    /**
     * Write the value data for the report.
     *
     * @param report The report
     */
    protected abstract void writeData(Report report);
}
