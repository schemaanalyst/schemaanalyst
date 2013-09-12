package org.schemaanalyst.dbms;

import org.schemaanalyst.dbms.derby.Derby;
import org.schemaanalyst.dbms.derby.DerbyNetwork;
import org.schemaanalyst.dbms.hsqldb.HSQLDB;
import org.schemaanalyst.dbms.mysql.MySQL;
import org.schemaanalyst.dbms.postgres.Postgres;
import org.schemaanalyst.dbms.sqlite.SQLite;

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
    public void visit(Derby dbms);

    /**
     * Visit a DerbyNetwork DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(DerbyNetwork dbms);

    /**
     * Visit a HSQLDB DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(HSQLDB dbms);

    /**
     * Visit a MySQL DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(MySQL dbms);

    /**
     * Visit a Postgres DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(Postgres dbms);

    /**
     * Visit a SQLite DBMS instance
     * 
     * @param dbms instance
     */
    public void visit(SQLite dbms);
}
