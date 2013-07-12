package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

        Column idColumn = coffeesTable.addColumn("id", new IntDataType());
        idColumn.setPrimaryKey();

        coffeesTable.addColumn("coffee_name", new VarCharDataType(50));

        Column priceColumn = coffeesTable.addColumn("price", new IntDataType());
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

        Column idSalesPeopleColumn = salesPeopleTable.addColumn("id", new IntDataType());
        idSalesPeopleColumn.setPrimaryKey();

        Column firstNameColumn = salesPeopleTable.addColumn("first_name", new VarCharDataType(50));
        firstNameColumn.setNotNull();

        Column lastNameColumn = salesPeopleTable.addColumn("last_name", new VarCharDataType(50));
        lastNameColumn.setNotNull();

        Column commissionRateColumn = salesPeopleTable.addColumn("commission_rate", new IntDataType());
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

        Column idCustomersColumn = customersTable.addColumn("id", new IntDataType());
        idCustomersColumn.setPrimaryKey();

        Column companyNameColumn = customersTable.addColumn("company_name", new VarCharDataType(50));
        companyNameColumn.setNotNull();

        Column streetAddressColumn = customersTable.addColumn("street_address", new VarCharDataType(50));
        streetAddressColumn.setNotNull();

        Column cityColumn = customersTable.addColumn("city", new VarCharDataType(50));
        cityColumn.setNotNull();

        Column stateColumn = customersTable.addColumn("state", new VarCharDataType(50));
        stateColumn.setNotNull();

        Column zipColumn = customersTable.addColumn("zip", new VarCharDataType(50));
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

        Column idOrdersColumn = ordersTable.addColumn("id", new IntDataType());
        idOrdersColumn.setPrimaryKey();

        Column customerIdColumn = ordersTable.addColumn("customer_id", new IntDataType());
        customerIdColumn.setForeignKey(customersTable, idCustomersColumn);

        Column salesPersonIdColumn = ordersTable.addColumn("salesperson_id", new IntDataType());
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

        Column idOrderItemsColumn = orderItemsTable.addColumn("id", new IntDataType());
        idOrderItemsColumn.setPrimaryKey();

        Column orderIdColumn = orderItemsTable.addColumn("order_id", new IntDataType());
        orderIdColumn.setForeignKey(ordersTable, idOrdersColumn);

        Column productIdColumn = orderItemsTable.addColumn("product_id", new IntDataType());
        productIdColumn.setForeignKey(coffeesTable, idColumn);

        orderItemsTable.addColumn("product_quantity", new IntDataType());
    }
}
