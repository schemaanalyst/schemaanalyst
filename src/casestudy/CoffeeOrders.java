package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class CoffeeOrders extends Schema {

    static final long serialVersionUID = 5017527789364863282L;

	public CoffeeOrders() {

		super("CoffeeOrders");
		
		/*
			  CREATE TABLE coffees (
			  id INTEGER PRIMARY KEY,
			  coffee_name VARCHAR(50) NOT NULL,
			  price INT NOT NULL
		  );
		*/		
		
		Table coffeesTable = createTable("coffees");
		
		Column idColumn = coffeesTable.addColumn("id", new IntegerColumnType());
		idColumn.setPrimaryKey();
		
		coffeesTable.addColumn("coffee_name", new VarCharColumnType(50));

		Column priceColumn = coffeesTable.addColumn("price", new IntColumnType());
		priceColumn.setNotNull();

		/*
		  CREATE TABLE salespeople (
			  id INTEGER PRIMARY KEY,
			  first_name VARCHAR(50) NOT NULL,
			  last_name VARCHAR(50) NOT NULL,
			  commission_rate INT NOT NULL
		  );
		 */

		Table salesPeopleTable = createTable("salespeople");
		
		Column idSalesPeopleColumn = salesPeopleTable.addColumn("id", new IntegerColumnType());
		idSalesPeopleColumn.setPrimaryKey();
		
		Column firstNameColumn = salesPeopleTable.addColumn("first_name", new VarCharColumnType(50));
		firstNameColumn.setNotNull();
		
		Column lastNameColumn = salesPeopleTable.addColumn("last_name", new VarCharColumnType(50));
		lastNameColumn.setNotNull();
		
		Column commissionRateColumn = salesPeopleTable.addColumn("commission_rate", new IntColumnType());
		commissionRateColumn.setNotNull();

		/*
		  CREATE TABLE customers (
			  id INTEGER PRIMARY KEY,
			  company_name VARCHAR(50) NOT NULL,
			  street_address VARCHAR(50) NOT NULL,
			  city VARCHAR(50) NOT NULL,
			  state VARCHAR(50) NOT NULL,
			  zip VARCHAR(50) NOT NULL
		  );
		 */

		Table customersTable = createTable("customers");
		
		Column idCustomersColumn = customersTable.addColumn("id", new IntegerColumnType());
		idCustomersColumn.setPrimaryKey();
		
		Column companyNameColumn = customersTable.addColumn("company_name", new VarCharColumnType(50));
		companyNameColumn.setNotNull();
		
		Column streetAddressColumn = customersTable.addColumn("street_address", new VarCharColumnType(50));
		streetAddressColumn.setNotNull();
		
		Column cityColumn = customersTable.addColumn("city", new VarCharColumnType(50));
		cityColumn.setNotNull();
		
		Column stateColumn = customersTable.addColumn("state", new VarCharColumnType(50));
		stateColumn.setNotNull();
		
		Column zipColumn = customersTable.addColumn("zip", new VarCharColumnType(50));
		zipColumn.setNotNull();
		

		/*
		  CREATE TABLE orders (
			  id INTEGER PRIMARY KEY,
			  customer_id INTEGER,
			  salesperson_id INTEGER,
			  FOREIGN KEY(customer_id) REFERENCES customers(id),
			  FOREIGN KEY(salesperson_id) REFERENCES salespeople(id)
		  );
		 */

		Table ordersTable = createTable("orders");
		
		Column idOrdersColumn = ordersTable.addColumn("id", new IntegerColumnType());
		idOrdersColumn.setPrimaryKey();
		
		Column customerIdColumn = ordersTable.addColumn("customer_id", new IntegerColumnType());
		customerIdColumn.setForeignKey(customersTable, idCustomersColumn);
		
		Column salesPersonIdColumn = ordersTable.addColumn("salesperson_id", new IntegerColumnType());
		salesPersonIdColumn.setForeignKey(salesPeopleTable, idSalesPeopleColumn);

		/*
		  CREATE TABLE order_items (
			  id INTEGER PRIMARY KEY,
			  order_id INTEGER,
			  product_id INTEGER,
			  product_quantity INTEGER,
			  FOREIGN KEY(order_id) REFERENCES orders(id),
			  FOREIGN KEY(product_id) REFERENCES coffees(id)
		  );
		 */

		Table orderItemsTable = createTable("order_items");
		
		Column idOrderItemsColumn = orderItemsTable.addColumn("id", new IntegerColumnType());
		idOrderItemsColumn.setPrimaryKey();
		
		Column orderIdColumn = orderItemsTable.addColumn("order_id", new IntegerColumnType());
		orderIdColumn.setForeignKey(ordersTable, idOrdersColumn);
		
		Column productIdColumn = orderItemsTable.addColumn("product_id", new IntegerColumnType());
		productIdColumn.setForeignKey(coffeesTable, idColumn);
		
		orderItemsTable.addColumn("product_quantity", new IntegerColumnType());
	}
}
