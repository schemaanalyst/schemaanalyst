/*************************************
 * Constraint coverage for DellStore *
 *************************************/
DROP TABLE IF EXISTS reorder;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orderlines;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS cust_hist;
DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
	category	INT	NOT NULL,
	categoryname	VARCHAR(50)	NOT NULL
);
CREATE TABLE cust_hist (
	customerid	INT	NOT NULL,
	orderid	INT	NOT NULL,
	prod_id	INT	NOT NULL
);
CREATE TABLE customers (
	customerid	INT	NOT NULL,
	firstname	VARCHAR(50)	NOT NULL,
	lastname	VARCHAR(50)	NOT NULL,
	address1	VARCHAR(50)	NOT NULL,
	address2	VARCHAR(50),
	city	VARCHAR(50)	NOT NULL,
	state	VARCHAR(50),
	zip	INT,
	country	VARCHAR(50)	NOT NULL,
	region	SMALLINT	NOT NULL,
	email	VARCHAR(50),
	phone	VARCHAR(50),
	creditcardtype	INT	NOT NULL,
	creditcard	VARCHAR(50)	NOT NULL,
	creditcardexpiration	VARCHAR(50)	NOT NULL,
	username	VARCHAR(50)	NOT NULL,
	password	VARCHAR(50)	NOT NULL,
	age	SMALLINT,
	income	INT,
	gender	VARCHAR(1)
);
CREATE TABLE inventory (
	prod_id	INT	NOT NULL,
	quan_in_stock	INT	NOT NULL,
	sales	INT	NOT NULL
);
CREATE TABLE orderlines (
	orderlineid	INT	NOT NULL,
	orderid	INT	NOT NULL,
	prod_id	INT	NOT NULL,
	quantity	SMALLINT	NOT NULL,
	orderdate	DATE	NOT NULL
);
CREATE TABLE orders (
	orderid	INT	NOT NULL,
	orderdate	DATE	NOT NULL,
	customerid	INT,
	netamount	NUMERIC(12, 2)	NOT NULL,
	tax	NUMERIC(12, 2)	NOT NULL,
	totalamount	NUMERIC(12, 2)	NOT NULL
);
CREATE TABLE products (
	prod_id	INT	NOT NULL,
	category	INT	NOT NULL,
	title	VARCHAR(50)	NOT NULL,
	actor	VARCHAR(50)	NOT NULL,
	price	NUMERIC(12, 2)	NOT NULL,
	special	SMALLINT,
	common_prod_id	INT	NOT NULL
);
CREATE TABLE reorder (
	prod_id	INT	NOT NULL,
	date_low	DATE	NOT NULL,
	quan_low	INT	NOT NULL,
	date_reordered	DATE,
	quan_reordered	INT,
	date_expected	DATE
);
-- Coverage: 78/78 (100.00000%) 
-- Time to generate: 78ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 17ms 
INSERT INTO categories(category, categoryname) VALUES(0, '');
INSERT INTO categories(category, categoryname) VALUES(0, '');
INSERT INTO cust_hist(customerid, orderid, prod_id) VALUES(0, 0, 0);
INSERT INTO cust_hist(customerid, orderid, prod_id) VALUES(0, 0, 0);
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
INSERT INTO inventory(prod_id, quan_in_stock, sales) VALUES(0, 0, 0);
INSERT INTO inventory(prod_id, quan_in_stock, sales) VALUES(0, 0, 0);
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, 0, 0, 0, '1000-01-01');
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, 0, 0, 0, '1000-01-01');
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, '1000-01-01', 0, 0, 0, 0);
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, '1000-01-01', 0, 0, 0, 0);
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, '', '', 0, 0, 0);
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, '', '', 0, 0, 0);
INSERT INTO reorder(prod_id, date_low, quan_low, date_reordered, quan_reordered, date_expected) VALUES(0, '1000-01-01', 0, '1000-01-01', 0, '1000-01-01');
INSERT INTO reorder(prod_id, date_low, quan_low, date_reordered, quan_reordered, date_expected) VALUES(0, '1000-01-01', 0, '1000-01-01', 0, '1000-01-01');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(category)" on table "categories"
-- * Success: true
-- * Time: 0ms 
INSERT INTO categories(category, categoryname) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(categoryname)" on table "categories"
-- * Success: true
-- * Time: 1ms 
INSERT INTO categories(category, categoryname) VALUES(0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(customerid)" on table "cust_hist"
-- * Success: true
-- * Time: 0ms 
INSERT INTO cust_hist(customerid, orderid, prod_id) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderid)" on table "cust_hist"
-- * Success: true
-- * Time: 1ms 
INSERT INTO cust_hist(customerid, orderid, prod_id) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(prod_id)" on table "cust_hist"
-- * Success: true
-- * Time: 0ms 
INSERT INTO cust_hist(customerid, orderid, prod_id) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(customerid)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(NULL, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(firstname)" on table "customers"
-- * Success: true
-- * Time: 4ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, NULL, '', '', '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(lastname)" on table "customers"
-- * Success: true
-- * Time: 5ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', NULL, '', '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(address1)" on table "customers"
-- * Success: true
-- * Time: 4ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', NULL, '', '', '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "customers"
-- * Success: true
-- * Time: 4ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', NULL, '', 0, '', 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(country)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, NULL, 0, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(region)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', NULL, '', '', 0, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(creditcardtype)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', NULL, '', '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(creditcard)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, NULL, '', '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(creditcardexpiration)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', NULL, '', '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(username)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', '', NULL, '', 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(password)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(customerid, firstname, lastname, address1, address2, city, state, zip, country, region, email, phone, creditcardtype, creditcard, creditcardexpiration, username, password, age, income, gender) VALUES(0, '', '', '', '', '', '', 0, '', 0, '', '', 0, '', '', '', NULL, 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(prod_id)" on table "inventory"
-- * Success: true
-- * Time: 0ms 
INSERT INTO inventory(prod_id, quan_in_stock, sales) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(quan_in_stock)" on table "inventory"
-- * Success: true
-- * Time: 1ms 
INSERT INTO inventory(prod_id, quan_in_stock, sales) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(sales)" on table "inventory"
-- * Success: true
-- * Time: 1ms 
INSERT INTO inventory(prod_id, quan_in_stock, sales) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderlineid)" on table "orderlines"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(NULL, 0, 0, 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderid)" on table "orderlines"
-- * Success: true
-- * Time: 2ms 
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, NULL, 0, 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(prod_id)" on table "orderlines"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, 0, NULL, 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(quantity)" on table "orderlines"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, 0, 0, NULL, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderdate)" on table "orderlines"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orderlines(orderlineid, orderid, prod_id, quantity, orderdate) VALUES(0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderid)" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(NULL, '1000-01-01', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(orderdate)" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(netamount)" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, '1000-01-01', 0, NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(tax)" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, '1000-01-01', 0, 0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(totalamount)" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(orderid, orderdate, customerid, netamount, tax, totalamount) VALUES(0, '1000-01-01', 0, 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(prod_id)" on table "products"
-- * Success: true
-- * Time: 0ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(NULL, 0, '', '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(category)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, NULL, '', '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(title)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, NULL, '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(actor)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, '', NULL, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, '', '', NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(common_prod_id)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(prod_id, category, title, actor, price, special, common_prod_id) VALUES(0, 0, '', '', 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(prod_id)" on table "reorder"
-- * Success: true
-- * Time: 1ms 
INSERT INTO reorder(prod_id, date_low, quan_low, date_reordered, quan_reordered, date_expected) VALUES(NULL, '1000-01-01', 0, '1000-01-01', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(date_low)" on table "reorder"
-- * Success: true
-- * Time: 1ms 
INSERT INTO reorder(prod_id, date_low, quan_low, date_reordered, quan_reordered, date_expected) VALUES(0, NULL, 0, '1000-01-01', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(quan_low)" on table "reorder"
-- * Success: true
-- * Time: 1ms 
INSERT INTO reorder(prod_id, date_low, quan_low, date_reordered, quan_reordered, date_expected) VALUES(0, '1000-01-01', NULL, '1000-01-01', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

