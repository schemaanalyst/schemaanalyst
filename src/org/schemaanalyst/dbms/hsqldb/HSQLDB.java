package org.schemaanalyst.dbms.hsqldb;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class HSQLDB extends DBMS {

    private HSQLDBDatabaseInteractor databaseInteraction = new HSQLDBDatabaseInteractor();

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        return databaseInteraction;
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
