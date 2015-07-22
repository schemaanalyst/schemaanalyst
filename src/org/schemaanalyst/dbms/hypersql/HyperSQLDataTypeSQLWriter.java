
package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.sqlrepresentation.datatype.MediumIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlwriter.DataTypeSQLWriter;

/**
 * @author Chris J. Wright
 */
public class HyperSQLDataTypeSQLWriter extends DataTypeSQLWriter {

    @Override
    public String writeTextDataType(TextDataType type) {
        return "LONGVARCHAR";
    }

    @Override
    public String writeMediumIntDataType(MediumIntDataType type) {
        return "INT";
    }

    @Override
    public String writeVarCharDataType(VarCharDataType type) {
        int length = 1000;
        if (type.getLength() != null) {
            length = type.getLength();
        }
        return "VARCHAR(" + length + ")";
    }
}
