package org.schemaanalyst.dbms;

public class DBMSFactory {

	public static DBMS instantiate(String name) throws ClassNotFoundException, 
                                                           InstantiationException, 
                                                           IllegalAccessException {
		String dbmsClassName = 
				"org.schemaanalyst.dbms." + 
						name.toLowerCase() + "." + name;
		
		Class<?> dbmsClass = Class.forName(dbmsClassName);
		DBMS dbms = (DBMS) dbmsClass.newInstance();
		return dbms;
	}	
}
