/*
 */
package org.schemaanalyst.configuration;

/**
 * Contains the parameters for connecting to a database.
 *
 * @author Chris J. Wright
 */
public class DatabaseConfiguration extends Configuration {

    private final static String PROPERTIES_LOCATION = "config/database.properties";
    /**
     * The DBMS to use.
     */
    private String dbms;
    /**
     * The Postgres port.
     */
    private String postgres_port;
    /**
     * The Postgres username.
     */
    private String postgres_username;
    /**
     * The Postgres password.
     */
    private String postgres_password;
    /**
     * The Postgres host.
     */
    private String postgres_host;
    /**
     * The Postgres database.
     */
    private String postgres_database;
    /**
     * The Postgres driver class name.
     */
    private String postgres_driver;
    /**
     * The SQLite database path.
     */
    private String sqlite_path;
    /**
     * The SQLite driver class name.
     */
    private String sqlite_driver;
    /**
     * Whether to use 'in-memory' mode.
     */
    private boolean sqlite_in_memory;
    /**
     * The HSQLDB username.
     */
    private String hsqldb_username;
    /**
     * The HSQLDB password.
     */
    private String hsqldb_password;
    /**
     * The HSQLDB database path.
     */
    private String hsqldb_path;
    /**
     * The HSQLDB driver class name.
     */
    private String hsqldb_driver;
    /**
     * Whether to use 'in-memory' mode.
     */
    private Boolean hsqldb_in_memory;
    /**
     * The Derby database path.
     */
    private String derby_path;
    /**
     * The Derby driver class name.
     */
    private String derby_driver;
    /**
     * The Derby host.
     */
    private String derby_host;
    /**
     * The Derby port.
     */
    private String derby_port;

    /**
     * Construct using the default Properties file location.
     */
    public DatabaseConfiguration() {
        load(PROPERTIES_LOCATION, this);
    }

    /**
     * Construct using the provided Properties file location.
     *
     * @param path The path to Properties file.
     */
    public DatabaseConfiguration(String path) {
        load(path, this);
    }

    /**
     * The DBMS to use.
     *
     * @return the dbms
     */
    public String getDbms() {
        return dbms;
    }

    /**
     * The Postgres port.
     *
     * @return the postgres_port
     */
    public String getPostgresPort() {
        return postgres_port;
    }

    /**
     * The Postgres username.
     *
     * @return the postgres_username
     */
    public String getPostgresUsername() {
        return postgres_username;
    }

    /**
     * The Postgres password.
     *
     * @return the postgres_password
     */
    public String getPostgresPassword() {
        return postgres_password;
    }

    /**
     * The Postgres host.
     *
     * @return the postgres_host
     */
    public String getPostgresHost() {
        return postgres_host;
    }

    /**
     * The Postgres database.
     *
     * @return the postgres_database
     */
    public String getPostgresDatabase() {
        return postgres_database;
    }

    /**
     * The Postgres driver class name.
     *
     * @return the postgres_driver
     */
    public String getPostgresDriver() {
        return postgres_driver;
    }

    /**
     * The SQLite database path.
     *
     * @return the sqlite_path
     */
    public String getSqlitePath() {
        return sqlite_path;
    }

    /**
     * The SQLite driver class name.
     *
     * @return the sqlite_driver
     */
    public String getSqliteDriver() {
        return sqlite_driver;
    }
    
    /**
     * Whether to use 'in-memory' mode for SQLite.
     * 
     * @return the sqlite_in_memory
     */
    public boolean getSqliteInMemory() {
        return sqlite_in_memory;
    }

    /**
     * The HSQLDB database path.
     *
     * @return the hsqldb_path
     */
    public String getHsqldbPath() {
        return hsqldb_path;
    }

    /**
     * The HSQLDB driver class name.
     *
     * @return the hsqldb_driver
     */
    public String getHsqldbDriver() {
        return hsqldb_driver;
    }
    
    /**
     * Whether to use 'in-memory' mode.
     * 
     * @return the hsqldb_in_memory
     */
    public Boolean getHsqldb_in_memory() {
        return hsqldb_in_memory;
    }

    /**
     * The Derby database path.
     *
     * @return the derby_path
     */
    public String getDerbyPath() {
        return derby_path;
    }

    /**
     * The Derby driver class name.
     *
     * @return the derby_driver
     */
    public String getDerbyDriver() {
        return derby_driver;
    }

    /**
     * The HSQLDB username.
     *
     * @return the hsqldb_username
     */
    public String getHsqldbUsername() {
        return hsqldb_username;
    }

    /**
     * The HSQLDB password.
     *
     * @return the hsqldb_password
     */
    public String getHsqldbPassword() {
        return hsqldb_password;
    }

    /**
     * The Derby host.
     *
     * @return the derby_host
     */
    public String getDerbyHost() {
        return derby_host;
    }

    /**
     * The Derby port.
     *
     * @return the derby_port
     */
    public String getDerbyPort() {
        return derby_port;
    }
}
