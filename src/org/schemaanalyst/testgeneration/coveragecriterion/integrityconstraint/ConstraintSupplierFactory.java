package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.derby.DerbyDBMS;
import org.schemaanalyst.dbms.hypersql.HyperSQLDBMS;
import org.schemaanalyst.dbms.mysql.MySQLDBMS;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;

/**
 * Created by phil on 19/08/2014.
 */
public class ConstraintSupplierFactory {

    public static ConstraintSupplier instantiateConstraintSupplier(final DBMS dbms) {
        return new DBMSVisitor() {
            ConstraintSupplier constraintSupplier;

            ConstraintSupplier instantiateConstraintSupplier() {
                dbms.accept(this);
                return constraintSupplier;
            }

            @Override
            public void visit(DerbyDBMS dbms) {
                constraintSupplier = new ICMinimalConstraintSupplier();
            }

            @Override
            public void visit(HyperSQLDBMS dbms) {
                constraintSupplier = new ICMinimalConstraintSupplier();
            }

            @Override
            public void visit(MySQLDBMS dbms) {
                constraintSupplier = new ICMinimalConstraintSupplier();
            }

            @Override
            public void visit(PostgresDBMS dbms) {
                constraintSupplier = new ICMinimalConstraintSupplier();
            }

            @Override
            public void visit(SQLiteDBMS dbms) {
                constraintSupplier = new SQLiteConstraintSupplier();

            }
        }.instantiateConstraintSupplier();
    }
}
