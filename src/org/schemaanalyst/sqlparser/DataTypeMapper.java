package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDataType;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TTypeName;

import org.schemaanalyst.representation.datatype.BooleanDataType;
import org.schemaanalyst.representation.datatype.CharDataType;
import org.schemaanalyst.representation.datatype.DataType;
import org.schemaanalyst.representation.datatype.DateDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.NumericDataType;
import org.schemaanalyst.representation.datatype.TimestampDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

class DataTypeMapper {

	// REFER TO the JavaDocs for TTypeName
	// http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html
	
	DataType getDataType(TTypeName dataType) {
		
		EDataType enumType = dataType.getDataType();	
					
		// *** BOOLEAN *** 
		if (enumType == EDataType.bool_t) {
			return new BooleanDataType();
		}
		
		
		// *** CHARACTER STRING *** 
		// char
		if (enumType == EDataType.nchar_t) {		
			TConstant lengthConstant = dataType.getLength();    		
			int length = Integer.valueOf(lengthConstant.toString());
			return new CharDataType(length);    		
		}

		// varchar
		if (enumType == EDataType.varchar_t) {	
			int length = getLength(dataType);
			return new VarCharDataType(length);		
		}
		
		// *** NUMERIC *** 	
		// int
		if (enumType == EDataType.int_t) {
    		return new IntDataType();
		}
		
    	// numeric
		if (enumType == EDataType.numeric_t) {		
			int precision = getPrecision(dataType);
			int scale = getScale(dataType);
			
			return new NumericDataType(precision, scale);
		}
			

		// *** TEMPORAL *** 
		// date
		if (enumType == EDataType.date_t) {
			return new DateDataType();
		}
		
		// timestamp with time zone
		if (enumType == EDataType.timestamp_with_time_zone_t) {			
			// TODO -- new time required here
			return new TimestampDataType();
		}		
			
		// UNKNOWN!
		throw new DataTypeMappingException(dataType);
	}	
	
	Integer getLength(TTypeName dataType) {
		return getArgument(dataType.getLength());				
	}
	
	Integer getPrecision(TTypeName dataType) {
		return getArgument(dataType.getPrecision());				
	}
	
	Integer getScale(TTypeName dataType) {
		return getArgument(dataType.getScale());
	}	

	Integer getArgument(TConstant argument) {
		return argument == null ? null : Integer.valueOf(argument.toString());
	}
}
