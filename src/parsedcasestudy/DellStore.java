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
 * Java code originally generated: 2013/07/11 14:07:49
 *
 */

@SuppressWarnings("serial")
public class DellStore extends Schema {

	public DellStore() {
		super("DellStore");

		Table tableCategories = this.createTable("categories");
		tableCategories.addColumn("category", new IntDataType());
		tableCategories.addColumn("categoryname", new VarCharDataType(50));
		tableCategories.addNotNullConstraint(tableCategories.getColumn("category"));
		tableCategories.addNotNullConstraint(tableCategories.getColumn("categoryname"));

		Table tableCustHist = this.createTable("cust_hist");
		tableCustHist.addColumn("customerid", new IntDataType());
		tableCustHist.addColumn("orderid", new IntDataType());
		tableCustHist.addColumn("prod_id", new IntDataType());
		tableCustHist.addNotNullConstraint(tableCustHist.getColumn("customerid"));
		tableCustHist.addNotNullConstraint(tableCustHist.getColumn("orderid"));
		tableCustHist.addNotNullConstraint(tableCustHist.getColumn("prod_id"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.addColumn("customerid", new IntDataType());
		tableCustomers.addColumn("firstname", new VarCharDataType(50));
		tableCustomers.addColumn("lastname", new VarCharDataType(50));
		tableCustomers.addColumn("address1", new VarCharDataType(50));
		tableCustomers.addColumn("address2", new VarCharDataType(50));
		tableCustomers.addColumn("city", new VarCharDataType(50));
		tableCustomers.addColumn("state", new VarCharDataType(50));
		tableCustomers.addColumn("zip", new IntDataType());
		tableCustomers.addColumn("country", new VarCharDataType(50));
		tableCustomers.addColumn("region", new SmallIntDataType());
		tableCustomers.addColumn("email", new VarCharDataType(50));
		tableCustomers.addColumn("phone", new VarCharDataType(50));
		tableCustomers.addColumn("creditcardtype", new IntDataType());
		tableCustomers.addColumn("creditcard", new VarCharDataType(50));
		tableCustomers.addColumn("creditcardexpiration", new VarCharDataType(50));
		tableCustomers.addColumn("username", new VarCharDataType(50));
		tableCustomers.addColumn("password", new VarCharDataType(50));
		tableCustomers.addColumn("age", new SmallIntDataType());
		tableCustomers.addColumn("income", new IntDataType());
		tableCustomers.addColumn("gender", new VarCharDataType(1));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("customerid"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("firstname"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("lastname"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("address1"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("city"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("country"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("region"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("creditcardtype"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("creditcard"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("creditcardexpiration"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("username"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("password"));

		Table tableInventory = this.createTable("inventory");
		tableInventory.addColumn("prod_id", new IntDataType());
		tableInventory.addColumn("quan_in_stock", new IntDataType());
		tableInventory.addColumn("sales", new IntDataType());
		tableInventory.addNotNullConstraint(tableInventory.getColumn("prod_id"));
		tableInventory.addNotNullConstraint(tableInventory.getColumn("quan_in_stock"));
		tableInventory.addNotNullConstraint(tableInventory.getColumn("sales"));

		Table tableOrderlines = this.createTable("orderlines");
		tableOrderlines.addColumn("orderlineid", new IntDataType());
		tableOrderlines.addColumn("orderid", new IntDataType());
		tableOrderlines.addColumn("prod_id", new IntDataType());
		tableOrderlines.addColumn("quantity", new SmallIntDataType());
		tableOrderlines.addColumn("orderdate", new DateDataType());
		tableOrderlines.addNotNullConstraint(tableOrderlines.getColumn("orderlineid"));
		tableOrderlines.addNotNullConstraint(tableOrderlines.getColumn("orderid"));
		tableOrderlines.addNotNullConstraint(tableOrderlines.getColumn("prod_id"));
		tableOrderlines.addNotNullConstraint(tableOrderlines.getColumn("quantity"));
		tableOrderlines.addNotNullConstraint(tableOrderlines.getColumn("orderdate"));

		Table tableOrders = this.createTable("orders");
		tableOrders.addColumn("orderid", new IntDataType());
		tableOrders.addColumn("orderdate", new DateDataType());
		tableOrders.addColumn("customerid", new IntDataType());
		tableOrders.addColumn("netamount", new NumericDataType(12, 2));
		tableOrders.addColumn("tax", new NumericDataType(12, 2));
		tableOrders.addColumn("totalamount", new NumericDataType(12, 2));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("orderid"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("orderdate"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("netamount"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("tax"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("totalamount"));

		Table tableProducts = this.createTable("products");
		tableProducts.addColumn("prod_id", new IntDataType());
		tableProducts.addColumn("category", new IntDataType());
		tableProducts.addColumn("title", new VarCharDataType(50));
		tableProducts.addColumn("actor", new VarCharDataType(50));
		tableProducts.addColumn("price", new NumericDataType(12, 2));
		tableProducts.addColumn("special", new SmallIntDataType());
		tableProducts.addColumn("common_prod_id", new IntDataType());
		tableProducts.addNotNullConstraint(tableProducts.getColumn("prod_id"));
		tableProducts.addNotNullConstraint(tableProducts.getColumn("category"));
		tableProducts.addNotNullConstraint(tableProducts.getColumn("title"));
		tableProducts.addNotNullConstraint(tableProducts.getColumn("actor"));
		tableProducts.addNotNullConstraint(tableProducts.getColumn("price"));
		tableProducts.addNotNullConstraint(tableProducts.getColumn("common_prod_id"));

		Table tableReorder = this.createTable("reorder");
		tableReorder.addColumn("prod_id", new IntDataType());
		tableReorder.addColumn("date_low", new DateDataType());
		tableReorder.addColumn("quan_low", new IntDataType());
		tableReorder.addColumn("date_reordered", new DateDataType());
		tableReorder.addColumn("quan_reordered", new IntDataType());
		tableReorder.addColumn("date_expected", new DateDataType());
		tableReorder.addNotNullConstraint(tableReorder.getColumn("prod_id"));
		tableReorder.addNotNullConstraint(tableReorder.getColumn("date_low"));
		tableReorder.addNotNullConstraint(tableReorder.getColumn("quan_low"));
	}
}

