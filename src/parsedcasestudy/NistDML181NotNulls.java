package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML181NotNulls schema.
 * Java code originally generated: 2013/07/11 14:09:17
 *
 */

@SuppressWarnings("serial")
public class NistDML181NotNulls extends Schema {

	public NistDML181NotNulls() {
		super("NistDML181NotNulls");

		Table tableLongNamedPeople = this.createTable("LONG_NAMED_PEOPLE");
		tableLongNamedPeople.addColumn("FIRSTNAME", new VarCharDataType(373));
		tableLongNamedPeople.addColumn("LASTNAME", new VarCharDataType(373));
		tableLongNamedPeople.addColumn("AGE", new IntDataType());
		tableLongNamedPeople.setPrimaryKeyConstraint(tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME"));
		tableLongNamedPeople.addNotNullConstraint(tableLongNamedPeople.getColumn("FIRSTNAME"));
		tableLongNamedPeople.addNotNullConstraint(tableLongNamedPeople.getColumn("LASTNAME"));

		Table tableOrders = this.createTable("ORDERS");
		tableOrders.addColumn("FIRSTNAME", new VarCharDataType(373));
		tableOrders.addColumn("LASTNAME", new VarCharDataType(373));
		tableOrders.addColumn("TITLE", new VarCharDataType(80));
		tableOrders.addColumn("COST", new NumericDataType(5, 2));
		tableOrders.addForeignKeyConstraint(tableOrders.getColumn("FIRSTNAME"), tableOrders.getColumn("LASTNAME"), tableLongNamedPeople, tableLongNamedPeople.getColumn("FIRSTNAME"), tableLongNamedPeople.getColumn("LASTNAME"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("FIRSTNAME"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("LASTNAME"));
	}
}

