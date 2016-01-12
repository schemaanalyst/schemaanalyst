package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/*
 * CustomerOrder schema.
 * Java code originally generated: 2013/08/17 00:30:31
 *
 */

@SuppressWarnings("serial")
public class CustomerOrder extends Schema {

	public CustomerOrder() {
		super("CustomerOrder");

		Table tableDbCategory = this.createTable("db_category");
		tableDbCategory.createColumn("id", new VarCharDataType(9));
		tableDbCategory.createColumn("name", new VarCharDataType(30));
		tableDbCategory.createColumn("parent_id", new VarCharDataType(9));
		this.createPrimaryKeyConstraint(tableDbCategory, tableDbCategory.getColumn("id"));
		this.createForeignKeyConstraint("db_category_parent_fk", tableDbCategory, tableDbCategory.getColumn("parent_id"), tableDbCategory, tableDbCategory.getColumn("id"));
		this.createNotNullConstraint(tableDbCategory, tableDbCategory.getColumn("id"));
		this.createNotNullConstraint(tableDbCategory, tableDbCategory.getColumn("name"));

		Table tableDbProduct = this.createTable("db_product");
		tableDbProduct.createColumn("ean_code", new VarCharDataType(13));
		tableDbProduct.createColumn("name", new VarCharDataType(30));
		tableDbProduct.createColumn("category_id", new VarCharDataType(9));
		tableDbProduct.createColumn("price", new DecimalDataType(8, 2));
		tableDbProduct.createColumn("manufacturer", new VarCharDataType(30));
		tableDbProduct.createColumn("notes", new VarCharDataType(256));
		tableDbProduct.createColumn("description", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableDbProduct, tableDbProduct.getColumn("ean_code"));
		this.createForeignKeyConstraint("db_product_category_fk", tableDbProduct, tableDbProduct.getColumn("category_id"), tableDbCategory, tableDbCategory.getColumn("id"));
		this.createNotNullConstraint(tableDbProduct, tableDbProduct.getColumn("ean_code"));
		this.createNotNullConstraint(tableDbProduct, tableDbProduct.getColumn("name"));
		this.createNotNullConstraint(tableDbProduct, tableDbProduct.getColumn("category_id"));
		this.createNotNullConstraint(tableDbProduct, tableDbProduct.getColumn("price"));
		this.createNotNullConstraint(tableDbProduct, tableDbProduct.getColumn("manufacturer"));

		Table tableDbRole = this.createTable("db_role");
		tableDbRole.createColumn("name", new VarCharDataType(16));
		this.createPrimaryKeyConstraint(tableDbRole, tableDbRole.getColumn("name"));
		this.createNotNullConstraint(tableDbRole, tableDbRole.getColumn("name"));

		Table tableDbUser = this.createTable("db_user");
		tableDbUser.createColumn("id", new IntDataType());
		tableDbUser.createColumn("name", new VarCharDataType(30));
		tableDbUser.createColumn("email", new VarCharDataType(50));
		tableDbUser.createColumn("password", new VarCharDataType(16));
		tableDbUser.createColumn("role_id", new VarCharDataType(16));
		tableDbUser.createColumn("active", new SmallIntDataType());
		this.createPrimaryKeyConstraint(tableDbUser, tableDbUser.getColumn("id"));
		this.createCheckConstraint("active_flag", tableDbUser, new InExpression(new ColumnExpression(tableDbUser, tableDbUser.getColumn("active")), new ListExpression(new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(1))), false));
		this.createForeignKeyConstraint("db_user_role_fk", tableDbUser, tableDbUser.getColumn("role_id"), tableDbRole, tableDbRole.getColumn("name"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("id"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("name"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("email"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("password"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("role_id"));
		this.createNotNullConstraint(tableDbUser, tableDbUser.getColumn("active"));

		Table tableDbCustomer = this.createTable("db_customer");
		tableDbCustomer.createColumn("id", new IntDataType());
		tableDbCustomer.createColumn("category", new CharDataType(1));
		tableDbCustomer.createColumn("salutation", new VarCharDataType(10));
		tableDbCustomer.createColumn("first_name", new VarCharDataType(30));
		tableDbCustomer.createColumn("last_name", new VarCharDataType(30));
		tableDbCustomer.createColumn("birth_date", new DateDataType());
		this.createPrimaryKeyConstraint(tableDbCustomer, tableDbCustomer.getColumn("id"));
		this.createForeignKeyConstraint("db_customer_user_fk", tableDbCustomer, tableDbCustomer.getColumn("id"), tableDbUser, tableDbUser.getColumn("id"));
		this.createNotNullConstraint(tableDbCustomer, tableDbCustomer.getColumn("id"));
		this.createNotNullConstraint(tableDbCustomer, tableDbCustomer.getColumn("category"));
		this.createNotNullConstraint(tableDbCustomer, tableDbCustomer.getColumn("first_name"));
		this.createNotNullConstraint(tableDbCustomer, tableDbCustomer.getColumn("last_name"));

		Table tableDbOrder = this.createTable("db_order");
		tableDbOrder.createColumn("id", new IntDataType());
		tableDbOrder.createColumn("customer_id", new IntDataType());
		tableDbOrder.createColumn("total_price", new DecimalDataType(8, 2));
		tableDbOrder.createColumn("created_at", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableDbOrder, tableDbOrder.getColumn("id"));
		this.createForeignKeyConstraint("db_order_customer_fk", tableDbOrder, tableDbOrder.getColumn("customer_id"), tableDbCustomer, tableDbCustomer.getColumn("id"));
		this.createNotNullConstraint(tableDbOrder, tableDbOrder.getColumn("id"));
		this.createNotNullConstraint(tableDbOrder, tableDbOrder.getColumn("customer_id"));
		this.createNotNullConstraint(tableDbOrder, tableDbOrder.getColumn("total_price"));
		this.createNotNullConstraint(tableDbOrder, tableDbOrder.getColumn("created_at"));

		Table tableDbOrderItem = this.createTable("db_order_item");
		tableDbOrderItem.createColumn("id", new IntDataType());
		tableDbOrderItem.createColumn("order_id", new IntDataType());
		tableDbOrderItem.createColumn("number_of_items", new IntDataType());
		tableDbOrderItem.createColumn("product_ean_code", new VarCharDataType(13));
		tableDbOrderItem.createColumn("total_price", new DecimalDataType(8, 2));
		this.createPrimaryKeyConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("id"));
		this.createForeignKeyConstraint("db_order_item_order_fk", tableDbOrderItem, tableDbOrderItem.getColumn("order_id"), tableDbOrder, tableDbOrder.getColumn("id"));
		this.createForeignKeyConstraint("db_order_item_product_fk", tableDbOrderItem, tableDbOrderItem.getColumn("product_ean_code"), tableDbProduct, tableDbProduct.getColumn("ean_code"));
		this.createNotNullConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("id"));
		this.createNotNullConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("order_id"));
		this.createNotNullConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("number_of_items"));
		this.createNotNullConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("product_ean_code"));
		this.createNotNullConstraint(tableDbOrderItem, tableDbOrderItem.getColumn("total_price"));
	}
}

