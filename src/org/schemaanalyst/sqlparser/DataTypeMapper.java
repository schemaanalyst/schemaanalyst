package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDataType;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TTypeName;

import org.schemaanalyst.representation.datatype.BooleanDataType;
import org.schemaanalyst.representation.datatype.CharDataType;
import org.schemaanalyst.representation.datatype.DataType;
import org.schemaanalyst.representation.datatype.DateDataType;
import org.schemaanalyst.representation.datatype.DecimalDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.NumericDataType;
import org.schemaanalyst.representation.datatype.SmallIntDataType;
import org.schemaanalyst.representation.datatype.TimestampDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

class DataTypeMapper {

	// REFER TO the JavaDocs for TTypeName
	// http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html
	
	static DataType map(TTypeName dataType) {
		return (new DataTypeMapper()).getDataType(dataType);
	}
	
	DataType getDataType(TTypeName dataType) {
		
		EDataType enumType = dataType.getDataType();	
					
		// *** BOOLEAN *** 
		if (enumType == EDataType.bool_t) {
			return new BooleanDataType();
		}
		
		
		// *** CHARACTER STRING *** 
		// char
		if (enumType == EDataType.char_t || enumType == EDataType.nchar_t) {		

			// NOTE: with GSP and Postgres schemas, "character" is parsed as NCHAR, which I think is a bug -- logged (no. 38). 
			// May need to address if not fixed.
			
			TConstant lengthConstant = dataType.getLength();    		
			int length = Integer.valueOf(lengthConstant.toString());
			return new CharDataType(length);    		
		}
		
		// varchar
		if (enumType == EDataType.varchar_t || enumType == EDataType.nvarchar_t) {	
			
			// NOTE: with GSP and Postgres schemas, "character varying" is parsed as NVARCHAR, which I think is a bug -- not logged, but may be dealt with no. 38 above.
			// Check -- may need to address/log if not fixed.			
			
			int length = getLength(dataType);
			return new VarCharDataType(length);		
		}
		
		// *** NUMERIC *** 	
		// decimal
		if (enumType == EDataType.dec_t) {
			int precision = getPrecision(dataType);
			int scale = getScale(dataType);
			
			return new DecimalDataType(precision, scale);			
		}
		
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
		
		// int
		if (enumType == EDataType.smallint_t) {
    		return new SmallIntDataType();
		}		
		

		// *** TEMPORAL *** 
		// date
		if (enumType == EDataType.date_t) {
			return new DateDataType();
		}
		
		// timestamp
		if (enumType == EDataType.timestamp_t) {			
			return new TimestampDataType();
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
