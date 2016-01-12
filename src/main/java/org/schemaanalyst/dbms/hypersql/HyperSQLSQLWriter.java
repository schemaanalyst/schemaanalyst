
package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 *
 * @author Chris J. Wright
 */
public class HyperSQLSQLWriter extends SQLWriter {

    @Override
    protected void instanitateSubWriters() {
        super.instanitateSubWriters();
        dataTypeSQLWriter = new HyperSQLDataTypeSQLWriter();
    }
    
}
