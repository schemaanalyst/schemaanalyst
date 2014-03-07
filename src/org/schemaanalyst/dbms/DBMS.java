package org.schemaanalyst.dbms;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * <p>
 * Contains the various objects relating to interacting with a specific DBMS.
 * </p>
 */
public abstract class DBMS {

    protected SQLWriter sqlWriter;
    protected ValueFactory valueFactory;

    public DBMS() {
        sqlWriter = new SQLWriter();
        valueFactory = new ValueFactory();
    }

    /**
     * Gets the name of this DBMS
     */
    public abstract String getName();
    
    /**
     * Get the {@link SQLWriter} instance for this DBMS.
     * 
     * @return The SQLWriter
     */
    public SQLWriter getSQLWriter() {
        return sqlWriter;
    }

    /**
     * Get the {@link ValueFactory} instance for this DBMS.
     * 
     * @return The ValueFactory
     */
    public ValueFactory getValueFactory() {
        return valueFactory;
    }

    /**
     * Get the {@link DatabaseInteractor} instance for this DBMS.
     * 
     * @param databaseName The name of the database to connect to
     * @param databaseConfiguration The database configuration instance
     * @param locationConfiguration The location configuration instance
     * @return The DatabaseInteractor
     */
    public abstract DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration);

    /**
     * Method to accept a {@link DBMSVisitor}.
     * 
     * @param visitor The DBMSVisitor
     */
    public abstract void accept(DBMSVisitor visitor);
}
