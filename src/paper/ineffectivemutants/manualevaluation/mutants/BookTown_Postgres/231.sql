-- 231
-- UCColumnA
-- Added UNIQUE to column title in table book_backup

CREATE TABLE "books" (
	"id"	INT	CONSTRAINT "books_id_pkey" PRIMARY KEY	NOT NULL,
	"title"	VARCHAR(100)	NOT NULL,
	"author_id"	INT,
	"subject_id"	INT
)

CREATE TABLE "publishers" (
	"id"	INT	CONSTRAINT "publishers_pkey" PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(100),
	"address"	VARCHAR(100)
)

CREATE TABLE "authors" (
	"id"	INT	CONSTRAINT "authors_pkey" PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(100),
	"first_name"	VARCHAR(100)
)

CREATE TABLE "states" (
	"id"	INT	CONSTRAINT "state_pkey" PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(100),
	"abbreviation"	CHAR(2)
)

CREATE TABLE "my_list" (
	"todos"	VARCHAR(100)
)

CREATE TABLE "stock" (
	"isbn"	VARCHAR(100)	CONSTRAINT "stock_pkey" PRIMARY KEY	NOT NULL,
	"cost"	NUMERIC(5, 2),
	"retail"	NUMERIC(5, 2),
	"stock"	INT
)

CREATE TABLE "numeric_values" (
	"num"	NUMERIC(30, 6)
)

CREATE TABLE "daily_inventory" (
	"isbn"	VARCHAR(100),
	"is_stocked"	BOOLEAN
)

CREATE TABLE "money_example" (
	"money_cash"	NUMERIC(6, 2),
	"numeric_cash"	NUMERIC(6, 2)
)

CREATE TABLE "shipments" (
	"id"	INT	NOT NULL,
	"customer_id"	INT,
	"isbn"	VARCHAR(100),
	"ship_date"	TIMESTAMP
)

CREATE TABLE "customers" (
	"id"	INT	CONSTRAINT "customers_pkey" PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(100),
	"first_name"	VARCHAR(100)
)

CREATE TABLE "book_queue" (
	"title"	VARCHAR(100)	NOT NULL,
	"author_id"	INT,
	"subject_id"	INT,
	"approved"	BOOLEAN
)

CREATE TABLE "stock_backup" (
	"isbn"	VARCHAR(100),
	"cost"	NUMERIC(5, 2),
	"retail"	NUMERIC(5, 2),
	"stock"	INT
)

CREATE TABLE "favorite_books" (
	"employee_id"	INT,
	"books"	VARCHAR(100)
)

CREATE TABLE "employees" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(100)	NOT NULL,
	"first_name"	VARCHAR(100),
	CONSTRAINT "employees_id" CHECK (("id" > 100))
)

CREATE TABLE "editions" (
	"isbn"	VARCHAR(100)	PRIMARY KEY	NOT NULL,
	"book_id"	INT,
	"edition"	INT,
	"publisher_id"	INT,
	"publication"	DATE,
	"type"	CHAR(1),
	CONSTRAINT "integrity" CHECK ((("book_id" IS NOT NULL) AND ("edition" IS NOT NULL)))
)

CREATE TABLE "distinguished_authors" (
	"id"	INT	CONSTRAINT "distinguished_authors_pkey" PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(100),
	"first_name"	VARCHAR(100),
	"award"	VARCHAR(100)
)

CREATE TABLE "varchar(100)_sorting" (
	"letter"	CHAR(1)
)

CREATE TABLE "subjects" (
	"id"	INT	CONSTRAINT "subjects_pkey" PRIMARY KEY	NOT NULL,
	"subject"	VARCHAR(100),
	"location"	VARCHAR(100)
)

CREATE TABLE "alternate_stock" (
	"isbn"	VARCHAR(100),
	"cost"	NUMERIC(5, 2),
	"retail"	NUMERIC(5, 2),
	"stock"	INT
)

CREATE TABLE "book_backup" (
	"id"	INT,
	"title"	VARCHAR(100)	UNIQUE,
	"author_id"	INT,
	"subject_id"	INT
)

CREATE TABLE "schedules" (
	"employee_id"	INT	CONSTRAINT "schedules_pkey" PRIMARY KEY	NOT NULL,
	"schedule"	VARCHAR(100)
)

