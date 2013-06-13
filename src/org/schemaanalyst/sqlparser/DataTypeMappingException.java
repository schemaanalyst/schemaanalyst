package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class DataTypeMappingException extends RuntimeException {
	
	public DataTypeMappingException(TTypeName dataType) {
		super("Cannot resolve data type: " + dataType + " [GSP EDataType: "+dataType.getDataType() + "]");
	}

}
