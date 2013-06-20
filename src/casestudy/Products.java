package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class Products extends Schema {

    static final long serialVersionUID = 8520782033384863844L;
	
	public Products() {
		super("Products");
		
		/*
		  
		  CREATE TABLE products (
		  product_no integer PRIMARY KEY NOT NULL,
		  name varchar(100) NOT NULL,
		  price numeric NOT NULL,
		  CHECK (price > 0),
		  discounted_price numeric NOT NULL,
		  CHECK (discounted_price > 0),
		  CHECK (price > discounted_price)
		  );

		*/

		Table productsTable = createTable( "products");

		Column productNo = productsTable .addColumn("product_no" , new IntDataType());
		productNo.setNotNull();
		productsTable.setPrimaryKeyConstraint(productNo);
		
		Column name = productsTable .addColumn("name" , new VarCharDataType(100));
		name.setNotNull();
		
		Column price = productsTable .addColumn("price" , new NumericDataType(100));
		price.setNotNull();
		productsTable.addCheckConstraint(new RelationalCheckCondition(price, ">", 0));

		Column discounted_price = productsTable .addColumn("discounted_price" , new NumericDataType(100));
		discounted_price.setNotNull();
		productsTable.addCheckConstraint(new RelationalCheckCondition(discounted_price, ">", 0));
		productsTable.addCheckConstraint(new RelationalCheckCondition(price , ">" , discounted_price));

		/*

		  CREATE TABLE orders (
		  order_id integer PRIMARY KEY,
		  shipping_address varchar(100)
		  );

		 */

		Table ordersTable = createTable( "orders");
		
		Column orderId = ordersTable .addColumn("order_id" , new IntDataType());
		ordersTable.setPrimaryKeyConstraint(orderId);

		@SuppressWarnings("unused")
		Column shippingAddress = ordersTable .addColumn("shipping_addres" , new VarCharDataType(100));
				
		/*

		  CREATE TABLE order_items (
		  product_no integer REFERENCES products,
		  order_id integer REFERENCES orders,
		  quantity integer NOT NULL,
		  PRIMARY KEY (product_no, order_id),
		  CHECK (quantity > 0)
		  );
		  
		 */

		Table orderItemsTable = createTable( "order_items");

		Column productNumber = orderItemsTable .addColumn("product_no" , new IntDataType());
		productNumber.setForeignKey(productsTable, productNo);

		Column orderIdAgain = orderItemsTable .addColumn("order_id" , new IntDataType());
		orderIdAgain.setForeignKey(ordersTable, orderId);

		Column quantity = orderItemsTable .addColumn("quantity" , new IntDataType());
		quantity.setNotNull();
		
		orderItemsTable.setPrimaryKeyConstraint(productNumber, orderIdAgain);
		orderItemsTable.addCheckConstraint(new RelationalCheckCondition(quantity, ">", 0));
				
	}
}
