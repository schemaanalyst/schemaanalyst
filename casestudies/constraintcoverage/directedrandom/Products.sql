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
-- Coverage: 25/28 (89.28571%) 
-- Time to generate: 30031ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 299ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(0, '', 70, 54);
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-52, '', 83, 37);
INSERT INTO orders(order_id, shipping_address) VALUES(0, '');
INSERT INTO orders(order_id, shipping_address) VALUES(55, '');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(0, 0, 5);
INSERT INTO order_items(product_no, order_id, quantity) VALUES(-52, 0, 92);
-- * Number of objective function evaluations: 61
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[product_no]" on table "products"
-- * Success: true
-- * Time: 14ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(0, '', 28, 17);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(product_no)" on table "products"
-- * Success: true
-- * Time: 3ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(NULL, '', 42, 22);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "products"
-- * Success: true
-- * Time: 6ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-4, NULL, 92, 42);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "products"
-- * Success: true
-- * Time: 2ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(99, '', NULL, 84);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(discounted_price)" on table "products"
-- * Success: true
-- * Time: 3ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(2, '', 38, NULL);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "CHECK[price > 0]" on table "products"
-- * Success: false
-- * Time: 4477ms 
-- INSERT INTO products(product_no, name, price, discounted_price) VALUES(-79, '', 0, -1);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[price > 0]. Value: 0.28571428571428571430 [Sum: 0.40000000000000000001]
 	 	* Satisfy PRIMARY KEY[product_no]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-79] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -79 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-79] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -79 != -52. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* -79, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
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
-- * Time: 0ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-42, '', 98, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[price > discounted_price]" on table "products"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(89, '', 74, 78);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[order_id]" on table "orders"
-- * Success: true
-- * Time: 0ms 
INSERT INTO orders(order_id, shipping_address) VALUES(0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[product_no, order_id]" on table "order_items"
-- * Success: true
-- * Time: 3ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(75, '', 19, 5);
INSERT INTO orders(order_id, shipping_address) VALUES(-19, '');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(0, 0, 60);
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[product_no]" on table "order_items"
-- * Success: false
-- * Time: 10743ms 
-- INSERT INTO products(product_no, name, price, discounted_price) VALUES(-53, '', 91, 42);
-- INSERT INTO orders(order_id, shipping_address) VALUES(51, '');
-- INSERT INTO order_items(product_no, order_id, quantity) VALUES(NULL, 55, 89);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[product_no]. Value: 0.30769230769230769232 [Sum: 0.44444444444444444445]
 	 	* Satisfy PRIMARY KEY[product_no]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-53] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -53 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-53] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -53 != -52. Value: 0E-20 [Distance: 0]
 			 			* [-53] != [75]. Value: 0E-20 [Best: 0E-20]
 				 				* -53 != 75. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* -53, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 91, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(discounted_price). Value: 0E-20 [Sum: 0]
 		 		* 42, allowNull: false. Value: 0
 	 	* Satisfy CHECK[price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 91 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[discounted_price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 42 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[price > discounted_price]. Value: 0E-20 [Sum: 0E-20]
 		 		* 91 > 42. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [51] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 51 != 0. Value: 0E-20 [Distance: 0]
 			 			* [51] != [55]. Value: 0E-20 [Best: 0E-20]
 				 				* 51 != 55. Value: 0E-20 [Distance: 0]
 			 			* [51] != [-19]. Value: 0E-20 [Best: 0E-20]
 				 				* 51 != -19. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[product_no, order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [null, 55] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* null != 0. Value: 1
 				 				* 55 != 0. Value: 0E-20 [Distance: 0]
 			 			* [null, 55] != [-52, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* null != -52. Value: 1
 				 				* 55 != 0. Value: 0E-20 [Distance: 0]
 	 	* Violate FOREIGN KEY[product_no]. Value: 0.44444444444444444445 [Sum: 0.80000000000000000000]
 		 		* Evaluating row with reference rows. Value: 0.80000000000000000000 [Sum: 4]
 			 			* [null] != [-53]. Value: 1 [Best: 1]
 				 				* null != -53. Value: 1
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [-52]. Value: 1 [Best: 1]
 				 				* null != -52. Value: 1
 			 			* [null] != [75]. Value: 1 [Best: 1]
 				 				* null != 75. Value: 1
 	 	* Satisfy FOREIGN KEY[order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [55] = [51]. Value: 0.45454545454545454546 [Sum: 0.83333333333333333334]
 				 				* 55 = 51. Value: 0.83333333333333333334 [Distance: 5]
 			 			* [55] = [0]. Value: 0.49557522123893805310 [Sum: 0.98245614035087719299]
 				 				* 55 = 0. Value: 0.98245614035087719299 [Distance: 56]
 			 			* [55] = [55]. Value: 0E-20 [Sum: 0E-20]
 				 				* 55 = 55. Value: 0E-20 [Distance: 0]
 			 			* [55] = [-19]. Value: 0.49668874172185430464 [Sum: 0.98684210526315789474]
 				 				* 55 = -19. Value: 0.98684210526315789474 [Distance: 75]
 	 	* Satisfy NOT NULL(quantity). Value: 0E-20 [Sum: 0]
 		 		* 89, allowNull: false. Value: 0
 	 	* Satisfy CHECK[quantity > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 89 > 0. Value: 0E-20 [Distance: 0]*/ 

-- Negating "FOREIGN KEY[order_id]" on table "order_items"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(-94, '', 84, 52);
INSERT INTO orders(order_id, shipping_address) VALUES(26, '');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(-52, -82, 74);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(quantity)" on table "order_items"
-- * Success: true
-- * Time: 1ms 
INSERT INTO products(product_no, name, price, discounted_price) VALUES(73, '', 15, 13);
INSERT INTO orders(order_id, shipping_address) VALUES(-24, '');
INSERT INTO order_items(product_no, order_id, quantity) VALUES(0, -24, NULL);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "CHECK[quantity > 0]" on table "order_items"
-- * Success: false
-- * Time: 14478ms 
-- INSERT INTO products(product_no, name, price, discounted_price) VALUES(-96, '', 24, 19);
-- INSERT INTO orders(order_id, shipping_address) VALUES(NULL, '');
-- INSERT INTO order_items(product_no, order_id, quantity) VALUES(-94, 61, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[quantity > 0]. Value: 0.31250000000000000001 [Sum: 0.45454545454545454546]
 	 	* Satisfy PRIMARY KEY[product_no]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-96] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-96] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != -52. Value: 0E-20 [Distance: 0]
 			 			* [-96] != [75]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != 75. Value: 0E-20 [Distance: 0]
 			 			* [-96] != [-94]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != -94. Value: 0E-20 [Distance: 0]
 			 			* [-96] != [73]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != 73. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(product_no). Value: 0E-20 [Sum: 0]
 		 		* -96, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 24, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(discounted_price). Value: 0E-20 [Sum: 0]
 		 		* 19, allowNull: false. Value: 0
 	 	* Satisfy CHECK[price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 24 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[discounted_price > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 19 > 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[price > discounted_price]. Value: 0E-20 [Sum: 0E-20]
 		 		* 24 > 19. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[order_id]. Value: 0.45454545454545454546 [Sum: 0.83333333333333333334]
 		 		* No rows to compare with - evaluating NULL. Value: 0.83333333333333333334 [Sum: 5]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [55]. Value: 1 [Best: 1]
 				 				* null != 55. Value: 1
 			 			* [null] != [-19]. Value: 1 [Best: 1]
 				 				* null != -19. Value: 1
 			 			* [null] != [26]. Value: 1 [Best: 1]
 				 				* null != 26. Value: 1
 			 			* [null] != [-24]. Value: 1 [Best: 1]
 				 				* null != -24. Value: 1
 	 	* Satisfy PRIMARY KEY[product_no, order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-94, 61] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != 0. Value: 0E-20 [Distance: 0]
 				 				* 61 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-94, 61] != [-52, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != -52. Value: 0E-20 [Distance: 0]
 				 				* 61 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[product_no]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [-94] = [-96]. Value: 0.42857142857142857143 [Sum: 0.75000000000000000000]
 				 				* -94 = -96. Value: 0.75000000000000000000 [Distance: 3]
 			 			* [-94] = [0]. Value: 0.49738219895287958116 [Sum: 0.98958333333333333334]
 				 				* -94 = 0. Value: 0.98958333333333333334 [Distance: 95]
 			 			* [-94] = [-52]. Value: 0.49425287356321839081 [Sum: 0.97727272727272727273]
 				 				* -94 = -52. Value: 0.97727272727272727273 [Distance: 43]
 			 			* [-94] = [75]. Value: 0.49853372434017595309 [Sum: 0.99415204678362573100]
 				 				* -94 = 75. Value: 0.99415204678362573100 [Distance: 170]
 			 			* [-94] = [-94]. Value: 0E-20 [Sum: 0E-20]
 				 				* -94 = -94. Value: 0E-20 [Distance: 0]
 			 			* [-94] = [73]. Value: 0.49851632047477744808 [Sum: 0.99408284023668639054]
 				 				* -94 = 73. Value: 0.99408284023668639054 [Distance: 168]
 	 	* Satisfy FOREIGN KEY[order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [61] = [null]. Value: 0E-20 [Sum: 0]
 				 				* 61 = null. Value: 0
 			 			* [61] = [0]. Value: 0.49600000000000000001 [Sum: 0.98412698412698412699]
 				 				* 61 = 0. Value: 0.98412698412698412699 [Distance: 62]
 			 			* [61] = [55]. Value: 0.46666666666666666667 [Sum: 0.87500000000000000000]
 				 				* 61 = 55. Value: 0.87500000000000000000 [Distance: 7]
 			 			* [61] = [-19]. Value: 0.49693251533742331289 [Sum: 0.98780487804878048781]
 				 				* 61 = -19. Value: 0.98780487804878048781 [Distance: 81]
 			 			* [61] = [26]. Value: 0.49315068493150684932 [Sum: 0.97297297297297297298]
 				 				* 61 = 26. Value: 0.97297297297297297298 [Distance: 36]
 			 			* [61] = [-24]. Value: 0.49710982658959537573 [Sum: 0.98850574712643678161]
 				 				* 61 = -24. Value: 0.98850574712643678161 [Distance: 86]
 	 	* Satisfy NOT NULL(quantity). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate CHECK[quantity > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 0. Value: 0E-20 [Distance: 0]*/ 

