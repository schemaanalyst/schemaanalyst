package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * CoffeeOrders schema.
 * Java code originally generated: 2013/08/15 10:51:41
 *
 */

@SuppressWarnings("serial")
public class CoffeeOrders extends Schema {

	public CoffeeOrders() {
		super("CoffeeOrders");

		Table tableCoffees = this.createTable("coffees");
		tableCoffees.createColumn("id", new IntDataType());
		tableCoffees.createColumn("coffee_name", new VarCharDataType(50));
		tableCoffees.createColumn("price", new IntDataType());
		tableCoffees.createPrimaryKeyConstraint(tableCoffees.getColumn("id"));
		tableCoffees.createNotNullConstraint(tableCoffees.getColumn("coffee_name"));
		tableCoffees.createNotNullConstraint(tableCoffees.getColumn("price"));

		Table tableSalespeople = this.createTable("salespeople");
		tableSalespeople.createColumn("id", new IntDataType());
		tableSalespeople.createColumn("first_name", new VarCharDataType(50));
		tableSalespeople.createColumn("last_name", new VarCharDataType(50));
		tableSalespeople.createColumn("commission_rate", new IntDataType());
		tableSalespeople.createPrimaryKeyConstraint(tableSalespeople.getColumn("id"));
		tableSalespeople.createNotNullConstraint(tableSalespeople.getColumn("first_name"));
		tableSalespeople.createNotNullConstraint(tableSalespeople.getColumn("last_name"));
		tableSalespeople.createNotNullConstraint(tableSalespeople.getColumn("commission_rate"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.createColumn("id", new IntDataType());
		tableCustomers.createColumn("company_name", new VarCharDataType(50));
		tableCustomers.createColumn("street_address", new VarCharDataType(50));
		tableCustomers.createColumn("city", new VarCharDataType(50));
		tableCustomers.createColumn("state", new VarCharDataType(50));
		tableCustomers.createColumn("zip", new VarCharDataType(50));
		tableCustomers.createPrimaryKeyConstraint(tableCustomers.getColumn("id"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("company_name"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("street_address"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("city"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("state"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("zip"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("id", new IntDataType());
		tableOrders.createColumn("customer_id", new IntDataType());
		tableOrders.createColumn("salesperson_id", new IntDataType());
		tableOrders.createPrimaryKeyConstraint(tableOrders.getColumn("id"));
		tableOrders.createForeignKeyConstraint(tableOrders.getColumn("customer_id"), tableCustomers, tableOrders.getColumn("id"));
		tableOrders.createForeignKeyConstraint(tableOrders.getColumn("salesperson_id"), tableSalespeople, tableOrders.getColumn("id"));

		Table tableOrderItems = this.createTable("order_items");
		tableOrderItems.createColumn("id", new IntDataType());
		tableOrderItems.createColumn("order_id", new IntDataType());
		tableOrderItems.createColumn("product_id", new IntDataType());
		tableOrderItems.createColumn("product_quantity", new IntDataType());
		tableOrderItems.createPrimaryKeyConstraint(tableOrderItems.getColumn("id"));
		tableOrderItems.createForeignKeyConstraint(tableOrderItems.getColumn("order_id"), tableOrders, tableOrderItems.getColumn("id"));
		tableOrderItems.createForeignKeyConstraint(tableOrderItems.getColumn("product_id"), tableCoffees, tableOrderItems.getColumn("id"));
	}
}

