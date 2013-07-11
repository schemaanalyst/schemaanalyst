package org.schemaanalyst.database;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.sqlwriter.CellSQLWriter;

public abstract class Database {

	protected SQLWriter sqlWriter;
	private ValueFactory valueFactory;
	private CellSQLWriter valueToStringConverter;
	
	public Database() {
		sqlWriter = new SQLWriter();
		valueFactory = new ValueFactory();
		valueToStringConverter = new CellSQLWriter();
	}
	
	public SQLWriter getSQLWriter() {
		return sqlWriter;
	}
	
	public ValueFactory getValueFactory() {
		return valueFactory;
	}
	
	public CellSQLWriter getValueStringConverter() {
		return valueToStringConverter;
	}
	
    public abstract DatabaseInteractor getDatabaseInteraction();
    
	public abstract void accept(DatabaseVisitor visitor);
	
	public static Database instantiate(String name) throws ClassNotFoundException, 
														   InstantiationException, 
														   IllegalAccessException {
		String databaseClassName = 
				"org.schemaanalyst.database." + 
				name.toLowerCase() + "." + name;
		Class<?> databaseClass = Class.forName(databaseClassName);
		Database database = (Database) databaseClass.newInstance();
		return database;
	}
	
}
