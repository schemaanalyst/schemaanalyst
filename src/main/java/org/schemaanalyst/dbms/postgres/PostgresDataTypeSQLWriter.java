package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.MediumIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TinyIntDataType;
import org.schemaanalyst.sqlwriter.DataTypeSQLWriter;

/**
 * Created by phil on 22/07/2015.
 */
public class PostgresDataTypeSQLWriter extends DataTypeSQLWriter {

    @Override
    public String writeDateTimeDataType(DateTimeDataType type) {
        return "TIMESTAMP";
    }

    @Override
    public String writeDoubleDataType(DoubleDataType type) {
        return "DOUBLE PRECISION";
    }

    @Override
    public String writeMediumIntDataType(MediumIntDataType type) {
        return "INT";
    }

    @Override
    public String writeTinyIntDataType(TinyIntDataType type) {
        return "INT";
    }
}