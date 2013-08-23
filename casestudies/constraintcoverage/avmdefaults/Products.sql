/************************************
 * Constraint coverage for Products *
 ************************************/
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
CREATE TABLE products (
	product_no	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	price	NUMERIC	CONSTRAINT null NOT NULL,
	discounted_price	NUMERIC	CONSTRAINT null NOT NULL,
	CONSTRAINT null CHECK (price > 0),
	CONSTRAINT null CHECK (discounted_price > 0),
	CONSTRAINT null CHECK (price > discounted_price)
);
CREATE TABLE orders (
	order_id	INT	CONSTRAINT null PRIMARY KEY,
	shipping_address	VARCHAR(100)
);
CREATE TABLE order_items (
	product_no	INT	CONSTRAINT null  REFERENCES products (product_no),
	order_id	INT	CONSTRAINT null  REFERENCES orders (order_id),
	quantity	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (product_no, order_id),
	CONSTRAINT null CHECK (quantity > 0)
);
-- Coverage: 26/28 (92.85714%) 
-- Time to generate: 13824ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 495ms 
-- * Number of objective function evaluations: 288
-- * Number of restarts: 1

-- Negating "PRIMARY KEY{product_no}" on table "products"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 53
-- * Number of restarts: 1

-- Negating "PRIMARY KEY{order_id}" on table "orders"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{product_no, order_id}" on table "order_items"
-- * Success: false
-- * Time: 8957ms 
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 1012
/* Objective value:
 * Satisfy all except PRIMARY KEY{product_no, order_id}. Value: 0.24806201550387596900 [Sum: 0.32989690721649484537]
 	 	* Satisfy PRIMARY KEY{product_no}. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-55] != [71]. Value: 0E-20 [Best: 0E-20]
 				 				* -55 != 71. Value: 0E-20 [Distance: 0]
 			 			* [-55] != [-86]. Value: 0E-20 [Best: 0E-20]
 				 				* -55 != -86. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 57 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[discounted_price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 34 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[price > discounted_price]. Value: 0E-20 [Sum: 0E-20]
 		 		* 57 > 34. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* -55, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* 'e', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 57, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(discounted_price). Value: 0E-20 [Sum: 0]
 		 		* 34, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY{order_id}. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-30] != [-31]. Value: 0E-20 [Best: 0E-20]
 				 				* -30 != -31. Value: 0E-20 [Distance: 0]
 			 			* [-30] != [94]. Value: 0E-20 [Best: 0E-20]
 				 				* -30 != 94. Value: 0E-20 [Distance: 0]
 	 	* Violate PRIMARY KEY{product_no, order_id}. Value: 0.32989690721649484537 [Sum: 0.49230769230769230770]
 		 		* No rows to compare with - evaluating NULL. Value: 0.49230769230769230770 [Best: 0.49230769230769230770]
 			 			* [-55, -31] = [-86, -31]. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* -55 = -86. Value: 0.96969696969696969697 [Distance: 32]
 				 				* -31 = -31. Value: 0E-20 [Distance: 0]
 			 			* [-55, -31] = [71, 94]. Value: 0.66491455898418980480 [Sum: 1.98431348425196850394]
 				 				* -55 = 71. Value: 0.99218750000000000000 [Distance: 127]
 				 				* -31 = 94. Value: 0.99212598425196850394 [Distance: 126]
 	 	* Satisfy CHECK[quantity > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 9 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY{product_no}. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [-55] = [-55]. Value: 0E-20 [Sum: 0E-20]
 				 				* -55 = -55. Value: 0E-20 [Distance: 0]
 			 			* [-55] = [71]. Value: 0.49803921568627450981 [Sum: 0.99218750000000000000]
 				 				* -55 = 71. Value: 0.99218750000000000000 [Distance: 127]
 			 			* [-55] = [-86]. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* -55 = -86. Value: 0.96969696969696969697 [Distance: 32]
 	 	* Satisfy FOREIGN KEY{order_id}. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [-31] = [-30]. Value: 0.40000000000000000001 [Sum: 0.66666666666666666667]
 				 				* -31 = -30. Value: 0.66666666666666666667 [Distance: 2]
 			 			* [-31] = [-31]. Value: 0E-20 [Sum: 0E-20]
 				 				* -31 = -31. Value: 0E-20 [Distance: 0]
 			 			* [-31] = [94]. Value: 0.49802371541501976285 [Sum: 0.99212598425196850394]
 				 				* -31 = 94. Value: 0.99212598425196850394 [Distance: 126]
 	 	* Satisfy NOT NULL(quantity). Value: 0E-20 [Sum: 0]
 		 		* 9, allowNull: false. Value: 0*/ 

-- Negating "CHECK[price > 0]" on table "products"
-- * Success: false
-- * Time: 4225ms 
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 1573
/* Objective value:
 * Satisfy all except CHECK[price > 0]. Value: 0.28571428571428571430 [Sum: 0.40000000000000000001]
 	 	* Satisfy PRIMARY KEY{product_no}. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-40] != [71]. Value: 0E-20 [Best: 0E-20]
 				 				* -40 != 71. Value: 0E-20 [Distance: 0]
 			 			* [-40] != [-86]. Value: 0E-20 [Best: 0E-20]
 				 				* -40 != -86. Value: 0E-20 [Distance: 0]
 	 	* Violate CHECK[price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[discounted_price > 0]. Value: 0.40000000000000000001 [Sum: 0.66666666666666666667]
 		 		* -1 > 0. Value: 0.66666666666666666667 [Distance: 2]
 	 	* Satisfy CHECK[price > discounted_price]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 > -1. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* -40, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* 'txix', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(discounted_price). Value: 0E-20 [Sum: 0]
 		 		* -1, allowNull: false. Value: 0*/ 

-- Negating "CHECK[discounted_price > 0]" on table "products"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 1

-- Negating "CHECK[price > discounted_price]" on table "products"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "CHECK[quantity > 0]" on table "order_items"
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 121
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{product_no}" on table "order_items"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 117
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{order_id}" on table "order_items"
-- * Success: true
-- * Time: 43ms 
-- * Number of objective function evaluations: 417
-- * Number of restarts: 4

-- Negating "NOT NULL(product_no)" on table "products"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 106
-- * Number of restarts: 2

-- Negating "NOT NULL(name)" on table "products"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 44
-- * Number of restarts: 1

-- Negating "NOT NULL(price)" on table "products"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "NOT NULL(discounted_price)" on table "products"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(quantity)" on table "order_items"
-- * Success: true
-- * Time: 59ms 
-- * Number of objective function evaluations: 487
-- * Number of restarts: 5

