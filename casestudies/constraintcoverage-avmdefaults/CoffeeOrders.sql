/****************************************
 * Constraint coverage for CoffeeOrders *
 ****************************************/
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS salespeople;
DROP TABLE IF EXISTS coffees;
CREATE TABLE coffees (
	id	INT	PRIMARY KEY,
	coffee_name	VARCHAR(50)	NOT NULL,
	price	INT	NOT NULL
);
CREATE TABLE salespeople (
	id	INT	PRIMARY KEY,
	first_name	VARCHAR(50)	NOT NULL,
	last_name	VARCHAR(50)	NOT NULL,
	commission_rate	INT	NOT NULL
);
CREATE TABLE customers (
	id	INT	PRIMARY KEY,
	company_name	VARCHAR(50)	NOT NULL,
	street_address	VARCHAR(50)	NOT NULL,
	city	VARCHAR(50)	NOT NULL,
	state	VARCHAR(50)	NOT NULL,
	zip	VARCHAR(50)	NOT NULL
);
CREATE TABLE orders (
	id	INT	PRIMARY KEY,
	customer_id	INT	 REFERENCES customers (id),
	salesperson_id	INT	 REFERENCES salespeople (id)
);
CREATE TABLE order_items (
	id	INT	PRIMARY KEY,
	order_id	INT	 REFERENCES orders (id),
	product_id	INT	 REFERENCES coffees (id),
	product_quantity	INT
);
-- Coverage: 38/38 (100.00000%) 
-- Time to generate: 848ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 250ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(1, '', 0);
INSERT INTO coffees(id, coffee_name, price) VALUES(0, '', 0);
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(1, '', '', 0);
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(0, '', '', 0);
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(1, '', '', '', '', '');
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(0, '', '', '', '', '');
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(1, 0, 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(0, 0, 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(1, 0, 0, 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(0, 0, 0, 0);
-- * Number of objective function evaluations: 95
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "coffees"
-- * Success: true
-- * Time: 1ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(coffee_name)" on table "coffees"
-- * Success: true
-- * Time: 3ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(-1, NULL, 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "coffees"
-- * Success: true
-- * Time: 3ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(-1, '', NULL);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "salespeople"
-- * Success: true
-- * Time: 0ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(0, '', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "salespeople"
-- * Success: true
-- * Time: 3ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-1, NULL, '', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "salespeople"
-- * Success: true
-- * Time: 3ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-1, '', NULL, 0);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(commission_rate)" on table "salespeople"
-- * Success: true
-- * Time: 4ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-1, '', '', NULL);
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "customers"
-- * Success: true
-- * Time: 0ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(0, '', '', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(company_name)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, NULL, '', '', '', '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(street_address)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, '', NULL, '', '', '');
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "customers"
-- * Success: true
-- * Time: 4ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, '', '', NULL, '', '');
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(state)" on table "customers"
-- * Success: true
-- * Time: 4ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, '', '', '', NULL, '');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(zip)" on table "customers"
-- * Success: true
-- * Time: 5ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, '', '', '', '', NULL);
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "orders"
-- * Success: true
-- * Time: 20ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-1, '', '', '', '', '');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-1, '', '', 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[customer_id]" on table "orders"
-- * Success: true
-- * Time: 183ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(46, 'phctgpyae', 'ddanycpk', 'yrvyoaks', 'dcysrd', 'gqrupxsn');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-92, 'fva', 'gxrbjt', 23);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(22, -96, NULL);
-- * Number of objective function evaluations: 200
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[salesperson_id]" on table "orders"
-- * Success: true
-- * Time: 42ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-100, 'e', 'e', 'qullv', 'r', 'yi');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-91, 'acamirp', 'hvbai', -83);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(70, -100, -39);
-- * Number of objective function evaluations: 75
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[id]" on table "order_items"
-- * Success: true
-- * Time: 161ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-45, 'rs', 'mocioobsf', 'rkiqs', 'uabqpakvq', 'usphsedrx');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-38, 'anhtu', 'xnfi', -50);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(6, NULL, NULL);
INSERT INTO coffees(id, coffee_name, price) VALUES(74, 'ndyifmfjn', -57);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(1, NULL, NULL, -63);
-- * Number of objective function evaluations: 238
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[order_id]" on table "order_items"
-- * Success: true
-- * Time: 81ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(74, 'yopgmx', 'ij', 'gqhb', '', 'dpshpa');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-5, '', 'rku', 20);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(-41, NULL, -91);
INSERT INTO coffees(id, coffee_name, price) VALUES(-83, 'rxsse', 93);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(34, -10, NULL, -47);
-- * Number of objective function evaluations: 216
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[product_id]" on table "order_items"
-- * Success: true
-- * Time: 75ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-91, 'q', 'gke', 'oojar', 'dyuv', 'si');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-88, 'huhjyapf', 'raglk', -63);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(49, NULL, NULL);
INSERT INTO coffees(id, coffee_name, price) VALUES(84, 'glffudw', -19);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(-42, NULL, -67, -26);
-- * Number of objective function evaluations: 234
-- * Number of restarts: 1

