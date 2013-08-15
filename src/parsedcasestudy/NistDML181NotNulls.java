package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML181NotNulls schema.
 * Java code originally generated: 2013/08/15 23:00:20
 *
 */

@SuppressWarnings("serial")
public class NistDML181NotNulls extends Schema {

	public NistDML181NotNulls() {
		super("NistDML181NotNulls");

		Table tableLongNamedPeople = this.createTable("LONG_NAMED_PEOPLE");
		tableLongNamedPeople.createColumn("FIRSTNAME", new VarCharDataType(373));
		tableLongNamedPeople.createColumn("LASTNAME", new VarCharDataType(373));
		tableLongNamedPeople.createColumn("AGE", new IntDataType());
		tableLongNamedPeople.createPrimaryKeyConstraint(tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME"));
		tableLongNamedPeople.createNotNullConstraint(tableLongNamedPeople.getColumn("FIRSTNAME"));
		tableLongNamedPeople.createNotNullConstraint(tableLongNamedPeople.getColumn("LASTNAME"));

		Table tableOrders = this.createTable("ORDERS");
		tableOrders.createColumn("FIRSTNAME", new VarCharDataType(373));
		tableOrders.createColumn("LASTNAME", new VarCharDataType(373));
		tableOrders.createColumn("TITLE", new VarCharDataType(80));
		tableOrders.createColumn("COST", new NumericDataType(5, 2));
		tableOrders.createForeignKeyConstraint(Arrays.asList(tableOrders.getColumn("FIRSTNAME"), tableOrders.getColumn("LASTNAME")), tableLongNamedPeople, Arrays.asList(tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME")));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("FIRSTNAME"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("LASTNAME"));
	}
}

