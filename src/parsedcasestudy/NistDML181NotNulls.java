package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

import java.util.Arrays;

/*
 * NistDML181NotNulls schema.
 * Java code originally generated: 2013/08/17 00:30:44
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
		this.createPrimaryKeyConstraint(tableLongNamedPeople, tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME"));
		this.createNotNullConstraint(tableLongNamedPeople, tableLongNamedPeople.getColumn("FIRSTNAME"));
		this.createNotNullConstraint(tableLongNamedPeople, tableLongNamedPeople.getColumn("LASTNAME"));

		Table tableOrders = this.createTable("ORDERS");
		tableOrders.createColumn("FIRSTNAME", new VarCharDataType(373));
		tableOrders.createColumn("LASTNAME", new VarCharDataType(373));
		tableOrders.createColumn("TITLE", new VarCharDataType(80));
		tableOrders.createColumn("COST", new NumericDataType(5, 2));
		this.createForeignKeyConstraint(tableOrders, Arrays.asList(tableOrders.getColumn("FIRSTNAME"), tableOrders.getColumn("LASTNAME")), tableLongNamedPeople, Arrays.asList(tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME")));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("FIRSTNAME"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("LASTNAME"));
	}
}

