package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDataType;
import gudusoft.gsqlparser.nodes.TConstant;
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
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TinyIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

class DataTypeMapper {
	
	// REFER TO the JavaDocs for TTypeName
	// http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TTypeName.html	

	DataType getDataType(TTypeName dataType) {
		
		EDataType enumType = dataType.getDataType();	
		String typeString = dataType.toString();
		
		// *** BOOLEAN *** 
		if (enumType == EDataType.bool_t) {
			return new BooleanDataType();
		}		
		
		// *** CHARACTER STRING *** 
		// char
		if (enumType == EDataType.char_t || enumType == EDataType.nchar_t) {		
			// [GSP BUG 38, DBMS Postgres] "character" parsed as NVAR not CHAR 
			// (character seems to be regarded as nchar, and char char, but there is no difference
			// according to the Postgres docs -- Postgres has no notion of nchar	
			
			TConstant lengthConstant = dataType.getLength();    	
			if (lengthConstant != null) {
				int length = Integer.valueOf(lengthConstant.toString());
				return new CharDataType(length);
			}			    		
		}
		
		// varchar
		if (enumType == EDataType.varchar_t || enumType == EDataType.nvarchar_t) {	
			// [GSP BUG 38, DBMS Postgres] "character varying" parsed as NVARCHAR not VARCHAR 
			// (character seems to be regarded as nchar, and char char, but there is no difference
			// according to the Postgres docs -- Postgres has no notion of nchar
			
			int length = getLength(dataType);
			return new VarCharDataType(length);		
		}
		
		// text 
		// [TODO] New data type required to handle longtext?
		if (enumType == EDataType.text_t || enumType == EDataType.longtext_t) {	
			return new TextDataType();		
		}
				
		// *** NUMERIC *** 	
		// decimal
		if (enumType == EDataType.dec_t) {
			return new DecimalDataType(getPrecision(dataType), getScale(dataType));			
		}
		
		// int
		if (enumType == EDataType.int_t) {
    		return new IntDataType();
		}
		
		// int
		if (enumType == EDataType.smallint_t) {
    		return new SmallIntDataType();
		}			
		
    	// numeric
		if (enumType == EDataType.numeric_t) {		
			return new NumericDataType(getPrecision(dataType), getScale(dataType));
		}
		
    	// real		
		if (enumType == EDataType.real_t) {
			
			// [GSP BUG 42, DBMS MySQL] tinyint is a real on MySQL
			if (typeString.toLowerCase().equals("tinyint")) {
				return new TinyIntDataType();
			}
			
			return new RealDataType();
		}
			
		// *** TEMPORAL *** 
		// date
		if (enumType == EDataType.date_t) {
			return new DateDataType();
		}

		// datetime
		if (enumType == EDataType.datetime_t) {
			return new DateTimeDataType();
		}		
		
		// timestamp
		if (enumType == EDataType.timestamp_t) {			
			return new TimestampDataType();
		}								
		
		// *** GENERIC ***
		if (enumType == EDataType.generic_t) {

			// [GSP BUG 43, DBMS MySQL] "boolean" parsed as generic
			if (typeString.toLowerCase().equals("boolean")) {
				return new BooleanDataType();
			}			
					
			// [GSP BUG 44, DBMS Postgres] "text" parsed as generic
			if (typeString.toLowerCase().equals("text")) {
				return new TextDataType();
			}
			
			// [GSP BUG 39, DBMS Postgres] "time" parsed as generic
			if (typeString.toLowerCase().equals("time")) {
				return new TimeDataType();
			}		
			
			// [GSP BUG 39, DBMS Postgres] "serial" parsed as generic 
			// [TODO] new type potentially, handling as int			
			if (typeString.toLowerCase().equals("serial")) {
				return new IntDataType();
			}
		}		
		
		// Data type not supported
		throw new UnsupportedFeatureException(dataType);
	}	
	
	Integer getArgument(TConstant argument) {
		return argument == null ? null : Integer.valueOf(argument.toString());
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
	
	static DataType map(TTypeName dataType) {
		return (new DataTypeMapper()).getDataType(dataType);
	}	
}
