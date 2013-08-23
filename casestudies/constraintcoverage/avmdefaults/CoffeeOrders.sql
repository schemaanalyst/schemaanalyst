/****************************************
 * Constraint coverage for CoffeeOrders *
 ****************************************/
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS salespeople;
DROP TABLE IF EXISTS coffees;
CREATE TABLE coffees (
	id	INT	CONSTRAINT null PRIMARY KEY,
	coffee_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	price	INT	CONSTRAINT null NOT NULL
);
CREATE TABLE salespeople (
	id	INT	CONSTRAINT null PRIMARY KEY,
	first_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	commission_rate	INT	CONSTRAINT null NOT NULL
);
CREATE TABLE customers (
	id	INT	CONSTRAINT null PRIMARY KEY,
	company_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	street_address	VARCHAR(50)	CONSTRAINT null NOT NULL,
	city	VARCHAR(50)	CONSTRAINT null NOT NULL,
	state	VARCHAR(50)	CONSTRAINT null NOT NULL,
	zip	VARCHAR(50)	CONSTRAINT null NOT NULL
);
CREATE TABLE orders (
	id	INT	CONSTRAINT null PRIMARY KEY,
	customer_id	INT	CONSTRAINT null  REFERENCES customers (id),
	salesperson_id	INT	CONSTRAINT null  REFERENCES salespeople (id)
);
CREATE TABLE order_items (
	id	INT	CONSTRAINT null PRIMARY KEY,
	order_id	INT	CONSTRAINT null  REFERENCES orders (id),
	product_id	INT	CONSTRAINT null  REFERENCES coffees (id),
	product_quantity	INT
);
-- Coverage: 38/38 (100.00000%) 
-- Time to generate: 952ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 299ms 
-- * Number of objective function evaluations: 95
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "coffees"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "salespeople"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "customers"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "orders"
-- * Success: true
-- * Time: 25ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "order_items"
-- * Success: true
-- * Time: 261ms 
-- * Number of objective function evaluations: 215
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{customer_id}" on table "orders"
-- * Success: true
-- * Time: 76ms 
-- * Number of objective function evaluations: 148
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{salesperson_id}" on table "orders"
-- * Success: true
-- * Time: 39ms 
-- * Number of objective function evaluations: 71
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{order_id}" on table "order_items"
-- * Success: true
-- * Time: 125ms 
-- * Number of objective function evaluations: 226
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{product_id}" on table "order_items"
-- * Success: true
-- * Time: 94ms 
-- * Number of objective function evaluations: 248
-- * Number of restarts: 1

-- Negating "NOT NULL(coffee_name)" on table "coffees"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "coffees"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "salespeople"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 1

-- Negating "NOT NULL(last_name)" on table "salespeople"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 30
-- * Number of restarts: 1

-- Negating "NOT NULL(commission_rate)" on table "salespeople"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 48
-- * Number of restarts: 1

-- Negating "NOT NULL(company_name)" on table "customers"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 22
-- * Number of restarts: 1

-- Negating "NOT NULL(street_address)" on table "customers"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 39
-- * Number of restarts: 1

-- Negating "NOT NULL(city)" on table "customers"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 48
-- * Number of restarts: 1

-- Negating "NOT NULL(state)" on table "customers"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 62
-- * Number of restarts: 1

-- Negating "NOT NULL(zip)" on table "customers"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 86
-- * Number of restarts: 1

