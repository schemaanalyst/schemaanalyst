package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDbVendor;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.derby.DerbyDBMS;
import org.schemaanalyst.dbms.derby.DerbyNetworkDBMS;
import org.schemaanalyst.dbms.hypersql.HyperSQLDBMS;
import org.schemaanalyst.dbms.mysql.MySQLDBMS;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;

class VendorResolver {

    static EDbVendor resolve(DBMS dbms) {

        class VendorResolverDBMSVisitor implements DBMSVisitor {

            EDbVendor vendor;

            @Override
            public void visit(DerbyDBMS dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            @Override
            public void visit(DerbyNetworkDBMS dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            @Override
            public void visit(HyperSQLDBMS dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            @Override
            public void visit(MySQLDBMS dbms) {
                vendor = EDbVendor.dbvmysql;
            }

            @Override
            public void visit(PostgresDBMS dbms) {
                vendor = EDbVendor.dbvpostgresql;
            }

            @Override
            public void visit(SQLiteDBMS dbms) {
                vendor = EDbVendor.dbvgeneric;
            }
        }

        VendorResolverDBMSVisitor vrv = new VendorResolverDBMSVisitor();
        dbms.accept(vrv);
        return vrv.vendor;
    }
}
