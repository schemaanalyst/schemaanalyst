package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class Derby extends DBMS {

    private SQLWriter sqlWriter = new DerbySQLWriter();
    private DerbyDatabaseInteractor databaseInteraction = new DerbyDatabaseInteractor();

    @Override
    public SQLWriter getSQLWriter() {
        return sqlWriter;
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
