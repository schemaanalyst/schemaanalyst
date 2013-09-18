package org.schemaanalyst.dbms;

import org.schemaanalyst.dbms.derby.DerbyDBMS;
import org.schemaanalyst.dbms.hypersql.HyperSQLDBMS;
import org.schemaanalyst.dbms.mysql.MySQLDBMS;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;

/**
 * <p>
 * Visitor interface for subclasses of {@link DBMS}.
 * </p>
 */
public interface DBMSVisitor {

    /**
     * Visit a Derby DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(DerbyDBMS dbms);

    /**
     * Visit a HSQLDB DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(HyperSQLDBMS dbms);

    /**
     * Visit a MySQL DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(MySQLDBMS dbms);

    /**
     * Visit a Postgres DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(PostgresDBMS dbms);

    /**
     * Visit a SQLite DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(SQLiteDBMS dbms);
}
