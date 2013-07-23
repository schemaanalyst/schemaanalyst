package org.schemaanalyst.dbms;

import java.util.ArrayList;
import java.util.List;

public class DBMSFactory {

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
    
    public static List<String> getDBMSChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("Postgres");
        choices.add("SQLite");
        return choices;
    }
}
