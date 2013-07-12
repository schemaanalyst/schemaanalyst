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
 * Java code originally generated: 2013/07/11 14:07:40
 *
 */
@SuppressWarnings("serial")
public class CustomerOrder extends Schema {

    public CustomerOrder() {
        super("CustomerOrder");

        Table tableDbCategory = this.createTable("db_category");
        tableDbCategory.addColumn("id", new VarCharDataType(9));
        tableDbCategory.addColumn("name", new VarCharDataType(30));
        tableDbCategory.addColumn("parent_id", new VarCharDataType(9));
        tableDbCategory.setPrimaryKeyConstraint(tableDbCategory.getColumn("id"));
        tableDbCategory.addForeignKeyConstraint("db_category_parent_fk", tableDbCategory.getColumn("parent_id"), tableDbCategory, tableDbCategory.getColumn("id"));
        tableDbCategory.addNotNullConstraint(tableDbCategory.getColumn("id"));
        tableDbCategory.addNotNullConstraint(tableDbCategory.getColumn("name"));

        Table tableDbProduct = this.createTable("db_product");
        tableDbProduct.addColumn("ean_code", new VarCharDataType(13));
        tableDbProduct.addColumn("name", new VarCharDataType(30));
        tableDbProduct.addColumn("category_id", new VarCharDataType(9));
        tableDbProduct.addColumn("price", new DecimalDataType(8, 2));
        tableDbProduct.addColumn("manufacturer", new VarCharDataType(30));
        tableDbProduct.addColumn("notes", new VarCharDataType(256));
        tableDbProduct.addColumn("description", new VarCharDataType(256));
        tableDbProduct.setPrimaryKeyConstraint(tableDbProduct.getColumn("ean_code"));
        tableDbProduct.addForeignKeyConstraint("db_product_category_fk", tableDbProduct.getColumn("category_id"), tableDbCategory, tableDbCategory.getColumn("id"));
        tableDbProduct.addNotNullConstraint(tableDbProduct.getColumn("ean_code"));
        tableDbProduct.addNotNullConstraint(tableDbProduct.getColumn("name"));
        tableDbProduct.addNotNullConstraint(tableDbProduct.getColumn("category_id"));
        tableDbProduct.addNotNullConstraint(tableDbProduct.getColumn("price"));
        tableDbProduct.addNotNullConstraint(tableDbProduct.getColumn("manufacturer"));

        Table tableDbRole = this.createTable("db_role");
        tableDbRole.addColumn("name", new VarCharDataType(16));
        tableDbRole.setPrimaryKeyConstraint(tableDbRole.getColumn("name"));
        tableDbRole.addNotNullConstraint(tableDbRole.getColumn("name"));

        Table tableDbUser = this.createTable("db_user");
        tableDbUser.addColumn("id", new IntDataType());
        tableDbUser.addColumn("name", new VarCharDataType(30));
        tableDbUser.addColumn("email", new VarCharDataType(50));
        tableDbUser.addColumn("password", new VarCharDataType(16));
        tableDbUser.addColumn("role_id", new VarCharDataType(16));
        tableDbUser.addColumn("active", new SmallIntDataType());
        tableDbUser.setPrimaryKeyConstraint(tableDbUser.getColumn("id"));
        tableDbUser.addForeignKeyConstraint("db_user_role_fk", tableDbUser.getColumn("role_id"), tableDbRole, tableDbRole.getColumn("name"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("id"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("name"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("email"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("password"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("role_id"));
        tableDbUser.addNotNullConstraint(tableDbUser.getColumn("active"));
        tableDbUser.addCheckConstraint("active_flag", new InExpression(new ColumnExpression(tableDbUser.getColumn("active")), new ListExpression(new ConstantExpression(new NumericValue(0)), new ConstantExpression(new NumericValue(1))), false));

        Table tableDbCustomer = this.createTable("db_customer");
        tableDbCustomer.addColumn("id", new IntDataType());
        tableDbCustomer.addColumn("category", new CharDataType(1));
        tableDbCustomer.addColumn("salutation", new VarCharDataType(10));
        tableDbCustomer.addColumn("first_name", new VarCharDataType(30));
        tableDbCustomer.addColumn("last_name", new VarCharDataType(30));
        tableDbCustomer.addColumn("birth_date", new DateDataType());
        tableDbCustomer.setPrimaryKeyConstraint(tableDbCustomer.getColumn("id"));
        tableDbCustomer.addForeignKeyConstraint("db_customer_user_fk", tableDbCustomer.getColumn("id"), tableDbUser, tableDbUser.getColumn("id"));
        tableDbCustomer.addNotNullConstraint(tableDbCustomer.getColumn("id"));
        tableDbCustomer.addNotNullConstraint(tableDbCustomer.getColumn("category"));
        tableDbCustomer.addNotNullConstraint(tableDbCustomer.getColumn("first_name"));
        tableDbCustomer.addNotNullConstraint(tableDbCustomer.getColumn("last_name"));

        Table tableDbOrder = this.createTable("db_order");
        tableDbOrder.addColumn("id", new IntDataType());
        tableDbOrder.addColumn("customer_id", new IntDataType());
        tableDbOrder.addColumn("total_price", new DecimalDataType(8, 2));
        tableDbOrder.addColumn("created_at", new TimestampDataType());
        tableDbOrder.setPrimaryKeyConstraint(tableDbOrder.getColumn("id"));
        tableDbOrder.addForeignKeyConstraint("db_order_customer_fk", tableDbOrder.getColumn("customer_id"), tableDbCustomer, tableDbCustomer.getColumn("id"));
        tableDbOrder.addNotNullConstraint(tableDbOrder.getColumn("id"));
        tableDbOrder.addNotNullConstraint(tableDbOrder.getColumn("customer_id"));
        tableDbOrder.addNotNullConstraint(tableDbOrder.getColumn("total_price"));
        tableDbOrder.addNotNullConstraint(tableDbOrder.getColumn("created_at"));

        Table tableDbOrderItem = this.createTable("db_order_item");
        tableDbOrderItem.addColumn("id", new IntDataType());
        tableDbOrderItem.addColumn("order_id", new IntDataType());
        tableDbOrderItem.addColumn("number_of_items", new IntDataType());
        tableDbOrderItem.addColumn("product_ean_code", new VarCharDataType(13));
        tableDbOrderItem.addColumn("total_price", new DecimalDataType(8, 2));
        tableDbOrderItem.setPrimaryKeyConstraint(tableDbOrderItem.getColumn("id"));
        tableDbOrderItem.addForeignKeyConstraint("db_order_item_order_fk", tableDbOrderItem.getColumn("order_id"), tableDbOrder, tableDbOrder.getColumn("id"));
        tableDbOrderItem.addForeignKeyConstraint("db_order_item_product_fk", tableDbOrderItem.getColumn("product_ean_code"), tableDbProduct, tableDbProduct.getColumn("ean_code"));
        tableDbOrderItem.addNotNullConstraint(tableDbOrderItem.getColumn("id"));
        tableDbOrderItem.addNotNullConstraint(tableDbOrderItem.getColumn("order_id"));
        tableDbOrderItem.addNotNullConstraint(tableDbOrderItem.getColumn("number_of_items"));
        tableDbOrderItem.addNotNullConstraint(tableDbOrderItem.getColumn("product_ean_code"));
        tableDbOrderItem.addNotNullConstraint(tableDbOrderItem.getColumn("total_price"));
    }
}
