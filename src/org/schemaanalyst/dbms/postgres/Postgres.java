package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class Postgres extends DBMS {

    private PostgresDatabaseInteractor databaseInteraction = new PostgresDatabaseInteractor();

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        return databaseInteraction;
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
