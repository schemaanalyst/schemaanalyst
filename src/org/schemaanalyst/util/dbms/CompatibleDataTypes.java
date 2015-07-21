package org.schemaanalyst.util.dbms;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;

import java.util.*;

/**
 * Created by phil on 21/07/2015.
 */
public class CompatibleDataTypes {

    public void writeCompatibleDataTypes(DBMS dbms) {


    }

    private Map<DataType, Set<DataType>> getCompatibleDataTypes() {
        Map<DataType, Set<DataType>> equivalentDataTypes = new HashMap<>();

        List<DataType> dataTypeList = getDataTypeList();

        for (DataType sourceType : dataTypeList) {
            for (DataType targetType : dataTypeList) {

            }
        }

        return equivalentDataTypes;
    }

    private Schema makeSchema(DataType sourceType, DataType targetType) {
        Schema schema = new Schema("type_testing");
        Table sourceTable = schema.createTable("source");
        Table targetTable = schema.createTable("target");
        sourceTable.createColumn("sourceColumn", sourceType);
        targetTable.createColumn("targetColumn", targetType);
        return schema;
    }

    private List<DataType> getDataTypeList() {
        List dataTypeList = new ArrayList<>();
        dataTypeList.add(new BigIntDataType());
        dataTypeList.add(new BooleanDataType());
        dataTypeList.add(new CharDataType());
        dataTypeList.add(new DateDataType());
        dataTypeList.add(new DateTimeDataType());
        dataTypeList.add(new DecimalDataType());
        dataTypeList.add(new DoubleDataType());
        dataTypeList.add(new FloatDataType());
        dataTypeList.add(new IntDataType());
        dataTypeList.add(new MediumIntDataType());
        dataTypeList.add(new NumericDataType());
        dataTypeList.add(new RealDataType());
        dataTypeList.add(new SingleCharDataType());
        dataTypeList.add(new SmallIntDataType());
        dataTypeList.add(new TextDataType());
        dataTypeList.add(new TimeDataType());
        dataTypeList.add(new TimestampDataType());
        dataTypeList.add(new TinyIntDataType());
        dataTypeList.add(new VarCharDataType());
        return dataTypeList;
    }
}
