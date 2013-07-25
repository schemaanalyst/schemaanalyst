package org.schemaanalyst.dbms;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.sqlwriter.CellSQLWriter;

public abstract class DBMS {

    private SQLWriter sqlWriter;
    private ValueFactory valueFactory;
    private CellSQLWriter valueToStringConverter;

    public DBMS() {
        sqlWriter = new SQLWriter();
        valueFactory = new ValueFactory();
        valueToStringConverter = new CellSQLWriter();
    }

    public SQLWriter getSQLWriter() {
        return sqlWriter;
    }

    public ValueFactory getValueFactory() {
        return valueFactory;
    }

    public CellSQLWriter getValueStringConverter() {
        return valueToStringConverter;
    }

    public abstract DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration);

    public abstract void accept(DBMSVisitor visitor);
}
