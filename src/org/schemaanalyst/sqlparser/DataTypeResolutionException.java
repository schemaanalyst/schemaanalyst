package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TTypeName;

@SuppressWarnings("serial")
public class DataTypeResolutionException extends RuntimeException {
	
	public DataTypeResolutionException(TTypeName dataType) {
		super("Cannot resolve data type: " + dataType + " [GSP EDataType: "+dataType.getDataType() + "]");
	}

}
