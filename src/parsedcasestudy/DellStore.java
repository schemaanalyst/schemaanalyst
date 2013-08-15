package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * DellStore schema.
 * Java code originally generated: 2013/08/15 23:00:07
 *
 */

@SuppressWarnings("serial")
public class DellStore extends Schema {

	public DellStore() {
		super("DellStore");

		Table tableCategories = this.createTable("categories");
		tableCategories.createColumn("category", new IntDataType());
		tableCategories.createColumn("categoryname", new VarCharDataType(50));
		tableCategories.createNotNullConstraint(tableCategories.getColumn("category"));
		tableCategories.createNotNullConstraint(tableCategories.getColumn("categoryname"));

		Table tableCustHist = this.createTable("cust_hist");
		tableCustHist.createColumn("customerid", new IntDataType());
		tableCustHist.createColumn("orderid", new IntDataType());
		tableCustHist.createColumn("prod_id", new IntDataType());
		tableCustHist.createNotNullConstraint(tableCustHist.getColumn("customerid"));
		tableCustHist.createNotNullConstraint(tableCustHist.getColumn("orderid"));
		tableCustHist.createNotNullConstraint(tableCustHist.getColumn("prod_id"));

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
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("customerid"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("firstname"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("lastname"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("address1"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("city"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("country"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("region"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("creditcardtype"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("creditcard"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("creditcardexpiration"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("username"));
		tableCustomers.createNotNullConstraint(tableCustomers.getColumn("password"));

		Table tableInventory = this.createTable("inventory");
		tableInventory.createColumn("prod_id", new IntDataType());
		tableInventory.createColumn("quan_in_stock", new IntDataType());
		tableInventory.createColumn("sales", new IntDataType());
		tableInventory.createNotNullConstraint(tableInventory.getColumn("prod_id"));
		tableInventory.createNotNullConstraint(tableInventory.getColumn("quan_in_stock"));
		tableInventory.createNotNullConstraint(tableInventory.getColumn("sales"));

		Table tableOrderlines = this.createTable("orderlines");
		tableOrderlines.createColumn("orderlineid", new IntDataType());
		tableOrderlines.createColumn("orderid", new IntDataType());
		tableOrderlines.createColumn("prod_id", new IntDataType());
		tableOrderlines.createColumn("quantity", new SmallIntDataType());
		tableOrderlines.createColumn("orderdate", new DateDataType());
		tableOrderlines.createNotNullConstraint(tableOrderlines.getColumn("orderlineid"));
		tableOrderlines.createNotNullConstraint(tableOrderlines.getColumn("orderid"));
		tableOrderlines.createNotNullConstraint(tableOrderlines.getColumn("prod_id"));
		tableOrderlines.createNotNullConstraint(tableOrderlines.getColumn("quantity"));
		tableOrderlines.createNotNullConstraint(tableOrderlines.getColumn("orderdate"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("orderid", new IntDataType());
		tableOrders.createColumn("orderdate", new DateDataType());
		tableOrders.createColumn("customerid", new IntDataType());
		tableOrders.createColumn("netamount", new NumericDataType(12, 2));
		tableOrders.createColumn("tax", new NumericDataType(12, 2));
		tableOrders.createColumn("totalamount", new NumericDataType(12, 2));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("orderid"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("orderdate"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("netamount"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("tax"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("totalamount"));

		Table tableProducts = this.createTable("products");
		tableProducts.createColumn("prod_id", new IntDataType());
		tableProducts.createColumn("category", new IntDataType());
		tableProducts.createColumn("title", new VarCharDataType(50));
		tableProducts.createColumn("actor", new VarCharDataType(50));
		tableProducts.createColumn("price", new NumericDataType(12, 2));
		tableProducts.createColumn("special", new SmallIntDataType());
		tableProducts.createColumn("common_prod_id", new IntDataType());
		tableProducts.createNotNullConstraint(tableProducts.getColumn("prod_id"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("category"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("title"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("actor"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("price"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("common_prod_id"));

		Table tableReorder = this.createTable("reorder");
		tableReorder.createColumn("prod_id", new IntDataType());
		tableReorder.createColumn("date_low", new DateDataType());
		tableReorder.createColumn("quan_low", new IntDataType());
		tableReorder.createColumn("date_reordered", new DateDataType());
		tableReorder.createColumn("quan_reordered", new IntDataType());
		tableReorder.createColumn("date_expected", new DateDataType());
		tableReorder.createNotNullConstraint(tableReorder.getColumn("prod_id"));
		tableReorder.createNotNullConstraint(tableReorder.getColumn("date_low"));
		tableReorder.createNotNullConstraint(tableReorder.getColumn("quan_low"));
	}
}

