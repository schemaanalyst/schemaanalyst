/*
 */
package org.schemaanalyst.configuration;

import java.io.File;
import org.schemaanalyst.util.PropertiesParser;

/**
 * Contains the parameters for connecting to a database, loaded automatically on
 * demand. Makes use of FolderConfiguration to determine the location of the
 * properties file.
 *
 * @author Chris J. Wright
 */
public class DatabaseConfiguration {

    private final static String PROPERTIES_LOCATION = FolderConfiguration.config_dir + File.separator + "folders.properties";
    /**
     * The host address of the DBMS that hosts the database.
     */
    private static String db_host;
    /**
     * The port of the DBMS that hosts the database.
     */
    private static String db_port;
    /**
     * The name of the DBMS hosting the database. Supported values are
     * 'Postgres', 'SQLite' and 'Hsqldb', and are case sensitive.
     */
    private static String db_dbms;
    /**
     * The username to connect to the DBMS hosting the database.
     */
    private static String db_username;
    /**
     * The password to connect to the DBMS hosting the database.
     */
    private static String db_password;

    static {
        PropertiesParser.parse(PROPERTIES_LOCATION, DatabaseConfiguration.class);
    }
}
