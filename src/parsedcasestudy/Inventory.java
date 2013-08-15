package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Inventory schema.
 * Java code originally generated: 2013/08/15 23:00:14
 *
 */

@SuppressWarnings("serial")
public class Inventory extends Schema {

	public Inventory() {
		super("Inventory");

		Table tableInventory = this.createTable("Inventory");
		tableInventory.createColumn("id", new IntDataType());
		tableInventory.createColumn("product", new VarCharDataType(50));
		tableInventory.createColumn("quantity", new IntDataType());
		tableInventory.createColumn("price", new DecimalDataType(18, 2));
		tableInventory.createPrimaryKeyConstraint(tableInventory.getColumn("id"));
		tableInventory.createUniqueConstraint(tableInventory.getColumn("product"));
	}
}

