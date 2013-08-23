/************************************
 * Constraint coverage for BookTown *
 ************************************/
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS book_backup;
DROP TABLE IF EXISTS alternate_stock;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS varchar(100)_sorting;
DROP TABLE IF EXISTS distinguished_authors;
DROP TABLE IF EXISTS editions;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS favorite_books;
DROP TABLE IF EXISTS stock_backup;
DROP TABLE IF EXISTS book_queue;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS shipments;
DROP TABLE IF EXISTS money_example;
DROP TABLE IF EXISTS daily_inventory;
DROP TABLE IF EXISTS numeric_values;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS my_list;
DROP TABLE IF EXISTS states;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS publishers;
DROP TABLE IF EXISTS books;
CREATE TABLE books (
	id	INT	CONSTRAINT books_id_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	title	VARCHAR(100)	CONSTRAINT null NOT NULL,
	author_id	INT,
	subject_id	INT
);
CREATE TABLE publishers (
	id	INT	CONSTRAINT publishers_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	name	VARCHAR(100),
	address	VARCHAR(100)
);
CREATE TABLE authors (
	id	INT	CONSTRAINT authors_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(100),
	first_name	VARCHAR(100)
);
CREATE TABLE states (
	id	INT	CONSTRAINT state_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	name	VARCHAR(100),
	abbreviation	CHAR(2)
);
CREATE TABLE my_list (
	todos	VARCHAR(100)
);
CREATE TABLE stock (
	isbn	VARCHAR(100)	CONSTRAINT stock_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	cost	NUMERIC(5, 2),
	retail	NUMERIC(5, 2),
	stock	INT
);
CREATE TABLE numeric_values (
	num	NUMERIC(30, 6)
);
CREATE TABLE daily_inventory (
	isbn	VARCHAR(100),
	is_stocked	BOOLEAN
);
CREATE TABLE money_example (
	money_cash	NUMERIC(6, 2),
	numeric_cash	NUMERIC(6, 2)
);
CREATE TABLE shipments (
	id	INT	CONSTRAINT null NOT NULL,
	customer_id	INT,
	isbn	VARCHAR(100),
	ship_date	TIMESTAMP
);
CREATE TABLE customers (
	id	INT	CONSTRAINT customers_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(100),
	first_name	VARCHAR(100)
);
CREATE TABLE book_queue (
	title	VARCHAR(100)	CONSTRAINT null NOT NULL,
	author_id	INT,
	subject_id	INT,
	approved	BOOLEAN
);
CREATE TABLE stock_backup (
	isbn	VARCHAR(100),
	cost	NUMERIC(5, 2),
	retail	NUMERIC(5, 2),
	stock	INT
);
CREATE TABLE favorite_books (
	employee_id	INT,
	books	VARCHAR(100)
);
CREATE TABLE employees (
	id	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	first_name	VARCHAR(100),
	CONSTRAINT employees_id CHECK ((id > 100))
);
CREATE TABLE editions (
	isbn	VARCHAR(100)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	book_id	INT,
	edition	INT,
	publisher_id	INT,
	publication	DATE,
	type	CHAR(1),
	CONSTRAINT integrity CHECK (((book_id IS NOT NULL) AND (edition IS NOT NULL)))
);
CREATE TABLE distinguished_authors (
	id	INT	CONSTRAINT authors_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(100),
	first_name	VARCHAR(100),
	award	VARCHAR(100)
);
CREATE TABLE varchar(100)_sorting (
	letter	CHAR(1)
);
CREATE TABLE subjects (
	id	INT	CONSTRAINT subjects_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	subject	VARCHAR(100),
	location	VARCHAR(100)
);
CREATE TABLE alternate_stock (
	isbn	VARCHAR(100),
	cost	NUMERIC(5, 2),
	retail	NUMERIC(5, 2),
	stock	INT
);
CREATE TABLE book_backup (
	id	INT,
	title	VARCHAR(100),
	author_id	INT,
	subject_id	INT
);
CREATE TABLE schedules (
	employee_id	INT	CONSTRAINT schedules_pkey PRIMARY KEY	CONSTRAINT null NOT NULL,
	schedule	VARCHAR(100)
);
-- Coverage: 56/56 (100.00000%) 
-- Time to generate: 650ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 644ms 
-- * Number of objective function evaluations: 393
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "books"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "publishers"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "authors"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "states"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{isbn}" on table "stock"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "customers"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "employees"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{isbn}" on table "editions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "distinguished_authors"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "subjects"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{employee_id}" on table "schedules"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[(id > 100)]" on table "employees"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[((book_id IS NOT NULL) AND (edition IS NOT NULL))]" on table "editions"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "books"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(title)" on table "books"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "publishers"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "authors"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "states"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(isbn)" on table "stock"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "shipments"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "customers"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(title)" on table "book_queue"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "employees"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "employees"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(isbn)" on table "editions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "distinguished_authors"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "subjects"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(employee_id)" on table "schedules"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

