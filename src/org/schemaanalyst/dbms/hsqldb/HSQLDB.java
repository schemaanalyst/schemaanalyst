package org.schemaanalyst.dbms.hsqldb;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class HSQLDB extends DBMS {

    private HSQLDBDatabaseInteractor databaseInteraction = new HSQLDBDatabaseInteractor();

    public DatabaseInteractor getDatabaseInteractor() {
        return databaseInteraction;
    }

    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
