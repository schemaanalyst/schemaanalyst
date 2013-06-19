package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class DataTypeMappingException extends RuntimeException {
	
	public DataTypeMappingException(TTypeName dataType) {
		super("Data type: " + dataType + " is not supported [GSP EDataType: "+dataType.getDataType() + "]");
	}

}
