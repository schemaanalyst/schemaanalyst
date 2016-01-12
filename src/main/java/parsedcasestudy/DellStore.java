package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;

/*
 * DellStore schema.
 * Java code originally generated: 2013/08/17 00:30:32
 *
 */

@SuppressWarnings("serial")
public class DellStore extends Schema {

	public DellStore() {
		super("DellStore");

		Table tableCategories = this.createTable("categories");
		tableCategories.createColumn("category", new IntDataType());
		tableCategories.createColumn("categoryname", new VarCharDataType(50));
		this.createNotNullConstraint(tableCategories, tableCategories.getColumn("category"));
		this.createNotNullConstraint(tableCategories, tableCategories.getColumn("categoryname"));

		Table tableCustHist = this.createTable("cust_hist");
		tableCustHist.createColumn("customerid", new IntDataType());
		tableCustHist.createColumn("orderid", new IntDataType());
		tableCustHist.createColumn("prod_id", new IntDataType());
		this.createNotNullConstraint(tableCustHist, tableCustHist.getColumn("customerid"));
		this.createNotNullConstraint(tableCustHist, tableCustHist.getColumn("orderid"));
		this.createNotNullConstraint(tableCustHist, tableCustHist.getColumn("prod_id"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.createColumn("customerid", new IntDataType());
		tableCustomers.createColumn("firstname", new VarCharDataType(50));
		tableCustomers.createColumn("lastname", new VarCharDataType(50));
		tableCustomers.createColumn("address1", new VarCharDataType(50));
		tableCustomers.createColumn("address2", new VarCharDataType(50));
		tableCustomers.createColumn("city", new VarCharDataType(50));
		tableCustomers.createColumn("state", new VarCharDataType(50));
		tableCustomers.createColumn("zip", new IntDataType());
		tableCustomers.createColumn("country", new VarCharDataType(50));
		tableCustomers.createColumn("region", new SmallIntDataType());
		tableCustomers.createColumn("email", new VarCharDataType(50));
		tableCustomers.createColumn("phone", new VarCharDataType(50));
		tableCustomers.createColumn("creditcardtype", new IntDataType());
		tableCustomers.createColumn("creditcard", new VarCharDataType(50));
		tableCustomers.createColumn("creditcardexpiration", new VarCharDataType(50));
		tableCustomers.createColumn("username", new VarCharDataType(50));
		tableCustomers.createColumn("password", new VarCharDataType(50));
		tableCustomers.createColumn("age", new SmallIntDataType());
		tableCustomers.createColumn("income", new IntDataType());
		tableCustomers.createColumn("gender", new VarCharDataType(1));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("customerid"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("firstname"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("lastname"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("address1"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("city"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("country"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("region"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("creditcardtype"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("creditcard"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("creditcardexpiration"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("username"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("password"));

		Table tableInventory = this.createTable("inventory");
		tableInventory.createColumn("prod_id", new IntDataType());
		tableInventory.createColumn("quan_in_stock", new IntDataType());
		tableInventory.createColumn("sales", new IntDataType());
		this.createNotNullConstraint(tableInventory, tableInventory.getColumn("prod_id"));
		this.createNotNullConstraint(tableInventory, tableInventory.getColumn("quan_in_stock"));
		this.createNotNullConstraint(tableInventory, tableInventory.getColumn("sales"));

		Table tableOrderlines = this.createTable("orderlines");
		tableOrderlines.createColumn("orderlineid", new IntDataType());
		tableOrderlines.createColumn("orderid", new IntDataType());
		tableOrderlines.createColumn("prod_id", new IntDataType());
		tableOrderlines.createColumn("quantity", new SmallIntDataType());
		tableOrderlines.createColumn("orderdate", new DateDataType());
		this.createNotNullConstraint(tableOrderlines, tableOrderlines.getColumn("orderlineid"));
		this.createNotNullConstraint(tableOrderlines, tableOrderlines.getColumn("orderid"));
		this.createNotNullConstraint(tableOrderlines, tableOrderlines.getColumn("prod_id"));
		this.createNotNullConstraint(tableOrderlines, tableOrderlines.getColumn("quantity"));
		this.createNotNullConstraint(tableOrderlines, tableOrderlines.getColumn("orderdate"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("orderid", new IntDataType());
		tableOrders.createColumn("orderdate", new DateDataType());
		tableOrders.createColumn("customerid", new IntDataType());
		tableOrders.createColumn("netamount", new NumericDataType(12, 2));
		tableOrders.createColumn("tax", new NumericDataType(12, 2));
		tableOrders.createColumn("totalamount", new NumericDataType(12, 2));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("orderid"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("orderdate"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("netamount"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("tax"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("totalamount"));

		Table tableProducts = this.createTable("products");
		tableProducts.createColumn("prod_id", new IntDataType());
		tableProducts.createColumn("category", new IntDataType());
		tableProducts.createColumn("title", new VarCharDataType(50));
		tableProducts.createColumn("actor", new VarCharDataType(50));
		tableProducts.createColumn("price", new NumericDataType(12, 2));
		tableProducts.createColumn("special", new SmallIntDataType());
		tableProducts.createColumn("common_prod_id", new IntDataType());
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("prod_id"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("category"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("title"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("actor"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("price"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("common_prod_id"));

		Table tableReorder = this.createTable("reorder");
		tableReorder.createColumn("prod_id", new IntDataType());
		tableReorder.createColumn("date_low", new DateDataType());
		tableReorder.createColumn("quan_low", new IntDataType());
		tableReorder.createColumn("date_reordered", new DateDataType());
		tableReorder.createColumn("quan_reordered", new IntDataType());
		tableReorder.createColumn("date_expected", new DateDataType());
		this.createNotNullConstraint(tableReorder, tableReorder.getColumn("prod_id"));
		this.createNotNullConstraint(tableReorder, tableReorder.getColumn("date_low"));
		this.createNotNullConstraint(tableReorder, tableReorder.getColumn("quan_low"));
	}
}

