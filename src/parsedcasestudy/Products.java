package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * Products schema.
 * Java code originally generated: 2013/08/17 00:31:01
 *
 */

@SuppressWarnings("serial")
public class Products extends Schema {

	public Products() {
		super("Products");

		Table tableProducts = this.createTable("products");
		tableProducts.createColumn("product_no", new IntDataType());
		tableProducts.createColumn("name", new VarCharDataType(100));
		tableProducts.createColumn("price", new NumericDataType());
		tableProducts.createColumn("discounted_price", new NumericDataType());
		this.createPrimaryKeyConstraint(tableProducts, tableProducts.getColumn("product_no"));
		this.createCheckConstraint(tableProducts, new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableProducts, new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("discounted_price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableProducts, new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("price")), RelationalOperator.GREATER, new ColumnExpression(tableProducts, tableProducts.getColumn("discounted_price"))));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("product_no"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("name"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("price"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("discounted_price"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("order_id", new IntDataType());
		tableOrders.createColumn("shipping_address", new VarCharDataType(100));
		this.createPrimaryKeyConstraint(tableOrders, tableOrders.getColumn("order_id"));

		Table tableOrderItems = this.createTable("order_items");
		tableOrderItems.createColumn("product_no", new IntDataType());
		tableOrderItems.createColumn("order_id", new IntDataType());
		tableOrderItems.createColumn("quantity", new IntDataType());
		this.createPrimaryKeyConstraint(tableOrderItems, tableOrderItems.getColumn("product_no"), tableOrderItems.getColumn("order_id"));
		this.createCheckConstraint(tableOrderItems, new RelationalExpression(new ColumnExpression(tableOrderItems, tableOrderItems.getColumn("quantity")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		this.createForeignKeyConstraint(tableOrderItems, tableOrderItems.getColumn("product_no"), tableProducts, tableProducts.getColumn("product_no"));
		this.createForeignKeyConstraint(tableOrderItems, tableOrderItems.getColumn("order_id"), tableOrders, tableOrders.getColumn("order_id"));
		this.createNotNullConstraint(tableOrderItems, tableOrderItems.getColumn("quantity"));
	}
}

