package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class NistDML181NotNulls extends Schema {

    static final long serialVersionUID = 430698852888568914L;
	
	@SuppressWarnings("unused")
	public NistDML181NotNulls() {
		super("NistDML181NotNulls");
		
		/*
		  
		  CREATE TABLE LONG_NAMED_PEOPLE 
		  (
		  FIRSTNAME VARCHAR (373),
		  LASTNAME VARCHAR (373),
		  AGE INT,
		  PRIMARY KEY (FIRSTNAME, LASTNAME)
		  );

		*/

		Table longNamedPeopleTable = createTable( "LONG_NAMED_PEOPLE");

		Column firstName = longNamedPeopleTable.addColumn("FIRSTNAME" , new VarCharColumnType(373));
		Column lastName = longNamedPeopleTable.addColumn("LASTNAME" , new VarCharColumnType(373));
		Column age = longNamedPeopleTable.addColumn("AGE" , new IntColumnType());

		firstName.setNotNull();
		lastName.setNotNull();
		
		longNamedPeopleTable.setPrimaryKeyConstraint(firstName, lastName);

		/*

		  CREATE TABLE ORDERS 
		  (
		  FIRSTNAME VARCHAR (373),
		  LASTNAME VARCHAR (373),
		  TITLE VARCHAR (80),
		  COST NUMERIC(5,2),
		  FOREIGN KEY (FIRSTNAME, LASTNAME)
		  REFERENCES LONG_NAMED_PEOPLE
		  );

		 */

		Table ordersTable = createTable( "ORDERS");
		
		Column firstNameOrders = ordersTable.addColumn("FIRSTNAME" , new VarCharColumnType(373));
		Column lastNameOrders = ordersTable.addColumn("LASTNAME" , new VarCharColumnType(373));
		Column title = ordersTable.addColumn("TITLE" , new VarCharColumnType(80));
		Column cost = ordersTable.addColumn("COST" , new NumericColumnType(5,2));

		firstNameOrders.setNotNull();
		lastNameOrders.setNotNull();
		ordersTable.addForeignKeyConstraint(longNamedPeopleTable, firstNameOrders, lastNameOrders, firstName, lastName);
	}
}
