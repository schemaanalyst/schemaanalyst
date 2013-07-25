/*
 */
package org.schemaanalyst.configuration;

import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the parameters for connecting to a database.
 *
 * @author Chris J. Wright
 */
public class DatabaseConfiguration {

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
     * The HSQLDB database path.
     */
    private String hsqldb_path;
    /**
     * The HSQLDB driver class name.
     */
    private String hsqldb_driver;
    /**
     * The Derby database path.
     */
    private String derby_path;
    /**
     * The Derby driver class name.
     */
    private String derby_driver;

    /**
     * Construct using the default Properties file location.
     */
    public DatabaseConfiguration() {
        PropertiesParser.parse(PROPERTIES_LOCATION, this);
    }

    /**
     * Construct using the provided Properties file location.
     *
     * @param path The path to Properties file.
     */
    public DatabaseConfiguration(String path) {
        PropertiesParser.parse(path, this);
    }

    /**
     * The DBMS to use.
     * @return the dbms
     */
    public String getDbms() {
        return dbms;
    }

    /**
     * The Postgres port.
     * @return the postgres_port
     */
    public String getPostgresPort() {
        return postgres_port;
    }

    /**
     * The Postgres username.
     * @return the postgres_username
     */
    public String getPostgresUsername() {
        return postgres_username;
    }

    /**
     * The Postgres password.
     * @return the postgres_password
     */
    public String getPostgresPassword() {
        return postgres_password;
    }

    /**
     * The Postgres host.
     * @return the postgres_host
     */
    public String getPostgresHost() {
        return postgres_host;
    }
    
    /**
     * The Postgres database.
     * @return the postgres_database
     */
    public String getPostgresDatabase() {
        return postgres_database;
    }

    /**
     * The Postgres driver class name.
     * @return the postgres_driver
     */
    public String getPostgresDriver() {
        return postgres_driver;
    }

    /**
     * The SQLite database path.
     * @return the sqlite_path
     */
    public String getSqlitePath() {
        return sqlite_path;
    }

    /**
     * The SQLite driver class name.
     * @return the sqlite_driver
     */
    public String getSqliteDriver() {
        return sqlite_driver;
    }

    /**
     * The HSQLDB database path.
     * @return the hsqldb_path
     */
    public String getHsqldbPath() {
        return hsqldb_path;
    }

    /**
     * The HSQLDB driver class name.
     * @return the hsqldb_driver
     */
    public String getHsqldbDriver() {
        return hsqldb_driver;
    }

    /**
     * The Derby database path.
     * @return the derby_path
     */
    public String getDerbyPath() {
        return derby_path;
    }

    /**
     * The Derby driver class name.
     * @return the derby_driver
     */
    public String getDerbyDriver() {
        return derby_driver;
    }
}
