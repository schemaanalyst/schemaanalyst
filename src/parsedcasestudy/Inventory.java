package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Inventory schema.
 * Java code originally generated: 2013/07/11 14:08:35
 *
 */

@SuppressWarnings("serial")
public class Inventory extends Schema {

	public Inventory() {
		super("Inventory");

		Table tableInventory = this.createTable("Inventory");
		tableInventory.addColumn("id", new IntDataType());
		tableInventory.addColumn("product", new VarCharDataType(50));
		tableInventory.addColumn("quantity", new IntDataType());
		tableInventory.addColumn("price", new DecimalDataType(18, 2));
		tableInventory.setPrimaryKeyConstraint(tableInventory.getColumn("id"));
		tableInventory.addUniqueConstraint(tableInventory.getColumn("product"));
	}
}

