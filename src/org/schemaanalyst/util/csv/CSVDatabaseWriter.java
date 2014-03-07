/*
 */
package org.schemaanalyst.util.csv;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.ExperimentConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A writer for saving CSVResult objects to a database.
 * </p>
 *
 * @author Chris J. Wright
 */
public class CSVDatabaseWriter extends CSVWriter {

    private static final Logger LOGGER = Logger.getLogger(CSVDatabaseWriter.class.getName());
    /**
     * The database configuration.
     */
    protected final DatabaseConfiguration databaseConfiguration;
    /**
     * The experiment configuration.
     */
    protected final ExperimentConfiguration experimentConfiguration;
    /**
     * Whether initialization was successful.
     */
    private final boolean initialized;
    /**
     * The connection to the database.
     */
    private Connection connection;

    /**
     * Constructs a writer that will connect to a database using the provided
     * details and credentials.
     *
     * @param databaseConfiguration The database configuration
     * @param experimentConfiguration The experiment configuration
     */
    public CSVDatabaseWriter(DatabaseConfiguration databaseConfiguration, ExperimentConfiguration experimentConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        this.experimentConfiguration = experimentConfiguration;
        this.initialized = initializeDatabaseConnection();
        if (!initialized) {
            LOGGER.log(Level.SEVERE, "Failed to open database connection (allowing execution to continue, in case other writers are in use)");
        }
    }

    @Override
    public void write(CSVResult result) {
        if (initialized) {
            try {
                PreparedStatement stmt = connection.prepareStatement(buildStatementString(result));
                setStatementValues(result,stmt);
                stmt.execute();
            } catch (SQLException | NumberFormatException ex) {
                Logger.getLogger(CSVDatabaseWriter.class.getName()).log(Level.SEVERE, "Failed to write CSVResult", ex);
            }
        }
    }

    /** 
     * Initializes a connection to the database used for storing results.
     * 
     * @return True if the connection was successful, otherwise false
     */
    private boolean initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getPostgresDriver());
            LOGGER.log(Level.INFO, "Loading Postgres driver: {0}", databaseConfiguration.getPostgresDriver());
            connection = DriverManager.getConnection(experimentConfiguration.getDatabaseUrl(), experimentConfiguration.getUsername(), experimentConfiguration.getPassword());
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CSVDatabaseWriter.class.getName()).log(Level.SEVERE, "Failed to open database connection", ex);
        }
        return false;
    }

    /**
     * Build the statement string needed to create the prepared statement.
     * 
     * @param result The result being stored
     * @return The statement string
     */
    private String buildStatementString(CSVResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(experimentConfiguration.getTableName());
        builder.append(" (");
        Map<String, Object> values = result.getValues();
        boolean firstKey = true;
        for (String key : values.keySet()) {
            if (!firstKey) {
                builder.append(',');
            } else {
                firstKey = false;
            }
            builder.append(key);
        }
        builder.append(") VALUES (");
        boolean firstValue = true;
        for (int i = 0; i < values.size(); i++) {
            if (!firstValue) {
                builder.append(',');
            } else {
                firstValue = false;
            }
            builder.append('?');
        }
        builder.append(')');
        return builder.toString();
    }

    private void setStatementValues(CSVResult result, PreparedStatement stmt) throws SQLException {
        Map<String, Object> values = result.getValues();
        int i = 1;
        for (String key : values.keySet()) {
            Object value = values.get(key);
            if (value.getClass().equals(Integer.class)) {
                stmt.setInt(i++, (Integer)value);
            } else if (value.getClass().equals(Long.class)) {
                stmt.setLong(i++, (Long)value);
            } else if (value.getClass().equals(Float.class)) {
                stmt.setFloat(i++, (Float)value);
            } else if (value.getClass().equals(Double.class)) {
                stmt.setDouble(i++, (Double)value);
            } else {
                stmt.setString(i++, value.toString());
            }
        }
    }
}
