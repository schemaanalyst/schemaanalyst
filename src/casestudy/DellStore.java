package casestudy;

import casestudy.runner.InstantiateSchema;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class DellStore extends Schema {

    static final long serialVersionUID = 3024026832734934489L;
	
	@SuppressWarnings("unused")
	public DellStore() {
		super("DellStore");
		
		/*

		  CREATE TABLE categories (
		  category int NOT NULL,
		  categoryname character varying(50) NOT NULL
		  );

		  serial --> int
		  
		*/		
		
		Table categoriesTable = createTable("categories");
		
		Column categoryColumn = categoriesTable.addColumn("category", new IntColumnType());
		Column categoryNameColumn = categoriesTable.addColumn("categoryname", new VarCharColumnType(50));
		    
		categoryColumn.setNotNull();
		categoryNameColumn.setNotNull();

		/*

		  CREATE TABLE cust_hist (
		  customerid integer NOT NULL,
		  orderid integer NOT NULL,
		  prod_id integer NOT NULL
		  );

		 */

		Table custHistTable = createTable("cust_hist");
		
		Column customerIdColumn = custHistTable.addColumn("customerid", new IntegerColumnType());
		Column orderIdColumn = custHistTable.addColumn("orderid", new IntegerColumnType());
		Column prodIdColumn = custHistTable.addColumn("prodid", new IntegerColumnType());

		customerIdColumn.setNotNull();
		orderIdColumn.setNotNull();
		prodIdColumn.setNotNull();
		prodIdColumn.setNotNull();

		/*

		  CREATE TABLE customers (
		  customerid int NOT NULL,
		  firstname character varying(50) NOT NULL,
		  lastname character varying(50) NOT NULL,
		  address1 character varying(50) NOT NULL,
		  address2 character varying(50),
		  city character varying(50) NOT NULL,
		  state character varying(50),
		  zip integer,
		  country character varying(50) NOT NULL,
		  region int NOT NULL,
		  email character varying(50),
		  phone character varying(50),
		  creditcardtype integer NOT NULL,
		  creditcard character varying(50) NOT NULL,
		  creditcardexpiration character varying(50) NOT NULL,
		  username character varying(50) NOT NULL,
		  "password" character varying(50) NOT NULL,
		  age int,
		  income integer,
		  gender character varying(1)
		  );

		 */
		
		Table customersTable = createTable("customers");

		Column customerIdCustHistColumn = customersTable.addColumn("customerid" , new IntegerColumnType());
		Column firstNameColumn = customersTable.addColumn("firstname", new VarCharColumnType(50));
		Column lastNameColumn = customersTable.addColumn("lastname", new VarCharColumnType(50));
		Column addressOneColumn = customersTable.addColumn("address1", new VarCharColumnType(50));
		Column addressTwoColumn = customersTable.addColumn("address2", new VarCharColumnType(50));
		Column cityColumn = customersTable.addColumn("city", new VarCharColumnType(50));
		Column stateColumn = customersTable.addColumn("state", new VarCharColumnType(50));
		Column zipColumn = customersTable.addColumn("zip" , new IntegerColumnType());
		Column countryColumn = customersTable.addColumn("country", new VarCharColumnType(50));
		Column regionColumn = customersTable.addColumn("region", new IntegerColumnType());
		Column emailColumn = customersTable.addColumn("email", new VarCharColumnType(50));
		Column phoneColumn = customersTable.addColumn("phone", new VarCharColumnType(50));
		Column creditCardTypeColumn = customersTable.addColumn("creditcardtype", new VarCharColumnType(50));
		Column creditCardColumn = customersTable.addColumn("creditcard", new VarCharColumnType(50));
		Column creditCardExpirationColumn = customersTable.addColumn("creditcardexpiration", 
							       new VarCharColumnType(50));
		Column usernameColumn = customersTable.addColumn("username", new VarCharColumnType(50));
		Column passwordColumn = customersTable.addColumn("password", new VarCharColumnType(50));
		Column ageColumn = customersTable.addColumn("age", new IntegerColumnType());
		Column incomeColumn = customersTable.addColumn("income", new IntegerColumnType());
		Column genderColumn = customersTable.addColumn("gender", new VarCharColumnType(1));

		customerIdColumn.setNotNull();
		firstNameColumn.setNotNull();
		lastNameColumn.setNotNull();
		addressOneColumn.setNotNull();
		cityColumn.setNotNull();
		stateColumn.setNotNull();
		countryColumn.setNotNull();
		regionColumn.setNotNull();
		creditCardTypeColumn.setNotNull();
		creditCardColumn.setNotNull();
		creditCardExpirationColumn.setNotNull();
		usernameColumn.setNotNull();
		passwordColumn.setNotNull();
		
		/*

		  CREATE TABLE inventory (
		  prod_id integer NOT NULL,
		  quan_in_stock integer NOT NULL,
		  sales integer NOT NULL
		  );

		*/

		Table inventoryTable = createTable("inventory");
		
		Column prodIdInventoryColumn = inventoryTable.addColumn("prod_id" , new IntegerColumnType());
		Column quanInStockColumn = inventoryTable.addColumn("quan_in_stock" , new IntegerColumnType());
		Column salesColumn = inventoryTable.addColumn("sales", new IntegerColumnType());
		
		prodIdInventoryColumn.setNotNull();
		quanInStockColumn.setNotNull();
		salesColumn.setNotNull();

		/*

		  CREATE TABLE orderlines (
		  orderlineid integer NOT NULL,
		  orderid integer NOT NULL,
		  prod_id integer NOT NULL,
		  quantity int NOT NULL,
		  orderdate date NOT NULL
		  );

		 */

		Table orderLinesTable = createTable("orderlines");
		
		Column orderLineIdColumn = orderLinesTable.addColumn("orderlineid" , new IntegerColumnType());
		Column orderIdOrderLinesColumn = orderLinesTable.addColumn("orderid" , new IntegerColumnType());
		Column prodIdOrderLinesColumn = orderLinesTable.addColumn("prod_id" , new IntegerColumnType());
		Column quantityColumn = orderLinesTable.addColumn("quantity" , new IntegerColumnType());
		Column dateColumn = orderLinesTable.addColumn("orderdate" , new DateColumnType());

                orderLineIdColumn.setNotNull();
                orderIdOrderLinesColumn.setNotNull();
                prodIdOrderLinesColumn.setNotNull();
                quantityColumn.setNotNull();
                dateColumn.setNotNull();

		/*

		  CREATE TABLE orders (
		  orderid integer NOT NULL,
		  orderdate date NOT NULL,
		  customerid integer,
		  netamount numeric(12,2) NOT NULL,
		  tax numeric(12,2) NOT NULL,
		  totalamount numeric(12,2) NOT NULL
		  );

		 */

		Table ordersTable = createTable("orders");

		Column orderIdOrdersColumn = ordersTable.addColumn("orderid" , new IntegerColumnType());
		Column orderDateColumn = ordersTable.addColumn("orderdate" , new DateColumnType());
		Column customerIdOrdersColumn = ordersTable.addColumn("customerid" , new IntegerColumnType());
		Column netAmountColumn = ordersTable.addColumn("netamount" , new IntegerColumnType());
		Column taxColumn = ordersTable.addColumn("tax" , new IntegerColumnType());
		Column totalAmountColumn = ordersTable.addColumn("totalamount" , new IntegerColumnType());
		
		orderIdOrdersColumn.setNotNull();
		orderDateColumn.setNotNull();
		netAmountColumn.setNotNull();
		taxColumn.setNotNull();
		totalAmountColumn.setNotNull();
		
		/*

		  CREATE TABLE products (
		  prod_id int NOT NULL,
		  category integer NOT NULL,
		  title character varying(50) NOT NULL,
		  actor character varying(50) NOT NULL,
		  price numeric(12,2) NOT NULL,
		  special int,
		  common_prod_id integer NOT NULL
		  );
		  
		 */

		Table productsTable = createTable("products");
		
		Column prodIdProductsColumn = productsTable.addColumn("prod_id" , new IntegerColumnType());
		Column categoryProductsColumn = productsTable.addColumn("category" , new IntegerColumnType());
		Column titleColumn = productsTable.addColumn("title" , new VarCharColumnType(50));
		Column actorColumn = productsTable.addColumn("actor", new VarCharColumnType(50));
		Column priceColumn = productsTable.addColumn("price", new IntegerColumnType());
		Column specialColumn = productsTable.addColumn("special" , new IntegerColumnType());
		Column commonProdIdColumn = productsTable.addColumn("commmon_prod_id" , new IntegerColumnType());
		
		prodIdProductsColumn.setNotNull();
		categoryProductsColumn.setNotNull();
		titleColumn.setNotNull();
		actorColumn.setNotNull();
		priceColumn.setNotNull();
		commonProdIdColumn.setNotNull();

		/*

		  CREATE TABLE reorder (
		  prod_id integer NOT NULL,
		  date_low date NOT NULL,
		  quan_low integer NOT NULL,
		  date_reordered date,
		  quan_reordered integer,
		  date_expected date
		  );

		 */

		Table reorderTable = createTable( "reorder");
		
		Column prodIdReorderColumn = reorderTable.addColumn("prod_id" , new IntegerColumnType());
		Column dateLowColumn = reorderTable.addColumn("date_low" , new DateColumnType());
		Column quanLowColumn = reorderTable.addColumn("quan_low" , new IntegerColumnType());
		Column dateReorderedColumn = reorderTable.addColumn("date_reordered" , new DateColumnType());
		Column quanReorderedColumn = reorderTable.addColumn("quan_reordered" , new IntegerColumnType());
		Column dateExpectedColumn = reorderTable.addColumn("date_expected" , new DateColumnType());

	}
}
