package org.schemaanalyst.database;

public abstract class DatabaseInteractor {

    /** No database interaction return code */
    protected static final int START = 0; 

    /** The return code to specify that there was an error in creating the SQL schema with a CREATE TABLE */
    protected static final int CREATE_TABLE_ERROR = -1;

    /** The signature for the CREATE TABLE statement */
    protected static final String CREATE_TABLE = "CREATE TABLE";

    /** The return code indicates an UPDATE, INSERT, DELETE */
    protected static final boolean UPDATE_COUNT = false;

    /** The return code indicates a SELECT with a ResultSet */
    protected static final boolean RESULT_SET = true;

    public abstract void initializeDatabaseConnection();

    public abstract Integer executeUpdate(String command);

    public abstract Integer execute(String command);

}