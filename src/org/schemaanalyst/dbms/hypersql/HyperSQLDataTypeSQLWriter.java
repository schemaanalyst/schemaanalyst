
package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlwriter.DataTypeSQLWriter;

/**
 * @author Chris J. Wright
 */
public class HyperSQLDataTypeSQLWriter extends DataTypeSQLWriter {

    @Override
    public String writeTextDataType(TextDataType type) {
        return "LONGVARCHAR";
    }
    
}
