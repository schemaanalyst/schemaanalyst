/*
 */

package org.schemaanalyst.util.csv;

/**
 * <p>
 * Parent class for classes that can write a {@link CSVResult} to some output.
 * </p>
 *
 * @author Chris J. Wright
 */
public abstract class CSVWriter {

    /**
     * Write a CSVResult object to some output.
     *
     * @param result The content to write.
     */
    public abstract void write(CSVResult result);

    public void write(Iterable<CSVResult> results) {
        for (CSVResult result : results) {
            write(result);
        }
    }
}
