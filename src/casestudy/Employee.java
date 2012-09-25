package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.RelationalCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class Employee extends Schema {

    static final long serialVersionUID = -8731994277749816652L;
	
	@SuppressWarnings("unused")
	public Employee() {
		super("Employee");
		
		/*
		  
		  create table Employee
		  (
		  id INT PRIMARY KEY, 	
		  first varchar(15),
		  last varchar(20),
		  age int,
		  address varchar(30),
		  city varchar(20),
		  state varchar(20)
		  CHECK (id >= 0) 
		  CHECK (age > 0)	 	
		  CHECK (age <= 150)	 	
		  );

		*/		
		
		Table employeeTable = createTable( "employee");
		
		Column id = employeeTable.addColumn("id" , new IntColumnType());
                employeeTable.setPrimaryKeyConstraint(id);
		Column first = employeeTable.addColumn("first" , new VarCharColumnType(15));
		Column last = employeeTable.addColumn("last" , new VarCharColumnType(20));
		Column age = employeeTable.addColumn("age" , new IntColumnType());
		Column address = employeeTable.addColumn("employee" , new VarCharColumnType(30));
		Column city = employeeTable.addColumn("city" , new VarCharColumnType(20));
		Column state = employeeTable.addColumn("state" , new VarCharColumnType(20));

		employeeTable.addCheckConstraint(new RelationalCheckPredicate(id, ">=", 0));
		employeeTable.addCheckConstraint(new RelationalCheckPredicate(age, ">", 0));
		employeeTable.addCheckConstraint(new RelationalCheckPredicate(age, "<=", 150)); 

	}
}
