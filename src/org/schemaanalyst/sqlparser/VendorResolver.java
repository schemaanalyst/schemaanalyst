package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDbVendor;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.derby.Derby;
import org.schemaanalyst.dbms.derby.DerbyNetwork;
import org.schemaanalyst.dbms.hsqldb.HSQLDB;
import org.schemaanalyst.dbms.mysql.MySQL;
import org.schemaanalyst.dbms.postgres.Postgres;
import org.schemaanalyst.dbms.sqlite.SQLite;

class VendorResolver {

    static EDbVendor resolve(DBMS dbms) {

        class VendorResolverDBMSVisitor implements DBMSVisitor {

            EDbVendor vendor;

            public void visit(Derby dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            public void visit(DerbyNetwork dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            public void visit(HSQLDB dbms) {
                vendor = EDbVendor.dbvgeneric;
            }

            public void visit(MySQL dbms) {
                vendor = EDbVendor.dbvmysql;
            }

            public void visit(Postgres dbms) {
                vendor = EDbVendor.dbvpostgresql;
            }

            public void visit(SQLite dbms) {
                vendor = EDbVendor.dbvgeneric;
            }
        }

        VendorResolverDBMSVisitor vrv = new VendorResolverDBMSVisitor();
        dbms.accept(vrv);
        return vrv.vendor;
    }
}
