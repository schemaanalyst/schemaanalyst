package casestudy;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.DecimalDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

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
		
		Column id = inventory.addColumn("id", new IntDataType());
		id.setPrimaryKey();
		
		Column product = inventory.addColumn("product", new VarCharDataType(50));
		product.setUnique();
		
		inventory.addColumn("quantity", new IntDataType());
		inventory.addColumn("price", new DecimalDataType(18, 2));	
	}
}
