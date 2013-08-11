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
-- Coverage: 36/38 (94.73684%) 
-- Time to generate: 24620ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 23ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(0, '', 0);
INSERT INTO coffees(id, coffee_name, price) VALUES(-52, '', 0);
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(0, '', '', 0);
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(10, '', '', 0);
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(0, '', '', '', '', '');
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-34, '', '', '', '', '');
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(0, 0, 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(96, 0, 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(0, 0, 0, 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(88, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "coffees"
-- * Success: true
-- * Time: 1ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(coffee_name)" on table "coffees"
-- * Success: true
-- * Time: 1ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(-75, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "coffees"
-- * Success: true
-- * Time: 1ms 
INSERT INTO coffees(id, coffee_name, price) VALUES(-96, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "salespeople"
-- * Success: true
-- * Time: 1ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(0, '', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "salespeople"
-- * Success: true
-- * Time: 1ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(92, NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "salespeople"
-- * Success: true
-- * Time: 2ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(25, '', NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(commission_rate)" on table "salespeople"
-- * Success: true
-- * Time: 2ms 
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(55, '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "customers"
-- * Success: true
-- * Time: 1ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(0, '', '', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(company_name)" on table "customers"
-- * Success: true
-- * Time: 1ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-3, NULL, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(street_address)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(46, '', NULL, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(67, '', '', NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(state)" on table "customers"
-- * Success: true
-- * Time: 2ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(79, '', '', '', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(zip)" on table "customers"
-- * Success: true
-- * Time: 3ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-84, '', '', '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "orders"
-- * Success: true
-- * Time: 8ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(44, '', '', '', '', '');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-72, '', '', 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[customer_id]" on table "orders"
-- * Success: false
-- * Time: 8585ms 
-- INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-100, '', '', '', '', '');
-- INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(NULL, '', '', 0);
-- INSERT INTO orders(id, customer_id, salesperson_id) VALUES(-33, 94, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[customer_id]. Value: 0.30000000000000000001 [Sum: 0.42857142857142857143]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-100] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -100 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-100] != [-34]. Value: 0E-20 [Best: 0E-20]
 				 				* -100 != -34. Value: 0E-20 [Distance: 0]
 			 			* [-100] != [44]. Value: 0E-20 [Best: 0E-20]
 				 				* -100 != 44. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(company_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(street_address). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(city). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(state). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(zip). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0.42857142857142857143 [Sum: 0.75000000000000000000]
 		 		* No rows to compare with - evaluating NULL. Value: 0.75000000000000000000 [Sum: 3]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [10]. Value: 1 [Best: 1]
 				 				* null != 10. Value: 1
 			 			* [null] != [-72]. Value: 1 [Best: 1]
 				 				* null != -72. Value: 1
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(commission_rate). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-33] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -33 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-33] != [96]. Value: 0E-20 [Best: 0E-20]
 				 				* -33 != 96. Value: 0E-20 [Distance: 0]
 	 	* Violate FOREIGN KEY[customer_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0E-20]
 			 			* [94] != [-100]. Value: 0E-20 [Best: 0E-20]
 				 				* 94 != -100. Value: 0E-20 [Distance: 0]
 			 			* [94] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 94 != 0. Value: 0E-20 [Distance: 0]
 			 			* [94] != [-34]. Value: 0E-20 [Best: 0E-20]
 				 				* 94 != -34. Value: 0E-20 [Distance: 0]
 			 			* [94] != [44]. Value: 0E-20 [Best: 0E-20]
 				 				* 94 != 44. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[salesperson_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [null]. Value: 0E-20 [Sum: 0]
 				 				* 0 = null. Value: 0
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [10]. Value: 0.47826086956521739131 [Sum: 0.91666666666666666667]
 				 				* 0 = 10. Value: 0.91666666666666666667 [Distance: 11]
 			 			* [0] = [-72]. Value: 0.49659863945578231293 [Sum: 0.98648648648648648649]
 				 				* 0 = -72. Value: 0.98648648648648648649 [Distance: 73]*/ 

-- Negating "FOREIGN KEY[salesperson_id]" on table "orders"
-- * Success: true
-- * Time: 1ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-99, '', '', '', '', '');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(39, '', '', 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(52, 0, -20);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "order_items"
-- * Success: true
-- * Time: 1ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-98, '', '', '', '', '');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-86, '', '', 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(-36, 0, 0);
INSERT INTO coffees(id, coffee_name, price) VALUES(-15, '', 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[order_id]" on table "order_items"
-- * Success: false
-- * Time: 15982ms 
-- INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(87, '', '', '', '', '');
-- INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-42, '', '', 0);
-- INSERT INTO orders(id, customer_id, salesperson_id) VALUES(-8, 0, 0);
-- INSERT INTO coffees(id, coffee_name, price) VALUES(12, '', 0);
-- INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(NULL, 55, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[order_id]. Value: 0.28571428571428571430 [Sum: 0.40000000000000000001]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-8] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -8 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-8] != [96]. Value: 0E-20 [Best: 0E-20]
 				 				* -8 != 96. Value: 0E-20 [Distance: 0]
 			 			* [-8] != [-36]. Value: 0E-20 [Best: 0E-20]
 				 				* -8 != -36. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[customer_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [87]. Value: 0.49717514124293785311 [Sum: 0.98876404494382022472]
 				 				* 0 = 87. Value: 0.98876404494382022472 [Distance: 88]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-34]. Value: 0.49295774647887323944 [Sum: 0.97222222222222222223]
 				 				* 0 = -34. Value: 0.97222222222222222223 [Distance: 35]
 			 			* [0] = [44]. Value: 0.49450549450549450550 [Sum: 0.97826086956521739131]
 				 				* 0 = 44. Value: 0.97826086956521739131 [Distance: 45]
 			 			* [0] = [-99]. Value: 0.49751243781094527364 [Sum: 0.99009900990099009901]
 				 				* 0 = -99. Value: 0.99009900990099009901 [Distance: 100]
 			 			* [0] = [-98]. Value: 0.49748743718592964825 [Sum: 0.99000000000000000000]
 				 				* 0 = -98. Value: 0.99000000000000000000 [Distance: 99]
 	 	* Satisfy FOREIGN KEY[salesperson_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-42]. Value: 0.49425287356321839081 [Sum: 0.97727272727272727273]
 				 				* 0 = -42. Value: 0.97727272727272727273 [Distance: 43]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [10]. Value: 0.47826086956521739131 [Sum: 0.91666666666666666667]
 				 				* 0 = 10. Value: 0.91666666666666666667 [Distance: 11]
 			 			* [0] = [-72]. Value: 0.49659863945578231293 [Sum: 0.98648648648648648649]
 				 				* 0 = -72. Value: 0.98648648648648648649 [Distance: 73]
 			 			* [0] = [39]. Value: 0.49382716049382716050 [Sum: 0.97560975609756097561]
 				 				* 0 = 39. Value: 0.97560975609756097561 [Distance: 40]
 			 			* [0] = [-86]. Value: 0.49714285714285714286 [Sum: 0.98863636363636363637]
 				 				* 0 = -86. Value: 0.98863636363636363637 [Distance: 87]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [12] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 12 != 0. Value: 0E-20 [Distance: 0]
 			 			* [12] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* 12 != -52. Value: 0E-20 [Distance: 0]
 			 			* [12] != [-15]. Value: 0E-20 [Best: 0E-20]
 				 				* 12 != -15. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(coffee_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(price). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [87] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 87 != 0. Value: 0E-20 [Distance: 0]
 			 			* [87] != [-34]. Value: 0E-20 [Best: 0E-20]
 				 				* 87 != -34. Value: 0E-20 [Distance: 0]
 			 			* [87] != [44]. Value: 0E-20 [Best: 0E-20]
 				 				* 87 != 44. Value: 0E-20 [Distance: 0]
 			 			* [87] != [-99]. Value: 0E-20 [Best: 0E-20]
 				 				* 87 != -99. Value: 0E-20 [Distance: 0]
 			 			* [87] != [-98]. Value: 0E-20 [Best: 0E-20]
 				 				* 87 != -98. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(company_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(street_address). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(city). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(state). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(zip). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-42] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -42 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-42] != [10]. Value: 0E-20 [Best: 0E-20]
 				 				* -42 != 10. Value: 0E-20 [Distance: 0]
 			 			* [-42] != [-72]. Value: 0E-20 [Best: 0E-20]
 				 				* -42 != -72. Value: 0E-20 [Distance: 0]
 			 			* [-42] != [39]. Value: 0E-20 [Best: 0E-20]
 				 				* -42 != 39. Value: 0E-20 [Distance: 0]
 			 			* [-42] != [-86]. Value: 0E-20 [Best: 0E-20]
 				 				* -42 != -86. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(commission_rate). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0.40000000000000000001 [Sum: 0.66666666666666666667]
 		 		* No rows to compare with - evaluating NULL. Value: 0.66666666666666666667 [Sum: 2]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [88]. Value: 1 [Best: 1]
 				 				* null != 88. Value: 1
 	 	* Violate FOREIGN KEY[order_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0E-20]
 			 			* [55] != [-8]. Value: 0E-20 [Best: 0E-20]
 				 				* 55 != -8. Value: 0E-20 [Distance: 0]
 			 			* [55] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 55 != 0. Value: 0E-20 [Distance: 0]
 			 			* [55] != [96]. Value: 0E-20 [Best: 0E-20]
 				 				* 55 != 96. Value: 0E-20 [Distance: 0]
 			 			* [55] != [-36]. Value: 0E-20 [Best: 0E-20]
 				 				* 55 != -36. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[product_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [12]. Value: 0.48148148148148148149 [Sum: 0.92857142857142857143]
 				 				* 0 = 12. Value: 0.92857142857142857143 [Distance: 13]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 			 			* [0] = [-15]. Value: 0.48484848484848484849 [Sum: 0.94117647058823529412]
 				 				* 0 = -15. Value: 0.94117647058823529412 [Distance: 16]*/ 

-- Negating "FOREIGN KEY[product_id]" on table "order_items"
-- * Success: true
-- * Time: 0ms 
INSERT INTO customers(id, company_name, street_address, city, state, zip) VALUES(-62, '', '', '', '', '');
INSERT INTO salespeople(id, first_name, last_name, commission_rate) VALUES(-23, '', '', 0);
INSERT INTO orders(id, customer_id, salesperson_id) VALUES(-55, 0, 0);
INSERT INTO coffees(id, coffee_name, price) VALUES(48, '', 0);
INSERT INTO order_items(id, order_id, product_id, product_quantity) VALUES(28, 0, 91, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

