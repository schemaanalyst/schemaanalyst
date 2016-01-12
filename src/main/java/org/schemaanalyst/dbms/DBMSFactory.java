package org.schemaanalyst.dbms;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Factory class that can retrieve a new {@link DBMS} instance given the name.
 * </p>
 */
public class DBMSFactory {

    /**
     * Creates a new {@link DBMS} instance of the type chosen given by the name.
     * 
     * @param name The DBMS name
     * @return The new DBMS instance
     */
    public static DBMS instantiate(String name) {
        if (!getDBMSChoices().contains(name)) {
            throw new DBMSException("Unknown DBMS: \"" + name + "\" is unrecognised or not supported");
        }

        try {
            String dbmsClassName = "org.schemaanalyst.dbms." + name.toLowerCase() + "." + name + "DBMS";
            Class<?> dbmsClass = Class.forName(dbmsClassName);
            return (DBMS) dbmsClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a list of {@link DBMS} valid choices.
     * 
     * @return The list of DBMS choices
     */
    public static List<String> getDBMSChoices() {
        // TODO: automatically generate this list!
        List<String> choices = new ArrayList<>();
        choices.add("Postgres");
        choices.add("SQLite");
        choices.add("HyperSQL");
        return choices;
    }
}
