package org.schemaanalyst.dbms.derby;

import java.sql.DriverManager;

import org.schemaanalyst.deprecated.configuration.Configuration;

public class DerbyNetworkDatabaseInteractor extends DerbyDatabaseInteractor {

    /**
     * Constructor.
     */
    public DerbyNetworkDatabaseInteractor() {
        connection = null;
    }

    /**
     * Initialize the connection to the Derby database.
     */
    @Override
    public void initializeDatabaseConnection() {
        // the connection is initially null
        connection = null;

        try {
            // load the Derby driver using reflection
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            if (Configuration.debug) {
                System.out.println();
                System.out.println("JDBC Driver Registered.");
            }


            // create a database url, this time making a connection
            // to the network interface for Derby
            String database = "jdbc:derby://" + Configuration.host
                    + ":" + Configuration.port + "/" + Configuration.database
                    + ";create=true";

            if (Configuration.debug) {
                System.out.println("JDBC Resource.");
                System.out.println(database);
            }

            // create the connection to the database; do not use
            // a user name or a password so that Derby will always
            // create the schema called "APP" which can be accessed
            // even if a CREATE SCHEMA statement has not been issued
            connection = DriverManager.getConnection(database);

            // tell Derby to always persist the data right away
            connection.setAutoCommit(true);

            if (Configuration.debug) {
                if (connection != null) {
                    System.out.println("JDBC Connection Okay.");
                } else {
                    System.out.println("Connection is Null.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}