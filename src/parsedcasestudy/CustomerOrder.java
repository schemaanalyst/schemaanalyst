package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/*
 * CustomerOrder schema.
 * Java code originally generated: 2013/08/15 23:00:06
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
		tableDbCategory.createPrimaryKeyConstraint(tableDbCategory.getColumn("id"));
		tableDbCategory.createForeignKeyConstraint("db_category_parent_fk", tableDbCategory.getColumn("parent_id"), tableDbCategory, tableDbCategory.getColumn("id"));
		tableDbCategory.createNotNullConstraint(tableDbCategory.getColumn("id"));
		tableDbCategory.createNotNullConstraint(tableDbCategory.getColumn("name"));

		Table tableDbProduct = this.createTable("db_product");
		tableDbProduct.createColumn("ean_code", new VarCharDataType(13));
		tableDbProduct.createColumn("name", new VarCharDataType(30));
		tableDbProduct.createColumn("category_id", new VarCharDataType(9));
		tableDbProduct.createColumn("price", new DecimalDataType(8, 2));
		tableDbProduct.createColumn("manufacturer", new VarCharDataType(30));
		tableDbProduct.createColumn("notes", new VarCharDataType(256));
		tableDbProduct.createColumn("description", new VarCharDataType(256));
		tableDbProduct.createPrimaryKeyConstraint(tableDbProduct.getColumn("ean_code"));
		tableDbProduct.createForeignKeyConstraint("db_product_category_fk", tableDbProduct.getColumn("category_id"), tableDbCategory, tableDbCategory.getColumn("id"));
		tableDbProduct.createNotNullConstraint(tableDbProduct.getColumn("ean_code"));
		tableDbProduct.createNotNullConstraint(tableDbProduct.getColumn("name"));
		tableDbProduct.createNotNullConstraint(tableDbProduct.getColumn("category_id"));
		tableDbProduct.createNotNullConstraint(tableDbProduct.getColumn("price"));
		tableDbProduct.createNotNullConstraint(tableDbProduct.getColumn("manufacturer"));

		Table tableDbRole = this.createTable("db_role");
		tableDbRole.createColumn("name", new VarCharDataType(16));
		tableDbRole.createPrimaryKeyConstraint(tableDbRole.getColumn("name"));
		tableDbRole.createNotNullConstraint(tableDbRole.getColumn("name"));

		Table tableDbUser = this.createTable("db_user");
		tableDbUser.createColumn("id", new IntDataType());
		tableDbUser.createColumn("name", new VarCharDataType(30));
		tableDbUser.createColumn("email", new VarCharDataType(50));
		tableDbUser.createColumn("password", new VarCharDataType(16));
		tableDbUser.createColumn("role_id", new VarCharDataType(16));
		tableDbUser.createColumn("active", new SmallIntDataType());
		tableDbUser.createPrimaryKeyConstraint(tableDbUser.getColumn("id"));
		tableDbUser.createForeignKeyConstraint("db_user_role_fk", tableDbUser.getColumn("role_id"), tableDbRole, tableDbRole.getColumn("name"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("id"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("name"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("email"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("password"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("role_id"));
		tableDbUser.createNotNullConstraint(tableDbUser.getColumn("active"));
		tableDbUser.createCheckConstraint("active_flag", new InExpression(new ColumnExpression(tableDbUser, tableDbUser.getColumn("active")), new ListExpression(new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(1))), false));

		Table tableDbCustomer = this.createTable("db_customer");
		tableDbCustomer.createColumn("id", new IntDataType());
		tableDbCustomer.createColumn("category", new CharDataType(1));
		tableDbCustomer.createColumn("salutation", new VarCharDataType(10));
		tableDbCustomer.createColumn("first_name", new VarCharDataType(30));
		tableDbCustomer.createColumn("last_name", new VarCharDataType(30));
		tableDbCustomer.createColumn("birth_date", new DateDataType());
		tableDbCustomer.createPrimaryKeyConstraint(tableDbCustomer.getColumn("id"));
		tableDbCustomer.createForeignKeyConstraint("db_customer_user_fk", tableDbCustomer.getColumn("id"), tableDbUser, tableDbUser.getColumn("id"));
		tableDbCustomer.createNotNullConstraint(tableDbCustomer.getColumn("id"));
		tableDbCustomer.createNotNullConstraint(tableDbCustomer.getColumn("category"));
		tableDbCustomer.createNotNullConstraint(tableDbCustomer.getColumn("first_name"));
		tableDbCustomer.createNotNullConstraint(tableDbCustomer.getColumn("last_name"));

		Table tableDbOrder = this.createTable("db_order");
		tableDbOrder.createColumn("id", new IntDataType());
		tableDbOrder.createColumn("customer_id", new IntDataType());
		tableDbOrder.createColumn("total_price", new DecimalDataType(8, 2));
		tableDbOrder.createColumn("created_at", new TimestampDataType());
		tableDbOrder.createPrimaryKeyConstraint(tableDbOrder.getColumn("id"));
		tableDbOrder.createForeignKeyConstraint("db_order_customer_fk", tableDbOrder.getColumn("customer_id"), tableDbCustomer, tableDbCustomer.getColumn("id"));
		tableDbOrder.createNotNullConstraint(tableDbOrder.getColumn("id"));
		tableDbOrder.createNotNullConstraint(tableDbOrder.getColumn("customer_id"));
		tableDbOrder.createNotNullConstraint(tableDbOrder.getColumn("total_price"));
		tableDbOrder.createNotNullConstraint(tableDbOrder.getColumn("created_at"));

		Table tableDbOrderItem = this.createTable("db_order_item");
		tableDbOrderItem.createColumn("id", new IntDataType());
		tableDbOrderItem.createColumn("order_id", new IntDataType());
		tableDbOrderItem.createColumn("number_of_items", new IntDataType());
		tableDbOrderItem.createColumn("product_ean_code", new VarCharDataType(13));
		tableDbOrderItem.createColumn("total_price", new DecimalDataType(8, 2));
		tableDbOrderItem.createPrimaryKeyConstraint(tableDbOrderItem.getColumn("id"));
		tableDbOrderItem.createForeignKeyConstraint("db_order_item_order_fk", tableDbOrderItem.getColumn("order_id"), tableDbOrder, tableDbOrder.getColumn("id"));
		tableDbOrderItem.createForeignKeyConstraint("db_order_item_product_fk", tableDbOrderItem.getColumn("product_ean_code"), tableDbProduct, tableDbProduct.getColumn("ean_code"));
		tableDbOrderItem.createNotNullConstraint(tableDbOrderItem.getColumn("id"));
		tableDbOrderItem.createNotNullConstraint(tableDbOrderItem.getColumn("order_id"));
		tableDbOrderItem.createNotNullConstraint(tableDbOrderItem.getColumn("number_of_items"));
		tableDbOrderItem.createNotNullConstraint(tableDbOrderItem.getColumn("product_ean_code"));
		tableDbOrderItem.createNotNullConstraint(tableDbOrderItem.getColumn("total_price"));
	}
}

