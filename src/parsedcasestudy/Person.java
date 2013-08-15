package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/*
 * Person schema.
 * Java code originally generated: 2013/08/15 23:00:37
 *
 */

@SuppressWarnings("serial")
public class Person extends Schema {

	public Person() {
		super("Person");

		Table tablePerson = this.createTable("person");
		tablePerson.createColumn("id", new IntDataType());
		tablePerson.createColumn("last_name", new VarCharDataType(45));
		tablePerson.createColumn("first_name", new VarCharDataType(45));
		tablePerson.createColumn("gender", new VarCharDataType(6));
		tablePerson.createColumn("date_of_birth", new DateDataType());
		tablePerson.createPrimaryKeyConstraint(tablePerson.getColumn("id"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("id"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("last_name"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("first_name"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("gender"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("date_of_birth"));
		tablePerson.createCheckConstraint(new InExpression(new ColumnExpression(tablePerson, tablePerson.getColumn("gender")), new ListExpression(new ConstantExpression(new StringValue("Male")), new ConstantExpression(new StringValue("Female")), new ConstantExpression(new StringValue("Uknown"))), false));
	}
}

