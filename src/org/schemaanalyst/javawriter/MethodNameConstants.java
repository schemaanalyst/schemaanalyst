package org.schemaanalyst.javawriter;

public class MethodNameConstants {

	// Schema class
	static final String SCHEMA_CREATE_TABLE_METHOD              = "createTable";
	
	// Table class
	static final String TABLE_ADD_COLUMN_METHOD                 = "addColumn";
	static final String TABLE_GET_COLUMN_METHOD                 = "getColumn";
	
	// Table class -- constraints
	static final String TABLE_ADD_CHECK_CONSTRAINT_METHOD 		= "addCheckConstraint";
	static final String TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD = "addForeignKeyConstraint";
	static final String TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD    = "addNotNullConstraint";
	static final String TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD = "setPrimaryKeyConstraint";
	static final String TABLE_ADD_UNIQUE_CONSTRAINT_METHOD      = "addUniqueConstraint";
	
	static final String TABLE_MAKE_COLUMN_LIST_METHOD           = "makeColumnList";	
}
