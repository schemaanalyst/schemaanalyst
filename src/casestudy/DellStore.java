package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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
		
		Column categoryColumn = categoriesTable.addColumn("category", new IntDataType());
		Column categoryNameColumn = categoriesTable.addColumn("categoryname", new VarCharDataType(50));
		    
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
		
		Column customerIdColumn = custHistTable.addColumn("customerid", new IntDataType());
		Column orderIdColumn = custHistTable.addColumn("orderid", new IntDataType());
		Column prodIdColumn = custHistTable.addColumn("prodid", new IntDataType());

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

		Column customerIdCustHistColumn = customersTable.addColumn("customerid" , new IntDataType());
		Column firstNameColumn = customersTable.addColumn("firstname", new VarCharDataType(50));
		Column lastNameColumn = customersTable.addColumn("lastname", new VarCharDataType(50));
		Column addressOneColumn = customersTable.addColumn("address1", new VarCharDataType(50));
		Column addressTwoColumn = customersTable.addColumn("address2", new VarCharDataType(50));
		Column cityColumn = customersTable.addColumn("city", new VarCharDataType(50));
		Column stateColumn = customersTable.addColumn("state", new VarCharDataType(50));
		Column zipColumn = customersTable.addColumn("zip" , new IntDataType());
		Column countryColumn = customersTable.addColumn("country", new VarCharDataType(50));
		Column regionColumn = customersTable.addColumn("region", new IntDataType());
		Column emailColumn = customersTable.addColumn("email", new VarCharDataType(50));
		Column phoneColumn = customersTable.addColumn("phone", new VarCharDataType(50));
		Column creditCardTypeColumn = customersTable.addColumn("creditcardtype", new VarCharDataType(50));
		Column creditCardColumn = customersTable.addColumn("creditcard", new VarCharDataType(50));
		Column creditCardExpirationColumn = customersTable.addColumn("creditcardexpiration", 
							       new VarCharDataType(50));
		Column usernameColumn = customersTable.addColumn("username", new VarCharDataType(50));
		Column passwordColumn = customersTable.addColumn("password", new VarCharDataType(50));
		Column ageColumn = customersTable.addColumn("age", new IntDataType());
		Column incomeColumn = customersTable.addColumn("income", new IntDataType());
		Column genderColumn = customersTable.addColumn("gender", new VarCharDataType(1));

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
		
		Column prodIdInventoryColumn = inventoryTable.addColumn("prod_id" , new IntDataType());
		Column quanInStockColumn = inventoryTable.addColumn("quan_in_stock" , new IntDataType());
		Column salesColumn = inventoryTable.addColumn("sales", new IntDataType());
		
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
		
		Column orderLineIdColumn = orderLinesTable.addColumn("orderlineid" , new IntDataType());
		Column orderIdOrderLinesColumn = orderLinesTable.addColumn("orderid" , new IntDataType());
		Column prodIdOrderLinesColumn = orderLinesTable.addColumn("prod_id" , new IntDataType());
		Column quantityColumn = orderLinesTable.addColumn("quantity" , new IntDataType());
		Column dateColumn = orderLinesTable.addColumn("orderdate" , new DateDataType());

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

		Column orderIdOrdersColumn = ordersTable.addColumn("orderid" , new IntDataType());
		Column orderDateColumn = ordersTable.addColumn("orderdate" , new DateDataType());
		Column customerIdOrdersColumn = ordersTable.addColumn("customerid" , new IntDataType());
		Column netAmountColumn = ordersTable.addColumn("netamount" , new IntDataType());
		Column taxColumn = ordersTable.addColumn("tax" , new IntDataType());
		Column totalAmountColumn = ordersTable.addColumn("totalamount" , new IntDataType());
		
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
		
		Column prodIdProductsColumn = productsTable.addColumn("prod_id" , new IntDataType());
		Column categoryProductsColumn = productsTable.addColumn("category" , new IntDataType());
		Column titleColumn = productsTable.addColumn("title" , new VarCharDataType(50));
		Column actorColumn = productsTable.addColumn("actor", new VarCharDataType(50));
		Column priceColumn = productsTable.addColumn("price", new IntDataType());
		Column specialColumn = productsTable.addColumn("special" , new IntDataType());
		Column commonProdIdColumn = productsTable.addColumn("commmon_prod_id" , new IntDataType());
		
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
		
		Column prodIdReorderColumn = reorderTable.addColumn("prod_id" , new IntDataType());
		Column dateLowColumn = reorderTable.addColumn("date_low" , new DateDataType());
		Column quanLowColumn = reorderTable.addColumn("quan_low" , new IntDataType());
		Column dateReorderedColumn = reorderTable.addColumn("date_reordered" , new DateDataType());
		Column quanReorderedColumn = reorderTable.addColumn("quan_reordered" , new IntDataType());
		Column dateExpectedColumn = reorderTable.addColumn("date_expected" , new DateDataType());

	}
}
