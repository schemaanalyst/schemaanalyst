/*****************************************
 * Constraint coverage for CustomerOrder *
 *****************************************/
DROP TABLE IF EXISTS db_order_item;
DROP TABLE IF EXISTS db_order;
DROP TABLE IF EXISTS db_customer;
DROP TABLE IF EXISTS db_user;
DROP TABLE IF EXISTS db_role;
DROP TABLE IF EXISTS db_product;
DROP TABLE IF EXISTS db_category;
CREATE TABLE db_category (
	id	VARCHAR(9)	PRIMARY KEY	NOT NULL,
	name	VARCHAR(30)	NOT NULL,
	parent_id	VARCHAR(9)	CONSTRAINT db_category_parent_fk  REFERENCES db_category (id)
);
CREATE TABLE db_product (
	ean_code	VARCHAR(13)	PRIMARY KEY	NOT NULL,
	name	VARCHAR(30)	NOT NULL,
	category_id	VARCHAR(9)	CONSTRAINT db_product_category_fk  REFERENCES db_category (id)	NOT NULL,
	price	DECIMAL(8, 2)	NOT NULL,
	manufacturer	VARCHAR(30)	NOT NULL,
	notes	VARCHAR(256),
	description	VARCHAR(256)
);
CREATE TABLE db_role (
	name	VARCHAR(16)	PRIMARY KEY	NOT NULL
);
CREATE TABLE db_user (
	id	INT	PRIMARY KEY	NOT NULL,
	name	VARCHAR(30)	NOT NULL,
	email	VARCHAR(50)	NOT NULL,
	password	VARCHAR(16)	NOT NULL,
	role_id	VARCHAR(16)	CONSTRAINT db_user_role_fk  REFERENCES db_role (name)	NOT NULL,
	active	SMALLINT	NOT NULL,
	CONSTRAINT active_flag CHECK (active IN (0, 1))
);
CREATE TABLE db_customer (
	id	INT	PRIMARY KEY	CONSTRAINT db_customer_user_fk  REFERENCES db_user (id)	NOT NULL,
	category	CHAR(1)	NOT NULL,
	salutation	VARCHAR(10),
	first_name	VARCHAR(30)	NOT NULL,
	last_name	VARCHAR(30)	NOT NULL,
	birth_date	DATE
);
CREATE TABLE db_order (
	id	INT	PRIMARY KEY	NOT NULL,
	customer_id	INT	CONSTRAINT db_order_customer_fk  REFERENCES db_customer (id)	NOT NULL,
	total_price	DECIMAL(8, 2)	NOT NULL,
	created_at	TIMESTAMP	NOT NULL
);
CREATE TABLE db_order_item (
	id	INT	PRIMARY KEY	NOT NULL,
	order_id	INT	CONSTRAINT db_order_item_order_fk  REFERENCES db_order (id)	NOT NULL,
	number_of_items	INT	NOT NULL,
	product_ean_code	VARCHAR(13)	CONSTRAINT db_order_item_product_fk  REFERENCES db_product (ean_code)	NOT NULL,
	total_price	DECIMAL(8, 2)	NOT NULL
);
-- Coverage: 83/84 (98.80952%) 
-- Time to generate: 9400ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 779ms 
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
INSERT INTO db_category(id, name, parent_id) VALUES('phctgpyae', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('', '', '', 0, '', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('danycpk', '', '', 0, '', '', '');
INSERT INTO db_role(name) VALUES('');
INSERT INTO db_role(name) VALUES('vpe');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(0, '', '', '', '', 0);
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(73, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(0, '', '', '', '', '1000-01-01');
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(73, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(0, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(5, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(0, 0, 0, '', 0);
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(-74, 0, 0, '', 0);
-- * Number of objective function evaluations: 155
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_category"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[parent_id]" on table "db_category"
-- * Success: false
-- * Time: 8402ms 
-- INSERT INTO db_category(id, name, parent_id) VALUES('uhl', '', NULL);
-- INSERT INTO db_category(id, name, parent_id) VALUES('ovbttr', '', 'sl');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[parent_id]. Value: 0.47058823529411764707 [Sum: 0.88888888888888888890]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['uhl'] != ['']. Value: 0 [Best: 0]
 				 				* 'uhl' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['uhl'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'uhl' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['ovbttr'] != ['uhl']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != 'uhl'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['ovbttr'] != ['']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['ovbttr'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Violate FOREIGN KEY[parent_id]. Value: 0.44444444444444444445 [Sum: 0.80000000000000000000]
 		 		* Evaluating row with reference rows. Value: 0.80000000000000000000 [Sum: 4]
 			 			* [null] != ['uhl']. Value: 1 [Best: 1]
 				 				* null != 'uhl'. Value: 1
 			 			* [null] != ['ovbttr']. Value: 1 [Best: 1]
 				 				* null != 'ovbttr'. Value: 1
 			 			* [null] != ['']. Value: 1 [Best: 1]
 				 				* null != ''. Value: 1
 			 			* [null] != ['phctgpyae']. Value: 1 [Best: 1]
 				 				* null != 'phctgpyae'. Value: 1
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0]
 			 			* ['sl'] != ['uhl']. Value: 0 [Best: 0]
 				 				* 'sl' != 'uhl'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['ovbttr']. Value: 0 [Best: 0]
 				 				* 'sl' != 'ovbttr'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['']. Value: 0 [Best: 0]
 				 				* 'sl' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'sl' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 'uhl', allowNull: false. Value: 0
 		 		* 'ovbttr', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['uhl'] != ['']. Value: 0 [Best: 0]
 				 				* 'uhl' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['uhl'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'uhl' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['ovbttr'] != ['uhl']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != 'uhl'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['ovbttr'] != ['']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['ovbttr'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'ovbttr' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Violate FOREIGN KEY[parent_id]. Value: 0.44444444444444444445 [Sum: 0.80000000000000000000]
 		 		* Evaluating row with reference rows. Value: 0.80000000000000000000 [Sum: 4]
 			 			* [null] != ['uhl']. Value: 1 [Best: 1]
 				 				* null != 'uhl'. Value: 1
 			 			* [null] != ['ovbttr']. Value: 1 [Best: 1]
 				 				* null != 'ovbttr'. Value: 1
 			 			* [null] != ['']. Value: 1 [Best: 1]
 				 				* null != ''. Value: 1
 			 			* [null] != ['phctgpyae']. Value: 1 [Best: 1]
 				 				* null != 'phctgpyae'. Value: 1
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0]
 			 			* ['sl'] != ['uhl']. Value: 0 [Best: 0]
 				 				* 'sl' != 'uhl'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['ovbttr']. Value: 0 [Best: 0]
 				 				* 'sl' != 'ovbttr'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['']. Value: 0 [Best: 0]
 				 				* 'sl' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['sl'] != ['phctgpyae']. Value: 0 [Best: 0]
 				 				* 'sl' != 'phctgpyae'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 'uhl', allowNull: false. Value: 0
 		 		* 'ovbttr', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(id)" on table "db_category"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES(NULL, '', '');
INSERT INTO db_category(id, name, parent_id) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_category"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('msnf', NULL, '');
INSERT INTO db_category(id, name, parent_id) VALUES('adw', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ean_code]" on table "db_product"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_category(id, name, parent_id) VALUES('md', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('', '', '', 0, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[category_id]" on table "db_product"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('xgjh', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('xjgr', '', 'yivkbg', 0, '', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(ean_code)" on table "db_product"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('d', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES(NULL, '', '', 0, '', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_product"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_category(id, name, parent_id) VALUES('rn', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('csvkn', NULL, '', 0, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(category_id)" on table "db_product"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_category(id, name, parent_id) VALUES('i', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('wkd', '', NULL, 0, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(price)" on table "db_product"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('xeead', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('mdy', '', '', NULL, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(manufacturer)" on table "db_product"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('qyv', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('fwrqtxqh', '', '', 0, NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[name]" on table "db_role"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES('');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_role"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES(NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_user"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES('rlwiqevo');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(0, '', '', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[role_id]" on table "db_user"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('kd');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-84, '', '', '', 'cmjfng', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_user"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('sanvgpky');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(NULL, '', '', '', '', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_user"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES('ppwnbqypr');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(25, NULL, '', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(email)" on table "db_user"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES('hsu');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-90, '', NULL, '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(password)" on table "db_user"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('lmjxmj');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(96, '', '', NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(role_id)" on table "db_user"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('hhxxfisj');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(68, '', '', '', NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(active)" on table "db_user"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_role(name) VALUES('cij');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-9, '', '', '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[active IN (0, 1)]" on table "db_user"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('wie');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-25, '', '', '', '', 52);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_customer"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('cvipyctb');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(6, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(0, '', '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[id]" on table "db_customer"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('h');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(51, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(87, '', '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_customer"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('oyhkrgna');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-92, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(NULL, '', '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(category)" on table "db_customer"
-- * Success: true
-- * Time: 2ms 
INSERT INTO db_role(name) VALUES('nnds');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-90, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(51, NULL, '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "db_customer"
-- * Success: true
-- * Time: 2ms 
INSERT INTO db_role(name) VALUES('exspi');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-2, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(6, '', '', NULL, '', '1000-01-01');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "db_customer"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('oqkcdjvf');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(28, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-92, '', '', '', NULL, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_order"
-- * Success: true
-- * Time: 9ms 
INSERT INTO db_role(name) VALUES('ukrs');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(9, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-2, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[customer_id]" on table "db_order"
-- * Success: true
-- * Time: 4ms 
INSERT INTO db_role(name) VALUES('q');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(36, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-92, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(79, 74, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_order"
-- * Success: true
-- * Time: 4ms 
INSERT INTO db_role(name) VALUES('kkd');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(12, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-90, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(NULL, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(customer_id)" on table "db_order"
-- * Success: true
-- * Time: 7ms 
INSERT INTO db_role(name) VALUES('jildaf');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-36, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(51, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-18, NULL, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(total_price)" on table "db_order"
-- * Success: true
-- * Time: 5ms 
INSERT INTO db_role(name) VALUES('ui');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(94, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(12, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(87, 0, NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(created_at)" on table "db_order"
-- * Success: true
-- * Time: 8ms 
INSERT INTO db_role(name) VALUES('lj');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(49, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(36, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(16, 0, 0, NULL);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_order_item"
-- * Success: true
-- * Time: 20ms 
INSERT INTO db_role(name) VALUES('sag');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(32, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(28, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-95, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('coedvxcm', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('frna', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(0, 0, 0, '', 0);
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[order_id]" on table "db_order_item"
-- * Success: true
-- * Time: 19ms 
INSERT INTO db_role(name) VALUES('yp');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-60, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(49, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-56, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('qpf', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('rvimj', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(53, 68, 0, '', 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[product_ean_code]" on table "db_order_item"
-- * Success: true
-- * Time: 13ms 
INSERT INTO db_role(name) VALUES('rppjeyp');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-75, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-75, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(76, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('dm', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('d', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(88, 0, 0, 'okajibve', 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_order_item"
-- * Success: true
-- * Time: 6ms 
INSERT INTO db_role(name) VALUES('gjxif');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-70, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(32, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(99, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('cc', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('sufqdlpi', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(NULL, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(order_id)" on table "db_order_item"
-- * Success: true
-- * Time: 6ms 
INSERT INTO db_role(name) VALUES('biqhcby');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-67, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(9, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-100, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('oivvwjl', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('tdshehc', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(22, NULL, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(number_of_items)" on table "db_order_item"
-- * Success: true
-- * Time: 20ms 
INSERT INTO db_role(name) VALUES('uknw');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(62, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(94, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-29, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('twq', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('rdbwev', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(17, 0, NULL, '', 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(product_ean_code)" on table "db_order_item"
-- * Success: true
-- * Time: 38ms 
INSERT INTO db_role(name) VALUES('gqtteh');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(39, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-60, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(97, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('lyxkynlmq', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('n', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(80, 0, 0, NULL, 0);
-- * Number of objective function evaluations: 7
-- * Number of restarts: 0

-- Negating "NOT NULL(total_price)" on table "db_order_item"
-- * Success: true
-- * Time: 40ms 
INSERT INTO db_role(name) VALUES('gm');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(75, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-36, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(2, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_category(id, name, parent_id) VALUES('cpgw', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('m', '', '', 0, '', '', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(-63, 0, 0, '', NULL);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

