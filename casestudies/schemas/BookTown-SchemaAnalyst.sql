-- SchemaAnalyst version
-- * no table inheritance (distinguished authors)
-- * no timestamp with timezone, used timezone for "shipments.ship_date"
-- * no create sequence statements

CREATE TABLE "books" (
	"id" integer NOT NULL,
	"title" varchar(100) NOT NULL,
	"author_id" integer,
	"subject_id" integer,
	Constraint "books_id_pkey" Primary Key ("id")
);

CREATE TABLE "publishers" (
	"id" integer NOT NULL,
	"name" varchar(100),
	"address" varchar(100),
	Constraint "publishers_pkey" Primary Key ("id")
);

CREATE TABLE "authors" (
	"id" integer NOT NULL,
	"last_name" varchar(100),
	"first_name" varchar(100),
	Constraint "authors_pkey" Primary Key ("id")
);

CREATE TABLE "states" (
	"id" integer NOT NULL,
	"name" varchar(100),
	"abbreviation" character(2),
	Constraint "state_pkey" Primary Key ("id")
);

CREATE TABLE "my_list" (
	"todos" varchar(100)
);

CREATE TABLE "stock" (
	"isbn" varchar(100) NOT NULL,
	"cost" numeric(5,2),
	"retail" numeric(5,2),
	"stock" integer,
	Constraint "stock_pkey" Primary Key ("isbn")
);

CREATE TABLE "numeric_values" (
	"num" numeric(30,6)
);

CREATE TABLE "daily_inventory" (
	"isbn" varchar(100),
	"is_stocked" boolean
);

CREATE TABLE "money_example" (
	"money_cash" numeric(6,2),
	"numeric_cash" numeric(6,2)
);

CREATE TABLE "shipments" (
	"id" integer DEFAULT nextval('"shipments_ship_id_seq"'::varchar(100)) NOT NULL,
	"customer_id" integer,
	"isbn" varchar(100),
	"ship_date" timestamp
);

CREATE TABLE "customers" (
	"id" integer NOT NULL,
	"last_name" varchar(100),
	"first_name" varchar(100),
	Constraint "customers_pkey" Primary Key ("id")
);

CREATE TABLE "book_queue" (
	"title" varchar(100) NOT NULL,
	"author_id" integer,
	"subject_id" integer,
	"approved" boolean
);

CREATE TABLE "stock_backup" (
	"isbn" varchar(100),
	"cost" numeric(5,2),
	"retail" numeric(5,2),
	"stock" integer
);

CREATE TABLE "favorite_books" (
	"employee_id" int,
	"books" varchar(100)
);

CREATE TABLE "employees" (
	"id" int PRIMARY KEY NOT NULL,
	"last_name" varchar(100) NOT NULL,
	"first_name" varchar(100),
	CONSTRAINT "employees_id" CHECK ((id > 100))
);

CREATE TABLE "editions" (
	"isbn" varchar(100) NOT NULL PRIMARY KEY,
	"book_id" integer,
	"edition" integer,
	"publisher_id" integer,
	"publication" date,
	"type" character(1),
	CONSTRAINT "integrity" CHECK (((book_id NOTNULL) AND (edition NOTNULL)))
);

CREATE TABLE "distinguished_authors" (
	"id" integer NOT NULL,
	"last_name" varchar(100),
	"first_name" varchar(100),
	"award" varchar(100),
	Constraint "authors_pkey" Primary Key ("id")	
);

CREATE TABLE "favorite_authors" (
	"employee_id" int,
	"authors_and_titles" varchar(100)
);

CREATE TABLE "varchar(100)_sorting" (
	"letter" character(1)
);

CREATE TABLE "subjects" (
	"id" integer NOT NULL,
	"subject" varchar(100),
	"location" varchar(100),
	Constraint "subjects_pkey" Primary Key ("id")
);

CREATE TABLE "alternate_stock" (
	"isbn" varchar(100),
	"cost" numeric(5,2),
	"retail" numeric(5,2),
	"stock" integer
);

CREATE TABLE "book_backup" (
	"id" integer,
	"title" varchar(100),
	"author_id" integer,
	"subject_id" integer
);

CREATE TABLE "schedules" (
        "employee_id" integer NOT NULL,
        "schedule" varchar(100),
        Constraint "schedules_pkey" Primary Key ("employee_id")
);


