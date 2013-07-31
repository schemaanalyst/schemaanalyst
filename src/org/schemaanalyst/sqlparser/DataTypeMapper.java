package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TParseTreeNode;
import gudusoft.gsqlparser.nodes.TTypeName;

import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TinyIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

class DataTypeMapper {

    public DataTypeMapper() {
    }

    // REFER TO the JavaDocs for TTypeName
    // http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html	
    public DataType getDataType(TTypeName dataType, TParseTreeNode node) {

        // used in case of bugs with switch type
        // String typeString = dataType.toString();

        switch (dataType.getDataType()) {

            // *** BOOLEAN *** 
            case bool_t:
            case boolean_t:
                return new BooleanDataType();

            // *** CHARACTER STRING *** 
            case char_t:
                Integer length = getLength(dataType);
                return (length == null)
                        ? new SingleCharDataType()
                        : new CharDataType(length);

            case varchar_t:
                return new VarCharDataType(getLength(dataType));

            // [TODO] New data type required to handle nchar/nvarchar?				

            // text 
            // [TODO] New data type required to handle longtext?
            case text_t:
                // case longtext_t:	
                return new TextDataType();

            // *** NUMERIC *** 	
            case dec_t:
                return new DecimalDataType(getPrecision(dataType), getScale(dataType));

            case int_t:
                return new IntDataType();

            case smallint_t:
                return new SmallIntDataType();

            case tinyint_t:
                return new TinyIntDataType();

            // numeric
            case numeric_t:
                return new NumericDataType(getPrecision(dataType), getScale(dataType));

            case real_t:
                return new RealDataType();

            // *** TEMPORAL *** 
            case date_t:
                return new DateDataType();

            case datetime_t:
                return new DateTimeDataType();

            case time_t:
                return new TimeDataType();

            case timestamp_t:
                return new TimestampDataType();

            default:
                // Data type not supported
                throw new UnsupportedSQLException(dataType, node);
        }
    }

    protected Integer getArgument(TConstant argument) {
        return argument == null ? null : Integer.valueOf(argument.toString());
    }

    protected Integer getLength(TTypeName dataType) {
        return getArgument(dataType.getLength());
    }

    protected Integer getPrecision(TTypeName dataType) {
        return getArgument(dataType.getPrecision());
    }

    protected Integer getScale(TTypeName dataType) {
        return getArgument(dataType.getScale());
    }
}
