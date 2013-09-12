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
     * @throws ClassNotFoundException If the DBMS class name is not valid
     * @throws InstantiationException If the DBMS class cannot be instantiated
     * @throws IllegalAccessException If the DBMS class cannot be instantiated
     */
    public static DBMS instantiate(String name) throws ClassNotFoundException,
                                                       InstantiationException,
                                                       IllegalAccessException {
        if (!getDBMSChoices().contains(name)) {
            throw new UnknownDBMSException("DBMS \"" + name + "\" is unrecognised or not supported");
        }
        
        String dbmsClassName =
                "org.schemaanalyst.dbms."
                + name.toLowerCase() + "." + name;

        Class<?> dbmsClass = Class.forName(dbmsClassName);
        DBMS dbms = (DBMS) dbmsClass.newInstance();
        return dbms;
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
        return choices;
    }
}
