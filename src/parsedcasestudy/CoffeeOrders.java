package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * CoffeeOrders schema.
 * Java code originally generated: 2013/08/17 00:30:29
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
		this.createPrimaryKeyConstraint(tableCoffees, tableCoffees.getColumn("id"));
		this.createNotNullConstraint(tableCoffees, tableCoffees.getColumn("coffee_name"));
		this.createNotNullConstraint(tableCoffees, tableCoffees.getColumn("price"));

		Table tableSalespeople = this.createTable("salespeople");
		tableSalespeople.createColumn("id", new IntDataType());
		tableSalespeople.createColumn("first_name", new VarCharDataType(50));
		tableSalespeople.createColumn("last_name", new VarCharDataType(50));
		tableSalespeople.createColumn("commission_rate", new IntDataType());
		this.createPrimaryKeyConstraint(tableSalespeople, tableSalespeople.getColumn("id"));
		this.createNotNullConstraint(tableSalespeople, tableSalespeople.getColumn("first_name"));
		this.createNotNullConstraint(tableSalespeople, tableSalespeople.getColumn("last_name"));
		this.createNotNullConstraint(tableSalespeople, tableSalespeople.getColumn("commission_rate"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.createColumn("id", new IntDataType());
		tableCustomers.createColumn("company_name", new VarCharDataType(50));
		tableCustomers.createColumn("street_address", new VarCharDataType(50));
		tableCustomers.createColumn("city", new VarCharDataType(50));
		tableCustomers.createColumn("state", new VarCharDataType(50));
		tableCustomers.createColumn("zip", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableCustomers, tableCustomers.getColumn("id"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("company_name"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("street_address"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("city"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("state"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("zip"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("id", new IntDataType());
		tableOrders.createColumn("customer_id", new IntDataType());
		tableOrders.createColumn("salesperson_id", new IntDataType());
		this.createPrimaryKeyConstraint(tableOrders, tableOrders.getColumn("id"));
		this.createForeignKeyConstraint(tableOrders, tableOrders.getColumn("customer_id"), tableCustomers, tableCustomers.getColumn("id"));
		this.createForeignKeyConstraint(tableOrders, tableOrders.getColumn("salesperson_id"), tableSalespeople, tableSalespeople.getColumn("id"));

		Table tableOrderItems = this.createTable("order_items");
		tableOrderItems.createColumn("id", new IntDataType());
		tableOrderItems.createColumn("order_id", new IntDataType());
		tableOrderItems.createColumn("product_id", new IntDataType());
		tableOrderItems.createColumn("product_quantity", new IntDataType());
		this.createPrimaryKeyConstraint(tableOrderItems, tableOrderItems.getColumn("id"));
		this.createForeignKeyConstraint(tableOrderItems, tableOrderItems.getColumn("order_id"), tableOrders, tableOrders.getColumn("id"));
		this.createForeignKeyConstraint(tableOrderItems, tableOrderItems.getColumn("product_id"), tableCoffees, tableCoffees.getColumn("id"));
	}
}

