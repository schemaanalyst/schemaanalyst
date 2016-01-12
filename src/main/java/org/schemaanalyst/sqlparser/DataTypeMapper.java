package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TParseTreeNode;
import gudusoft.gsqlparser.nodes.TTypeName;
import org.schemaanalyst.sqlrepresentation.datatype.*;

class DataTypeMapper {

    // REFER TO the JavaDocs for TTypeName
    // http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html	
    DataType getDataType(TTypeName dataType, TParseTreeNode node) {

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

    private Integer getArgument(TConstant argument) {
        return argument == null ? null : Integer.valueOf(argument.toString());
    }

    private Integer getLength(TTypeName dataType) {
        return getArgument(dataType.getLength());
    }

    private Integer getPrecision(TTypeName dataType) {
        return getArgument(dataType.getPrecision());
    }

    private Integer getScale(TTypeName dataType) {
        return getArgument(dataType.getScale());
    }
}