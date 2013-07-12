package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class Postgres extends DBMS {

    private PostgresDatabaseInteractor databaseInteraction = new PostgresDatabaseInteractor();

    public DatabaseInteractor getDatabaseInteractor() {
        return databaseInteraction;
    }

    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
