package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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
