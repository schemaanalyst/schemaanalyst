package org.schemaanalyst.dbms.mysql;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class MySQL extends DBMS {

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        return null; // not implemented yet
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
