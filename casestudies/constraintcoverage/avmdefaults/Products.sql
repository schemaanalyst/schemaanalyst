/************************************
 * Constraint coverage for Products *
 ************************************/
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
CREATE TABLE products (
	product_no	INT	PRIMARY KEY	NOT NULL,
	name	VARCHAR(100)	NOT NULL,
	price	NUMERIC	NOT NULL,
	discounted_price	NUMERIC	NOT NULL,
	CHECK (price > 0),
	CHECK (discounted_price > 0),
	CHECK (price > discounted_price)
);
CREATE TABLE orders (
	order_id	INT	PRIMARY KEY,
	shipping_address	VARCHAR(100)
);
CREATE TABLE order_items (
	product_no	INT	 REFERENCES products (product_no),
	order_id	INT	 REFERENCES orders (order_id),
	quantity	INT	NOT NULL,
	PRIMARY KEY (product_no, order_id),
	CHECK (quantity > 0)
);
-- Coverage: 27/28 (96.42857%) 
-- Time to generate: 7601ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 484ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(71, 'phctgpyae', 25, 4);
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-86, 'ny', 47, 25);
INSERT INTO orders(order_id, shipping_address) VALUES(-31, 'yrvyoaks');
INSERT INTO orders(order_id, shipping_address) VALUES(94, 'cysrdla');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(-86, -31, 30);
INSERT INTO order_items(product_no, order_id, quantity) VALUES(71, 94, 73);
-- * Number of objective function evaluations: 288
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[product_no]" on table "products"
-- * Success: true
-- * Time: 10ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(71, 'hfckgxrbj', 53, 23);
-- * Number of objective function evaluations: 53
-- * Number of restarts: 1

-- Negating "NOT NULL(product_no)" on table "products"
-- * Success: true
-- * Time: 3ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(NULL, 'tywey', 44, 35);
-- * Number of objective function evaluations: 20
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "products"
-- * Success: true
-- * Time: 10ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(69, NULL, 48, 42);
-- * Number of objective function evaluations: 64
-- * Number of restarts: 3

-- Negating "NOT NULL(price)" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(0, '', NULL, 1);
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "NOT NULL(discounted_price)" on table "products"
-- * Success: true
-- * Time: 2ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(0, '', 1, NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "CHECK[price > 0]" on table "products"
-- * Success: false
-- * Time: 2264ms 
-- INSERT INTO products(product_no, name, price, discounted_price) VALUES(81, 'yi', 0, -1);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 1578
/* Objective value:
 * Satisfy all except CHECK[price > 0]. Value: 0.28571428571428571430 [Sum: 0.40000000000000000001]
 	 	* Satisfy PRIMARY KEY[product_no]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [81] != [71]. Value: 0E-20 [Best: 0E-20]
 				 				* 81 != 71. Value: 0E-20 [Distance: 0]
 			 			* [81] != [-86]. Value: 0E-20 [Best: 0E-20]
 				 				* 81 != -86. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* 81, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* 'yi', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(discounted_price). Value: 0E-20 [Sum: 0]
 		 		* -1, allowNull: false. Value: 0
 	 	* Violate CHECK[price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[discounted_price > 0]. Value: 0.40000000000000000001 [Sum: 0.66666666666666666667]
 		 		* -1 > 0. Value: 0.66666666666666666667 [Distance: 2]
 	 	* Satisfy CHECK[price > discounted_price]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 > -1. Value: 0E-20 [Distance: 0]*/ 

-- Negating "CHECK[discounted_price > 0]" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-4, 'rokgkrbfk', 79, -55);
-- * Number of objective function evaluations: 17
-- * Number of restarts: 1

-- Negating "CHECK[price > discounted_price]" on table "products"
-- * Success: true
-- * Time: 0ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(0, '', 1, 1);
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[order_id]" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO orders(order_id, shipping_address) VALUES(-31, '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[product_no, order_id]" on table "order_items"
-- * Success: true
-- * Time: 4748ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(72, 'uecopiv', 26, 15);
INSERT INTO orders(order_id, shipping_address) VALUES(81, 'd');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(71, 94, 63);
-- * Number of objective function evaluations: 94983
-- * Number of restarts: 976

-- Negating "FOREIGN KEY[product_no]" on table "order_items"
-- * Success: true
-- * Time: 7ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-82, 'usxhv', 45, 18);
INSERT INTO orders(order_id, shipping_address) VALUES(-1, 'tjso');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(-24, NULL, 11);
-- * Number of objective function evaluations: 103
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[order_id]" on table "order_items"
-- * Success: true
-- * Time: 8ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(20, 'jw', 3, 2);
INSERT INTO orders(order_id, shipping_address) VALUES(73, 'ydaqoowrb');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(NULL, 88, 93);
-- * Number of objective function evaluations: 120
-- * Number of restarts: 1

-- Negating "NOT NULL(quantity)" on table "order_items"
-- * Success: true
-- * Time: 14ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-19, 'atjmfjd', 38, 37);
INSERT INTO orders(order_id, shipping_address) VALUES(-11, 'numngod');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(-19, NULL, NULL);
-- * Number of objective function evaluations: 206
-- * Number of restarts: 2

-- Negating "CHECK[quantity > 0]" on table "order_items"
-- * Success: true
-- * Time: 48ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(52, 'jt', 6, 2);
INSERT INTO orders(order_id, shipping_address) VALUES(36, 'wpj');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(NULL, -1, -8);
-- * Number of objective function evaluations: 416
-- * Number of restarts: 4

