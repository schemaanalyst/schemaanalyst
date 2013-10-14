/*
 */
package org.schemaanalyst.util.csv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.ExperimentConfiguration;

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
                Map<String, String> values = result.getValues();
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + experimentConfiguration.getTableName() + " "
                        + "(technique,dbms,casestudy,trial,totaltime,scorenumerator,scoredenominator,mutationpipeline,threads,dropstime,createstime,insertstime,mutationtime,paralleltime) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, values.get("technique"));
                stmt.setString(2, values.get("dbms"));
                stmt.setString(3, values.get("casestudy"));
                stmt.setInt(4, Integer.valueOf(values.get("trial")));
                stmt.setInt(5, Integer.valueOf(values.get("totaltime")));
                stmt.setInt(6, Integer.valueOf(values.get("scorenumerator")));
                stmt.setInt(7, Integer.valueOf(values.get("scoredenominator")));
                stmt.setString(8, values.get("mutationpipeline"));
                stmt.setInt(9, Integer.valueOf(values.get("threads")));
                stmt.setInt(10, Integer.valueOf(values.get("dropstime")));
                stmt.setInt(11, Integer.valueOf(values.get("createstime")));
                stmt.setInt(12, Integer.valueOf(values.get("insertstime")));
                stmt.setInt(13, Integer.valueOf(values.get("mutationtime")));
                stmt.setInt(14, Integer.valueOf(values.get("paralleltime")));
                stmt.execute();
            } catch (SQLException | NumberFormatException ex) {
                Logger.getLogger(CSVDatabaseWriter.class.getName()).log(Level.SEVERE, "Failed to write CSVResult", ex);
            }
        }
    }

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
}
