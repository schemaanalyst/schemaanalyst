package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.*;

public class BookTown extends Schema {
    
	private static final long serialVersionUID = -4943918732510194739L;

	@SuppressWarnings("unused")
	public BookTown() {
        super("BookTown");
        
        /*
            CREATE TABLE "books" (
                "id" integer NOT NULL,
                "title" varchar(100) NOT NULL,
                "author_id" integer,
                "subject_id" integer,
                Constraint "books_id_pkey" Primary Key ("id")
            );
        */
        
        Table books = createTable("books");
        
        Column books_id = books.addColumn("id", new IntDataType());
        books_id.setNotNull();
        books_id.setPrimaryKey();
        
        Column books_title = books.addColumn("title", new VarCharDataType(100));
        books_title.setNotNull();
        
        Column books_author_id = books.addColumn("author_id", new IntDataType());
        
        Column books_subject_id = books.addColumn("subject_id", new IntDataType());
        
        /*
            CREATE TABLE "publishers" (
                "id" integer NOT NULL,
                "name" varchar(100),
                "address" varchar(100),
                Constraint "publishers_pkey" Primary Key ("id")
            );
        */
        
        Table publishers = createTable("publishers");
        
        Column publishers_id = publishers.addColumn("id", new IntDataType());
        publishers_id.setNotNull();
        publishers_id.setPrimaryKey();
        
        Column publishers_name = publishers.addColumn("name", new VarCharDataType(100));
        
        Column publishers_address = publishers.addColumn("address", new VarCharDataType(100));
        
        /*
            CREATE TABLE "authors" (
                "id" integer NOT NULL,
                "last_name" varchar(100),
                "first_name" varchar(100),
                Constraint "authors_pkey" Primary Key ("id")
            );
        */
        
        Table authors = createTable("authors");
        
        Column authors_id = authors.addColumn("id", new IntDataType());
        authors_id.setNotNull();
        authors_id.setPrimaryKey();
        
        Column authors_last_name = authors.addColumn("last_name", new VarCharDataType(100));
        
        Column authors_first_name = authors.addColumn("first_name", new VarCharDataType(100));
        
        /*
            CREATE TABLE "states" (
                "id" integer NOT NULL,
                "name" varchar(100),
                "abbreviation" character(2),
                Constraint "state_pkey" Primary Key ("id")
            );
        */
        
        Table states = createTable("states");
        
        Column states_id = states.addColumn("id", new IntDataType());
        states_id.setNotNull();
        states_id.setPrimaryKey();
        
        Column states_name = states.addColumn("name", new VarCharDataType(100));
        
        Column states_abbreviation = states.addColumn("abbreviation", new CharDataType(2));
        
        /*
            CREATE TABLE "my_list" (
                "todos" varchar(100)
            );
        */
        
        Table my_list = createTable("my_list");
        
        Column my_list_todos = my_list.addColumn("todos", new VarCharDataType(100));
        
        /*
            CREATE TABLE "stock" (
                "isbn" varchar(100) NOT NULL,
                "cost" numeric(5,2),
                "retail" numeric(5,2),
                "stock" integer,
                Constraint "stock_pkey" Primary Key ("isbn")
            );
        */
        
        Table stock = createTable("stock");
        
        Column stock_isbn = stock.addColumn("isbn", new VarCharDataType(100));
        stock_isbn.setNotNull();
        stock_isbn.setPrimaryKey();
        
        Column stock_cost = stock.addColumn("cost", new NumericDataType(5,2));
        
        Column stock_retail = stock.addColumn("retail", new NumericDataType(5,2));
        
        Column stock_stock = stock.addColumn("stock", new IntDataType());
        
        /*
            CREATE TABLE "numeric_values" (
                "num" numeric(30,6)
            );
        */
        
        Table numeric_values = createTable("numeric_values");
        
        Column numeric_values_num = numeric_values.addColumn("num", new NumericDataType(30,6));
        
        /*
            CREATE TABLE "daily_inventory" (
                "isbn" varchar(100),
                "is_stocked" boolean
            );
        */
        
        Table daily_inventory = createTable("daily_inventory");
        
        Column daily_inventory_isbn = daily_inventory.addColumn("isbn", new VarCharDataType(100));
        
        Column daily_inventory_is_stocked = daily_inventory.addColumn("is_stocked", new org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType());
        
        /*
            CREATE TABLE "money_example" (
                "money_cash" numeric(6,2),
                "numeric_cash" numeric(6,2)
            );
        */
        
        Table money_example = createTable("money_example");
        
        Column money_example_money_cash = money_example.addColumn("money_cash", new NumericDataType(6,2));
        
        Column money_example_numeric_cash = money_example.addColumn("numeric_cash", new NumericDataType(6,2));
        
        /*
            CREATE TABLE "shipments" (
                "id" integer DEFAULT nextval('"shipments_ship_id_seq"'::varchar(100)) NOT NULL,
                "customer_id" integer,
                "isbn" varchar(100),
                "ship_date" timestamp with time zone
            );
        */
        
        Table shipments = createTable("shipments");
        
        Column shipments_id = shipments.addColumn("id", new IntDataType());
        shipments_id.setNotNull();
        
        Column shipments_customer_id = shipments.addColumn("customer_id", new IntDataType());
        
        Column shipments_isbn = shipments.addColumn("isbn", new VarCharDataType(100));
        
        Column shipments_ship_date = shipments.addColumn("ship_date", new TimestampDataType());
        
        /*
            CREATE TABLE "customers" (
                "id" integer NOT NULL,
                "last_name" varchar(100),
                "first_name" varchar(100),
                Constraint "customers_pkey" Primary Key ("id")
            );
        */
        
        Table customers = createTable("customers");
        
        Column customers_id = customers.addColumn("id", new IntDataType());
        customers_id.setNotNull();
        customers_id.setPrimaryKey();
        
        Column customers_last_name = customers.addColumn("last_name", new VarCharDataType(100));
        
        Column customers_first_name = customers.addColumn("first_name", new VarCharDataType(100));
        
        /*
            CREATE TABLE "book_queue" (
                "title" varchar(100) NOT NULL,
                "author_id" integer,
                "subject_id" integer,
                "approved" boolean
            );
        */
        
        Table book_queue = createTable("book_queue");
        
        Column book_queue_title = book_queue.addColumn("title", new VarCharDataType(100));
        book_queue_title.setNotNull();
        
        Column book_queue_author_id = book_queue.addColumn("author_id", new IntDataType());
        
        Column book_queue_subject_id = book_queue.addColumn("subject_id", new IntDataType());
        
        Column book_queue_approved = book_queue.addColumn("approved", new BooleanDataType());
        
        /*
            CREATE TABLE "stock_backup" (
                "isbn" varchar(100),
                "cost" numeric(5,2),
                "retail" numeric(5,2),
                "stock" integer
            );
        */
        
        Table stock_backup = createTable("stock_backup");
        
        Column stock_backup_isbn = stock_backup.addColumn("isbn", new VarCharDataType(100));
        
        Column stock_backup_cost = stock_backup.addColumn("cost", new NumericDataType(5,2));
        
        Column stock_backup_retail = stock_backup.addColumn("retail", new NumericDataType(5,2));
        
        Column stock_backup_stock = stock_backup.addColumn("stock", new IntDataType());
        
        /*
            CREATE TABLE "favorite_books" (
                "employee_id" int,
                "books" varchar(100)
            );
        */
        
        Table favorite_books = createTable("favorite_books");
        
        Column favorite_books_employee_id = favorite_books.addColumn("employee_id", new IntDataType());
        
        Column favorite_books_books = favorite_books.addColumn("books", new VarCharDataType(100));
        
        /*
            CREATE TABLE "employees" (
                "id" int PRIMARY KEY NOT NULL,
                "last_name" varchar(100) NOT NULL,
                "first_name" varchar(100),
                CONSTRAINT "employees_id" CHECK ((id > 100))
            );
        */
        
        Table employees = createTable("employees");
        
        Column employees_id = employees.addColumn("id", new IntDataType());
        employees_id.setPrimaryKey();
        employees_id.setNotNull();
        employees.addCheckConstraint(new RelationalCheckCondition(employees_id, ">", 100));
        
        Column employees_last_name = employees.addColumn("last_name", new VarCharDataType(100));
        employees_last_name.setNotNull();
        
        Column employees_first_name = employees.addColumn("first_name", new VarCharDataType(100));
        
        /*
            CREATE TABLE "editions" (
                "isbn" varchar(100) NOT NULL PRIMARY KEY,
                "book_id" integer,
                "edition" integer,
                "publisher_id" integer,
                "publication" date,
                "type" character(1),
                CONSTRAINT "integrity" CHECK (((book_id NOTNULL) AND (edition NOTNULL)))
            );
        */
        
        Table editions = createTable("editions");
        
        Column editions_isbn = editions.addColumn("isbn", new VarCharDataType(100));
        editions_isbn.setNotNull();
        editions_isbn.setPrimaryKey();
        
        Column editions_book_id = editions.addColumn("book_id", new IntDataType());
        editions_book_id.setNotNull();
        
        Column editions_edition = editions.addColumn("edition", new IntDataType());
        editions_edition.setNotNull();
        
        Column editions_publisher_id = editions.addColumn("publisher_id", new IntDataType());
        
        Column editions_publication = editions.addColumn("publication", new DateDataType());
        
        Column editions_type = editions.addColumn("type", new CharDataType(1));
        
        /*
            CREATE TABLE "distinguished_authors" (
                "award" varchar(100)
            )
            INHERITS ("authors");
        */
        
        Table distinguished_authors = createTable("distinguished_authors");
        
        Column distinguished_authors_id = distinguished_authors.addColumn("id", new IntDataType());
        distinguished_authors_id.setNotNull();
        distinguished_authors_id.setPrimaryKey();

        Column distinguished_authors_last_name = distinguished_authors.addColumn("last_name", new VarCharDataType(100));

        Column distinguished_authors_first_name = distinguished_authors.addColumn("first_name", new VarCharDataType(100));
        
        Column distinguished_authors_award = distinguished_authors.addColumn("award", new VarCharDataType(100));
        
        /*
            CREATE TABLE "favorite_authors" (
                "employee_id" int,
                "authors_and_titles" varchar(100)
            );
        */
        
        Table favorite_authors = createTable("favorite_authors");
        
        Column favorite_authors_employee_id = favorite_authors.addColumn("employee_id", new IntDataType());
        
        Column favorite_authors_authors_and_titles = favorite_authors.addColumn("authors_and_titles", new VarCharDataType(100));
        
        /*
            CREATE TABLE "varchar(100)_sorting" (
                    "letter" character(1)
            ); 
        */
        
        Table varchar_sorting = createTable("varchar100_sorting");
        
        Column letter = varchar_sorting.addColumn("letter", new CharDataType(1));
        
        /*
            CREATE TABLE "subjects" (
                "id" integer NOT NULL,
                "subject" varchar(100),
                "location" varchar(100),
                Constraint "subjects_pkey" Primary Key ("id")
            );
        */
        
        Table subjects = createTable("subjects");
        
        Column subjects_id = subjects.addColumn("id", new IntDataType());
        subjects_id.setNotNull();
        subjects_id.setPrimaryKey();
        
        Column subjects_subject = subjects.addColumn("subject", new VarCharDataType(100));
        
        Column subjects_location = subjects.addColumn("location", new VarCharDataType(100));
        
        /*
            CREATE TABLE "alternate_stock" (
                "isbn" varchar(100),
                "cost" numeric(5,2),
                "retail" numeric(5,2),
                "stock" integer
            );
        */
        
        Table alternate_stock = createTable("alternate_stock");
        
        Column alternate_stock_isbn = alternate_stock.addColumn("isbn", new VarCharDataType(100));
        
        Column alternate_stock_cost = alternate_stock.addColumn("cost", new NumericDataType(5,2));
        
        Column alternate_stock_retail = alternate_stock.addColumn("retail", new NumericDataType(5,2));
        
        Column alternate_stock_stock = alternate_stock.addColumn("stock", new IntDataType());
        
        /*
            CREATE TABLE "book_backup" (
                "id" integer,
                "title" varchar(100),
                "author_id" integer,
                "subject_id" integer
            );
        */
        
        Table book_backup = createTable("book_backup");
        
        Column book_backup_id = book_backup.addColumn("id", new IntDataType());
        
        Column book_backup_title = book_backup.addColumn("title", new VarCharDataType(100));
        
        Column book_backup_author_id = book_backup.addColumn("author_id", new IntDataType());
        
        Column book_backup_subject_id = book_backup.addColumn("subject_id", new IntDataType());
        
        /*
            CREATE TABLE "schedules" (
                "employee_id" integer NOT NULL,
                "schedule" varchar(100),
                Constraint "schedules_pkey" Primary Key ("employee_id")
            );
        */
        
        Table schedules = createTable("schedules");
        
        Column schedules_employee_id = schedules.addColumn("employee_id", new IntDataType());
        schedules_employee_id.setNotNull();
        schedules_employee_id.setPrimaryKey();
        
        Column schedules_schedule = schedules.addColumn("schedule", new VarCharDataType(100));
    }
}
