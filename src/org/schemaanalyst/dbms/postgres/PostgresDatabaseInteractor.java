package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DatabaseInteractor;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Contains the various objects relating to interacting with a Postgres DBMS.
 * </p>
 */
public class PostgresDatabaseInteractor extends DatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(PostgresDatabaseInteractor.class.getName());

    /**
     * Constructor.
     */
    public PostgresDatabaseInteractor(DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        super(databaseConfiguration, locationConfiguration);
    }

    /**
     * Initialize the connection to the Postgres database.
     */
    @Override
    public void initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getPostgresDriver());
            LOGGER.log(Level.FINE, "Loading Postgres driver: {0}", databaseConfiguration.getPostgresDriver());

            // note that right now the Postgres database management
            // system is hosting the "database" in the default
            // database which is called "postgres" ; we are not
            // creating a separate database because this is more
            // complex and does not provide any special features as
            // long as we name the mutant tables correctly
            String databaseUrl = "jdbc:postgresql://"
                    + databaseConfiguration.getPostgresHost() + ":"
                    + databaseConfiguration.getPostgresPort() + "/"
                    + databaseConfiguration.getPostgresDatabase();
            LOGGER.log(Level.FINE, "JDBC Connection URL: {0}", databaseUrl);
            connection =
                    DriverManager.
                    getConnection(databaseUrl,
                    databaseConfiguration.getPostgresUsername(),
                    databaseConfiguration.getPostgresPassword());

            // tell Postgres to always auto-commit results to the database
            connection.setAutoCommit(true);

            // show that we have already initialized the database
            initialized = true;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int getTableCount() {
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public'")) {
                resultSet.next();
                int result = resultSet.getInt(1);
                return result;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public DatabaseInteractor duplicate() {
        return new PostgresDatabaseInteractor(databaseConfiguration, locationConfiguration);
    }
}