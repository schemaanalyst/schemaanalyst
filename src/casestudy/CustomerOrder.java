package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.DecimalColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class CustomerOrder extends Schema {

    static final long serialVersionUID = 3171834102219521867L;    
	
	@SuppressWarnings("unused")
	public CustomerOrder() {

		super("CustomerOrder");
		
		/*

		  CREATE TABLE db_category (
		  id        varchar(9)  NOT NULL,
		  name      varchar(30) NOT NULL,
		  parent_id varchar(9)  default NULL,
		  PRIMARY KEY  (id),
		  CONSTRAINT db_category_parent_fk FOREIGN KEY (parent_id) REFERENCES db_category (id)
		  );

		*/		
		
		Table dbCategoryTable = createTable("db_category");
		
		Column idDbCategoryColumn = dbCategoryTable.addColumn("id", new VarCharColumnType(9));
		Column nameCategoryColumn = dbCategoryTable.addColumn("name", new VarCharColumnType(30));
		Column parentIdCategoryColumn = dbCategoryTable.addColumn("parent_id", new VarCharColumnType(9));

		dbCategoryTable.setPrimaryKeyConstraint(idDbCategoryColumn);
		idDbCategoryColumn.setNotNull();
		nameCategoryColumn.setNotNull();
		parentIdCategoryColumn.setDefault("");
		dbCategoryTable.addForeignKeyConstraint(dbCategoryTable, parentIdCategoryColumn, idDbCategoryColumn);

		/*

		  CREATE TABLE db_product (
		  ean_code     varchar(13)  NOT NULL,
		  name         varchar(30)  NOT NULL,
		  category_id  varchar(9)   NOT NULL,
		  price        decimal(8,2) NOT NULL,
		  manufacturer varchar(30)  NOT NULL,
		  notes        varchar(256)     NULL,
		  description  varchar(256)     NULL,
		  PRIMARY KEY  (ean_code),
		  CONSTRAINT db_product_category_fk FOREIGN KEY (category_id) REFERENCES db_category (id)
		  );

		  NOTE: I check inside of PostgreSQL and the "NULL" constraints for notes and 
		  description do not appear in the actual database.  So, we are not going to 
		  represent them inside of the intermediate representation.

		 */

		Table dbProductTable = createTable("db_product");
		
		Column eanCodeColumn = dbProductTable.addColumn("ean_code", new VarCharColumnType(13));
		Column nameColumn = dbProductTable.addColumn("name", new VarCharColumnType(30));
		Column categoryIdColumn = dbProductTable.addColumn("category_id", new VarCharColumnType(9));
		Column priceColumn = dbProductTable.addColumn("price", new DecimalColumnType(8,2));
		Column manufacturerColumn = dbProductTable.addColumn("manufacturer", new VarCharColumnType(30));
		Column notesColumn = dbProductTable.addColumn("notes", new VarCharColumnType(256));
		Column descriptionColumn = dbProductTable.addColumn("description", new VarCharColumnType(256));

		dbProductTable.setPrimaryKeyConstraint(eanCodeColumn);
		eanCodeColumn.setNotNull();
		nameColumn.setNotNull();
		categoryIdColumn.setNotNull();
		priceColumn.setNotNull();
		manufacturerColumn.setNotNull();
		dbProductTable.addForeignKeyConstraint(dbCategoryTable, categoryIdColumn, idDbCategoryColumn);

		/*
		  
		  CREATE TABLE db_role (
		  name varchar(16) NOT NULL,
		  PRIMARY KEY  (name)
		  );

		 */

		Table dbRoleTable = createTable("db_role");
		
		Column nameRowColumn = dbRoleTable.addColumn("name", new VarCharColumnType(16));
                nameRowColumn.setNotNull();
		dbRoleTable.setPrimaryKeyConstraint(nameRowColumn);
		
		/*

		  CREATE TABLE db_user (
		  id       integer     NOT NULL DEFAULT nextval('seq_id_gen'),
		  name     varchar(30) NOT NULL,
		  email    varchar(50) NOT NULL,
		  password varchar(16) NOT NULL,
		  role_id  varchar(16) NOT NULL,
		  active   smallint    NOT NULL default 1,
		  PRIMARY KEY  (id),
		  CONSTRAINT db_user_role_fk FOREIGN KEY (role_id) REFERENCES db_role (name),
		  constraint active_flag check (active in (0,1))
		  );
		  
		 */
		
		Table dbUserTable = createTable("db_user");
		
		Column idDbUserColumn = dbUserTable.addColumn("id", new IntegerColumnType());
		Column nameDbUserColumn = dbUserTable.addColumn("name", new VarCharColumnType(30));
		Column emailColumn = dbUserTable.addColumn("email", new VarCharColumnType(50));
		Column passwordColumn = dbUserTable.addColumn("password", new VarCharColumnType(16));
		Column roleIdColumn = dbUserTable.addColumn("role_id", new VarCharColumnType(16));
		Column activeColumn = dbUserTable.addColumn("active", new SmallIntColumnType());

		dbUserTable.setPrimaryKeyConstraint(idDbUserColumn);
                dbUserTable.addForeignKeyConstraint(dbRoleTable, roleIdColumn, nameRowColumn);
                dbUserTable.addCheckConstraint(new InCheckPredicate(activeColumn, 0, 1));
                
                idDbUserColumn.setNotNull();
		nameDbUserColumn.setNotNull();
		emailColumn.setNotNull();
		passwordColumn.setNotNull();
		roleIdColumn.setNotNull();
		activeColumn.setNotNull();
		activeColumn.setDefault(1);

		/*

		  CREATE TABLE db_customer (
		  id         integer      NOT NULL default 0,
		  category   char(1)      NOT NULL,
		  salutation varchar(10),
		  first_name varchar(30)  NOT NULL,
		  last_name  varchar(30)  NOT NULL,
		  birth_date date,
		  PRIMARY KEY  (id),
		  CONSTRAINT db_customer_user_fk FOREIGN KEY (id) REFERENCES db_user (id)
		  );

		 */

		Table dbCustomerTable = createTable("db_customer");
		
		Column idDbCustomerColumn = dbCustomerTable.addColumn("id", new IntegerColumnType());
		Column categoryColumn = dbCustomerTable.addColumn("category", new CharColumnType(1));
		Column salutationColumn = dbCustomerTable.addColumn("salutation", new VarCharColumnType(10));
		Column firstNameCustomerColumn = dbCustomerTable.addColumn("first_name", new VarCharColumnType(30));
		Column lastNameCustomerColumn = dbCustomerTable.addColumn("last_name", new VarCharColumnType(30));
		Column birthDateColumn = dbCustomerTable.addColumn("birth_date", new DateColumnType());

		idDbCustomerColumn.setNotNull();
		idDbCustomerColumn.setDefault(0);
		categoryColumn.setNotNull();
		firstNameCustomerColumn.setNotNull();
		lastNameCustomerColumn.setNotNull();
		
		dbCustomerTable.setPrimaryKeyConstraint(idDbCustomerColumn);
		dbCustomerTable.addForeignKeyConstraint(dbUserTable, idDbCustomerColumn, idDbUserColumn);

		/*

		  CREATE TABLE db_order (
		  id          integer   NOT NULL DEFAULT nextval('seq_id_gen'),
		  customer_id integer   NOT NULL,
		  total_price     decimal(8,2) NOT NULL,
		  created_at  timestamp NOT NULL,
		  PRIMARY KEY (id),
		  CONSTRAINT db_order_customer_fk FOREIGN KEY (customer_id) REFERENCES db_customer (id)
		  );		  

		 */

		Table dbOrderTable = createTable("db_order");
		
		Column idDbOrderColumn = dbOrderTable.addColumn("id", new IntegerColumnType());
		Column customerIdColumn = dbOrderTable.addColumn("customer_id", new IntegerColumnType());
		Column totalPriceColumn = dbOrderTable.addColumn("total_price", new DecimalColumnType(8,2));
		Column createdAtColumn = dbOrderTable.addColumn("created_at", new TimestampColumnType());

		idDbOrderColumn.setNotNull();
		customerIdColumn.setNotNull();
		totalPriceColumn.setNotNull();
		createdAtColumn.setNotNull();		
		
		dbOrderTable.setPrimaryKeyConstraint(idDbOrderColumn);
		dbOrderTable.addForeignKeyConstraint(dbCustomerTable, customerIdColumn, idDbCustomerColumn);

		/*

		  CREATE TABLE db_order_item (
		  id              integer      NOT NULL DEFAULT nextval('seq_id_gen'),
		  order_id        integer      NOT NULL,
		  number_of_items integer      NOT NULL default 1,
		  product_ean_code      varchar(13)  NOT NULL,
		  total_price     decimal(8,2) NOT NULL,
		  PRIMARY KEY  (id),
		  CONSTRAINT db_order_item_order_fk FOREIGN KEY (order_id) REFERENCES db_order (id),
		  CONSTRAINT db_order_item_product_fk FOREIGN KEY (product_ean_code) REFERENCES db_product (ean_code)
		  );

		 */

		Table dbOrderItemTable = createTable("db_order_item");
		
		Column idDbOrderItemColumn = dbOrderItemTable.addColumn("id", new IntegerColumnType());
		Column orderIdDbOrderItemColumn = dbOrderItemTable.addColumn("order_id", new IntegerColumnType());
		Column numberOfItemsColumn = dbOrderItemTable.addColumn("number_of_items", new IntegerColumnType());
		Column productEanCodeColumn = dbOrderItemTable.addColumn("product_ean_code", new VarCharColumnType(13));
		Column totalPriceOrderItemColumn = dbOrderItemTable.addColumn("total_price", new DecimalColumnType(8,2));

		idDbOrderItemColumn.setNotNull();
		orderIdDbOrderItemColumn.setNotNull();
		numberOfItemsColumn.setNotNull();
		numberOfItemsColumn.setDefault(1);
		productEanCodeColumn.setNotNull();
		totalPriceOrderItemColumn.setNotNull();

		dbOrderItemTable.setPrimaryKeyConstraint(idDbOrderItemColumn);
		dbOrderItemTable.addForeignKeyConstraint(dbOrderTable, orderIdDbOrderItemColumn, idDbOrderColumn);
		dbOrderItemTable.addForeignKeyConstraint(dbProductTable, productEanCodeColumn, eanCodeColumn);

	}
}
