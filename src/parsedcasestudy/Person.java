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
 * Java code originally generated: 2013/07/11 14:10:49
 *
 */
@SuppressWarnings("serial")
public class Person extends Schema {

    public Person() {
        super("Person");

        Table tablePerson = this.createTable("person");
        tablePerson.addColumn("id", new IntDataType());
        tablePerson.addColumn("last_name", new VarCharDataType(45));
        tablePerson.addColumn("first_name", new VarCharDataType(45));
        tablePerson.addColumn("gender", new VarCharDataType(6));
        tablePerson.addColumn("date_of_birth", new DateDataType());
        tablePerson.setPrimaryKeyConstraint(tablePerson.getColumn("id"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("id"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("last_name"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("first_name"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("gender"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("date_of_birth"));
        tablePerson.addCheckConstraint(new InExpression(new ColumnExpression(tablePerson.getColumn("gender")), new ListExpression(new ConstantExpression(new StringValue("Male")), new ConstantExpression(new StringValue("Female")), new ConstantExpression(new StringValue("Uknown"))), false));
    }
}
