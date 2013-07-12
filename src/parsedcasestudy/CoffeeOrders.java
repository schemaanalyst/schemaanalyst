package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * CoffeeOrders schema.
 * Java code originally generated: 2013/07/11 14:07:30
 *
 */
@SuppressWarnings("serial")
public class CoffeeOrders extends Schema {

    public CoffeeOrders() {
        super("CoffeeOrders");

        Table tableCoffees = this.createTable("coffees");
        tableCoffees.addColumn("id", new IntDataType());
        tableCoffees.addColumn("coffee_name", new VarCharDataType(50));
        tableCoffees.addColumn("price", new IntDataType());
        tableCoffees.setPrimaryKeyConstraint(tableCoffees.getColumn("id"));
        tableCoffees.addNotNullConstraint(tableCoffees.getColumn("coffee_name"));
        tableCoffees.addNotNullConstraint(tableCoffees.getColumn("price"));

        Table tableSalespeople = this.createTable("salespeople");
        tableSalespeople.addColumn("id", new IntDataType());
        tableSalespeople.addColumn("first_name", new VarCharDataType(50));
        tableSalespeople.addColumn("last_name", new VarCharDataType(50));
        tableSalespeople.addColumn("commission_rate", new IntDataType());
        tableSalespeople.setPrimaryKeyConstraint(tableSalespeople.getColumn("id"));
        tableSalespeople.addNotNullConstraint(tableSalespeople.getColumn("first_name"));
        tableSalespeople.addNotNullConstraint(tableSalespeople.getColumn("last_name"));
        tableSalespeople.addNotNullConstraint(tableSalespeople.getColumn("commission_rate"));

        Table tableCustomers = this.createTable("customers");
        tableCustomers.addColumn("id", new IntDataType());
        tableCustomers.addColumn("company_name", new VarCharDataType(50));
        tableCustomers.addColumn("street_address", new VarCharDataType(50));
        tableCustomers.addColumn("city", new VarCharDataType(50));
        tableCustomers.addColumn("state", new VarCharDataType(50));
        tableCustomers.addColumn("zip", new VarCharDataType(50));
        tableCustomers.setPrimaryKeyConstraint(tableCustomers.getColumn("id"));
        tableCustomers.addNotNullConstraint(tableCustomers.getColumn("company_name"));
        tableCustomers.addNotNullConstraint(tableCustomers.getColumn("street_address"));
        tableCustomers.addNotNullConstraint(tableCustomers.getColumn("city"));
        tableCustomers.addNotNullConstraint(tableCustomers.getColumn("state"));
        tableCustomers.addNotNullConstraint(tableCustomers.getColumn("zip"));

        Table tableOrders = this.createTable("orders");
        tableOrders.addColumn("id", new IntDataType());
        tableOrders.addColumn("customer_id", new IntDataType());
        tableOrders.addColumn("salesperson_id", new IntDataType());
        tableOrders.setPrimaryKeyConstraint(tableOrders.getColumn("id"));
        tableOrders.addForeignKeyConstraint(tableOrders.getColumn("customer_id"), tableCustomers, tableCustomers.getColumn("id"));
        tableOrders.addForeignKeyConstraint(tableOrders.getColumn("salesperson_id"), tableSalespeople, tableSalespeople.getColumn("id"));

        Table tableOrderItems = this.createTable("order_items");
        tableOrderItems.addColumn("id", new IntDataType());
        tableOrderItems.addColumn("order_id", new IntDataType());
        tableOrderItems.addColumn("product_id", new IntDataType());
        tableOrderItems.addColumn("product_quantity", new IntDataType());
        tableOrderItems.setPrimaryKeyConstraint(tableOrderItems.getColumn("id"));
        tableOrderItems.addForeignKeyConstraint(tableOrderItems.getColumn("order_id"), tableOrders, tableOrders.getColumn("id"));
        tableOrderItems.addForeignKeyConstraint(tableOrderItems.getColumn("product_id"), tableCoffees, tableCoffees.getColumn("id"));
    }
}
