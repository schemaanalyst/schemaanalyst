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
 * Java code originally generated: 2013/08/15 10:52:15
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
		tableProducts.createPrimaryKeyConstraint(tableProducts.getColumn("product_no"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("product_no"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("name"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("price"));
		tableProducts.createNotNullConstraint(tableProducts.getColumn("discounted_price"));
		tableProducts.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		tableProducts.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("discounted_price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		tableProducts.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts, tableProducts.getColumn("price")), RelationalOperator.GREATER, new ColumnExpression(tableProducts, tableProducts.getColumn("discounted_price"))));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("order_id", new IntDataType());
		tableOrders.createColumn("shipping_address", new VarCharDataType(100));
		tableOrders.createPrimaryKeyConstraint(tableOrders.getColumn("order_id"));

		Table tableOrderItems = this.createTable("order_items");
		tableOrderItems.createColumn("product_no", new IntDataType());
		tableOrderItems.createColumn("order_id", new IntDataType());
		tableOrderItems.createColumn("quantity", new IntDataType());
		tableOrderItems.createPrimaryKeyConstraint(tableOrderItems.getColumn("product_no"), tableOrderItems.getColumn("order_id"));
		tableOrderItems.createForeignKeyConstraint(tableOrderItems.getColumn("product_no"), tableProducts, tableOrderItems.getColumn("product_no"));
		tableOrderItems.createForeignKeyConstraint(tableOrderItems.getColumn("order_id"), tableOrders, tableOrderItems.getColumn("order_id"));
		tableOrderItems.createNotNullConstraint(tableOrderItems.getColumn("quantity"));
		tableOrderItems.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableOrderItems, tableOrderItems.getColumn("quantity")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
	}
}

