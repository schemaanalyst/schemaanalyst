package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

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
		
		Column id = person.addColumn("id", new IntColumnType());
		Column lastName = person.addColumn("lastName", new VarCharColumnType(45));
		Column firstName = person.addColumn("firstName", new VarCharColumnType(45));
		Column gender = person.addColumn("gender", new VarCharColumnType(6));
		Column dateOfBirth = person.addColumn("date_of_birth", new DateColumnType());
		
		id.setNotNull();
		lastName.setNotNull();
		firstName.setNotNull();
		gender.setNotNull();
		dateOfBirth.setNotNull();
		
		person.setPrimaryKeyConstraint(id);
		person.addCheckConstraint(new InCheckPredicate(gender, "Male", "Female", "Uknown"));
	}
}
