package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

        Table employeeTable = createTable("employee");

        Column id = employeeTable.addColumn("id", new IntDataType());
        employeeTable.setPrimaryKeyConstraint(id);
        Column first = employeeTable.addColumn("first", new VarCharDataType(15));
        Column last = employeeTable.addColumn("last", new VarCharDataType(20));
        Column age = employeeTable.addColumn("age", new IntDataType());
        Column address = employeeTable.addColumn("employee", new VarCharDataType(30));
        Column city = employeeTable.addColumn("city", new VarCharDataType(20));
        Column state = employeeTable.addColumn("state", new VarCharDataType(20));

        employeeTable.addCheckConstraint(new RelationalCheckCondition(id, ">=", 0));
        employeeTable.addCheckConstraint(new RelationalCheckCondition(age, ">", 0));
        employeeTable.addCheckConstraint(new RelationalCheckCondition(age, "<=", 150));

    }
}
