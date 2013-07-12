/*
 */
package experiment.results;

/**
 * Encoding of the possible file writing modes for a report writer.
 *
 * @author chris
 */
public enum WriteOption {

    /**
     * Append to an existing file (or create a file if one does not exist).
     */
    APPEND,
    /**
     * Replace an existing file (or create a file if one does not exist).
     */
    REPLACE,
    /**
     * Require a new file (fails if one already exists).
     */
    NEW_FILE
};
