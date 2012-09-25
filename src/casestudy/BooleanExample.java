package casestudy;

import org.schemaanalyst.schema.*;
import org.schemaanalyst.schema.columntype.*;

public class BooleanExample extends Schema {
    
    static final long serialVersionUID = -4855495220609266186L;

	@SuppressWarnings("unused")
	public BooleanExample() {
    
		super("BooleanExample");
        
        /*
            CREATE TABLE "daily_inventory" (
                "isbn" varchar(100),
                "is_stocked" boolean
            );
        */
        
        Table daily_inventory = createTable("daily_inventory");
        
        Column daily_inventory_isbn = daily_inventory.addColumn("isbn", new VarCharColumnType(100));
        
        Column daily_inventory_is_stocked = daily_inventory.addColumn("is_stocked", new org.schemaanalyst.schema.columntype.BooleanColumnType());
    }
}
