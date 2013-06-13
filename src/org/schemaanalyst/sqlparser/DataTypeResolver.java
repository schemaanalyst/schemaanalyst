package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDataType;

import org.schemaanalyst.schema.columntype.ColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

class DataTypeResolver {

	ColumnType resolve(EDataType dataType) {
		if (dataType == EDataType.int_t) {
    		return new IntColumnType();
    	} else if (dataType == EDataType.varchar_t) {
    		return new VarCharColumnType(50);
    	}
		return null;
	}
	
}
