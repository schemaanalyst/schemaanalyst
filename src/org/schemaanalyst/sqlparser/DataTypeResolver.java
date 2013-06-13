package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDataType;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TTypeName;

import org.schemaanalyst.schema.columntype.BooleanColumnType;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.ColumnType;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

class DataTypeResolver {

	// REFER TO the JavaDocs for TTypeName
	// http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html
	
	ColumnType resolve(TTypeName dataType) {
		
		EDataType enumType = dataType.getDataType();	
					
		// *** BOOLEAN *** 
		if (enumType == EDataType.bool_t) {
			return new BooleanColumnType();
		}
		
		
		// *** CHARACTER STRING *** 
		// char
		if (enumType == EDataType.nchar_t) {		
			TConstant lengthConstant = dataType.getLength();    		
			int length = Integer.valueOf(lengthConstant.toString());
			return new CharColumnType(length);    		
		}

		// varchar
		if (enumType == EDataType.varchar_t) {	
			int length = getLength(dataType);
			return new VarCharColumnType(length);		
		}
		
		// *** NUMERIC *** 	
		// int
		if (enumType == EDataType.int_t) {
    		return new IntColumnType();
		}
		
    	// numeric
		if (enumType == EDataType.numeric_t) {		
			int precision = getPrecision(dataType);
			int scale = getScale(dataType);
			
			return new NumericColumnType(precision, scale);
		}
			

		// *** TEMPORAL *** 
		// date
		if (enumType == EDataType.date_t) {
			return new DateColumnType();
		}
		
		// timestamp with time zone
		if (enumType == EDataType.timestamp_with_time_zone_t) {			
			// TODO -- return something more specific here?
			return new TimestampColumnType();
		}
		
		// *** GENERIC *** 		
		if (enumType == EDataType.generic_t) {	
			
			// this is a bug in GSP for Postgres
			// (logged with GSP bug database -- issue 0000032).			
			if (dataType.toString().equals("date")) {
				return new DateColumnType();
			}
		}
		
			
		// UNKNOWN!
		throw new DataTypeResolutionException(dataType);
	}	
	
	Integer getLength(TTypeName dataType) {
		return getArgument(dataType, dataType.getLength(), 0);				
	}
	
	Integer getPrecision(TTypeName dataType) {
		return getArgument(dataType, dataType.getPrecision(), 0);				
	}
	
	Integer getScale(TTypeName dataType) {
		return getArgument(dataType, dataType.getScale(), 1);
	}	

	// Get data type parameters -- defensive against bugs in GSP fore Postgres, where these values are not parsed
	// (logged with GSP bug database -- issue 0000031).
	Integer getArgument(TTypeName dataType, TConstant parsed, int argNo) {
		if (parsed == null) {
			String dataTypeStr = dataType.toString();
			String paramStr = dataTypeStr.substring(dataTypeStr.indexOf("(") + 1, dataTypeStr.indexOf(")"));
			String[] paramStrs = paramStr.split(",");			
			return (paramStrs.length > argNo) ? Integer.valueOf(paramStrs[argNo]) : null;
		} else {
			return Integer.valueOf(parsed.toString());
		}
	}
}
