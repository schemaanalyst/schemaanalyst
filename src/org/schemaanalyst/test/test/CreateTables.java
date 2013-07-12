package org.schemaanalyst.test.test;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

import plume.*;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.Configuration;

public class CreateTables {

    public static final String CREATE_TABLES = "CreateTables/";

    /**
     * The main method for testing the CREATE TABLE statements that are the case
     * studies.
     */
    public static void main(String[] args) {

        // extract all of the database configurations
        Configuration configuration = new Configuration();
        Options options = new Options("CreateTables [options]", configuration);
        options.parse_or_usage(args);

        // print the debugging information
        if (Configuration.debug) {
            System.out.println(options.settings());
        }

        // print the help screen to see the commands
        if (Configuration.help) {
            options.print_usage();
        }

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = constructDatabase(Configuration.type);

        // initialize the connection to the real relational database
        DatabaseInteractor databaseInteraction = database.getDatabaseInteractor();

        // read in the CREATE TABLE statement(s) from the file system
        StringBuffer createTables = new StringBuffer();
        ArrayList<String> createTablesList = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(Configuration.project + CREATE_TABLES
                    + Configuration.createtables));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                createTables.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (Configuration.debug) {
            System.out.println("Complete CREATE TABLE Listing.");
            System.out.println(createTables.toString());
        }

        // tokenize the string based on the semicolon divider
        StringTokenizer tokenizer = new StringTokenizer(createTables.toString(), ";");
        while (tokenizer.hasMoreTokens()) {
            String createTable = tokenizer.nextToken();
            if (Configuration.debug) {
                System.out.println("A single CREATE TABLE.");
                System.out.println(createTable + ";");
            }
            createTablesList.add(createTable + ";");
        }

        // create the schema inside of the real database
        for (String statement : createTablesList) {
            databaseInteraction.executeUpdate(statement);
        }

    }

    /**
     * Create the Database instanced based on the name provided.
     */
    public static DBMS constructDatabase(String databaseType) {
        try {
            return (DBMS) Class.forName(databaseType).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not construct database type \"" + databaseType + "\"");
        }
    }
}