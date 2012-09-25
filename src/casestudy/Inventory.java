package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.attribute.IdentityAttribute;
import org.schemaanalyst.schema.columntype.DecimalColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class Inventory extends Schema {

	private static final long serialVersionUID = 1652955918690283447L;

	public Inventory() {
		super("Inventory");
		
		/*
			CREATE TABLE Inventory
			(
			   id INT IDENTITY(1,1) PRIMARY KEY,
			   product VARCHAR(50) UNIQUE,
			   quantity INT,
			   price DECIMAL(18,2)
			);
		*/
		
		Table inventory = createTable("Inventory");
		
		Column id = inventory.addColumn("id", new IntColumnType());
		id.setPrimaryKey();
		//id.addAttribute(new IdentityAttribute());
		
		Column product = inventory.addColumn("product", new VarCharColumnType(50));
		product.setUnique();
		
		inventory.addColumn("quantity", new IntColumnType());
		inventory.addColumn("price", new DecimalColumnType(18, 2));	
	}
}
