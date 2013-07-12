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
 * Java code originally generated: 2013/07/11 14:10:54
 *
 */
@SuppressWarnings("serial")
public class Products extends Schema {

    public Products() {
        super("Products");

        Table tableProducts = this.createTable("products");
        tableProducts.addColumn("product_no", new IntDataType());
        tableProducts.addColumn("name", new VarCharDataType(100));
        tableProducts.addColumn("price", new NumericDataType());
        tableProducts.addColumn("discounted_price", new NumericDataType());
        tableProducts.setPrimaryKeyConstraint(tableProducts.getColumn("product_no"));
        tableProducts.addNotNullConstraint(tableProducts.getColumn("product_no"));
        tableProducts.addNotNullConstraint(tableProducts.getColumn("name"));
        tableProducts.addNotNullConstraint(tableProducts.getColumn("price"));
        tableProducts.addNotNullConstraint(tableProducts.getColumn("discounted_price"));
        tableProducts.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts.getColumn("price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
        tableProducts.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts.getColumn("discounted_price")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
        tableProducts.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableProducts.getColumn("price")), RelationalOperator.GREATER, new ColumnExpression(tableProducts.getColumn("discounted_price"))));

        Table tableOrders = this.createTable("orders");
        tableOrders.addColumn("order_id", new IntDataType());
        tableOrders.addColumn("shipping_address", new VarCharDataType(100));
        tableOrders.setPrimaryKeyConstraint(tableOrders.getColumn("order_id"));

        Table tableOrderItems = this.createTable("order_items");
        tableOrderItems.addColumn("product_no", new IntDataType());
        tableOrderItems.addColumn("order_id", new IntDataType());
        tableOrderItems.addColumn("quantity", new IntDataType());
        tableOrderItems.setPrimaryKeyConstraint(tableOrderItems.getColumn("product_no"), tableOrderItems.getColumn("order_id"));
        tableOrderItems.addForeignKeyConstraint(tableOrderItems.getColumn("product_no"), tableProducts, tableProducts.getColumn("product_no"));
        tableOrderItems.addForeignKeyConstraint(tableOrderItems.getColumn("order_id"), tableOrders, tableOrders.getColumn("order_id"));
        tableOrderItems.addNotNullConstraint(tableOrderItems.getColumn("quantity"));
        tableOrderItems.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableOrderItems.getColumn("quantity")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
    }
}
