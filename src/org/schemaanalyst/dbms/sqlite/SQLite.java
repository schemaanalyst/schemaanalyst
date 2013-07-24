package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class SQLite extends DBMS {

    private SQLiteDatabaseInteractor databaseInteraction = new SQLiteDatabaseInteractor();

    public SQLite() {
        sqlWriter = new SQLiteSQLWriter();
    }

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        return databaseInteraction;
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
