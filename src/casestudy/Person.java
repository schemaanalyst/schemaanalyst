package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class Person extends Schema {
	
	private static final long serialVersionUID = 7132020402162724301L;

	public Person() {
		super("Person");
		
		/*
		CREATE TABLE person (
		        id int not null,
		        last_name varchar(45) not null,
		        first_name varchar(45) not null,
		        gender varchar(6) not null,
		        date_of_birth date not null,
		        PRIMARY KEY  (id),
			CHECK (gender IN ('Male', 'Female', 'Uknown'))
		);
		*/
		
		Table person = createTable("person");
		
		Column id = person.addColumn("id", new IntDataType());
		Column lastName = person.addColumn("lastName", new VarCharDataType(45));
		Column firstName = person.addColumn("firstName", new VarCharDataType(45));
		Column gender = person.addColumn("gender", new VarCharDataType(6));
		Column dateOfBirth = person.addColumn("date_of_birth", new DateDataType());
		
		id.setNotNull();
		lastName.setNotNull();
		firstName.setNotNull();
		gender.setNotNull();
		dateOfBirth.setNotNull();
		
		person.setPrimaryKeyConstraint(id);
		person.addCheckConstraint(new InCheckCondition(gender, "Male", "Female", "Uknown"));
	}
}
