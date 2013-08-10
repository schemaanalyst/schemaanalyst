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
-- Coverage: 84/84 (100.00000%) 
-- Time to generate: 18865ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 536ms 
INSERT INTO db_category(id, name, parent_id) VALUES('a', '', '');
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('a', '', '', 0, '', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('', '', '', 0, '', '', '');
INSERT INTO db_role(name) VALUES('a');
INSERT INTO db_role(name) VALUES('');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(1, '', '', '', '', 0);
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(0, '', '', '', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(1, '', '', '', '', '1000-01-01');
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(0, '', '', '', '', '1000-01-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(1, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(0, 0, 0, '1970-01-01 00:00:00');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(1, 0, 0, '', 0);
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(0, 0, 0, '', 0);
-- * Number of objective function evaluations: 164
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "db_category"
-- * Success: true
-- * Time: 0ms 
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
INSERT INTO db_category(id, name, parent_id) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[parent_id]" on table "db_category"
-- * Success: true
-- * Time: 25ms 
INSERT INTO db_category(id, name, parent_id) VALUES('b', '', '`');
INSERT INTO db_category(id, name, parent_id) VALUES('^', '', '`');
-- * Number of objective function evaluations: 49
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_category"
-- * Success: true
-- * Time: 2ms 
INSERT INTO db_category(id, name, parent_id) VALUES(NULL, '', '');
INSERT INTO db_category(id, name, parent_id) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 7
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_category"
-- * Success: true
-- * Time: 16ms 
INSERT INTO db_category(id, name, parent_id) VALUES('b', NULL, '');
INSERT INTO db_category(id, name, parent_id) VALUES('`', NULL, '');
-- * Number of objective function evaluations: 31
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ean_code]" on table "db_product"
-- * Success: true
-- * Time: 1ms 
INSERT INTO db_category(id, name, parent_id) VALUES('b', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('', '', '', 0, '', '', '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[category_id]" on table "db_product"
-- * Success: true
-- * Time: 15ms 
INSERT INTO db_category(id, name, parent_id) VALUES('`', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('b', '', 'aa', 0, '', '', '');
-- * Number of objective function evaluations: 39
-- * Number of restarts: 0

-- Negating "NOT NULL(ean_code)" on table "db_product"
-- * Success: true
-- * Time: 8ms 
INSERT INTO db_category(id, name, parent_id) VALUES('aa', '', '');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES(NULL, '', '', 0, '', '', '');
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_product"
-- * Success: true
-- * Time: 166ms 
INSERT INTO db_category(id, name, parent_id) VALUES('jvtywey', '', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('r', NULL, '', -100, 'm', 'mirpis', 'baicvahnw');
-- * Number of objective function evaluations: 292
-- * Number of restarts: 2

-- Negating "NOT NULL(category_id)" on table "db_product"
-- * Success: true
-- * Time: 237ms 
INSERT INTO db_category(id, name, parent_id) VALUES('yparpusp', 'jlriarsp', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('kiq', 'vuabq', NULL, -38, 'akxfq', 'necrjoxn', 'pmeif');
-- * Number of objective function evaluations: 215
-- * Number of restarts: 1

-- Negating "NOT NULL(price)" on table "db_product"
-- * Success: true
-- * Time: 111ms 
INSERT INTO db_category(id, name, parent_id) VALUES('ehsbcwnc', '', 'b');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('yvxabof', 'xfwagq', 'aa', NULL, 'shpalcrku', 'uilsnx', 'dx');
-- * Number of objective function evaluations: 152
-- * Number of restarts: 1

-- Negating "NOT NULL(manufacturer)" on table "db_product"
-- * Success: true
-- * Time: 46ms 
INSERT INTO db_category(id, name, parent_id) VALUES('glffudw', 'cuq', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('oojar', 'dyuv', 'a', -88, NULL, 'raglk', 'smjhwuo');
-- * Number of objective function evaluations: 137
-- * Number of restarts: 1

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
-- * Time: 1ms 
INSERT INTO db_role(name) VALUES('b');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(0, '', '', '', '', 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[role_id]" on table "db_user"
-- * Success: true
-- * Time: 7ms 
INSERT INTO db_role(name) VALUES('`');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-1, '', '', '', 'aa', 0);
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "db_user"
-- * Success: true
-- * Time: 2ms 
INSERT INTO db_role(name) VALUES('aa');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(NULL, '', '', '', '', 0);
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "db_user"
-- * Success: true
-- * Time: 22ms 
INSERT INTO db_role(name) VALUES('huqcx');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(75, NULL, 'jjnaatg', 'sed', '', 0);
-- * Number of objective function evaluations: 97
-- * Number of restarts: 1

-- Negating "NOT NULL(email)" on table "db_user"
-- * Success: true
-- * Time: 86ms 
INSERT INTO db_role(name) VALUES('ttqnape');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(77, 'vubgv', NULL, 'gga', 'ttqnape', 0);
-- * Number of objective function evaluations: 241
-- * Number of restarts: 1

-- Negating "NOT NULL(password)" on table "db_user"
-- * Success: true
-- * Time: 46ms 
INSERT INTO db_role(name) VALUES('qmkqa');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-57, 'ontfy', 'jdf', NULL, 'qmkqa', 1);
-- * Number of objective function evaluations: 166
-- * Number of restarts: 1

-- Negating "NOT NULL(role_id)" on table "db_user"
-- * Success: true
-- * Time: 16ms 
INSERT INTO db_role(name) VALUES('lw');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(34, 'bdnxa', 'u', 'jbtanj', NULL, 1);
-- * Number of objective function evaluations: 88
-- * Number of restarts: 1

-- Negating "NOT NULL(active)" on table "db_user"
-- * Success: true
-- * Time: 22ms 
INSERT INTO db_role(name) VALUES('cvmnroxjb');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-65, 'jorebf', 'a', 'mgq', 'huqcx', NULL);
-- * Number of objective function evaluations: 160
-- * Number of restarts: 1

-- Negating "CHECK[active IN (0, 1)]" on table "db_user"
-- * Success: true
-- * Time: 18ms 
INSERT INTO db_role(name) VALUES('tyx');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(37, 'kofxhuc', 'sql', 'gpcvuhl', 'aa', 37);
-- * Number of objective function evaluations: 158
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[id]" on table "db_customer"
-- * Success: true
-- * Time: 15ms 
INSERT INTO db_role(name) VALUES('jbbf');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-90, 'cslyhfuq', 'lervh', 'nj', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(0, 'r', 'seynnngk', 'pxqmsjtcg', 'hrbhmoob', '1996-07-30');
-- * Number of objective function evaluations: 141
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[id]" on table "db_customer"
-- * Success: true
-- * Time: 23ms 
INSERT INTO db_role(name) VALUES('fymegl');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-49, 'nfxtnq', 'mryguh', 'leawm', 'lw', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-72, 'r', '', 'bxq', '', '2011-02-14');
-- * Number of objective function evaluations: 155
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "db_customer"
-- * Success: true
-- * Time: 76ms 
INSERT INTO db_role(name) VALUES('doyklv');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(66, 'lkohdrgoi', 'mtlcjmvnv', 'tdupnwq', 'aa', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(NULL, 'a', 'oqwat', 'ed', 'kug', '2011-06-17');
-- * Number of objective function evaluations: 442
-- * Number of restarts: 2

-- Negating "NOT NULL(category)" on table "db_customer"
-- * Success: true
-- * Time: 52ms 
INSERT INTO db_role(name) VALUES('tdyqhsh');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-45, 'chstjokh', 'b', 'abjpksh', 'ttqnape', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(66, NULL, 'k', 'abqpvuh', '', '2019-11-08');
-- * Number of objective function evaluations: 268
-- * Number of restarts: 1

-- Negating "NOT NULL(first_name)" on table "db_customer"
-- * Success: true
-- * Time: 150ms 
INSERT INTO db_role(name) VALUES('rlty');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(4, 'pkxn', 'filwu', 'n', '', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-49, 'n', 'wdxhcq', NULL, '', '1993-09-14');
-- * Number of objective function evaluations: 742
-- * Number of restarts: 3

-- Negating "NOT NULL(last_name)" on table "db_customer"
-- * Success: true
-- * Time: 54ms 
INSERT INTO db_role(name) VALUES('uunqoj');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-24, 'iwha', 'nhmywbu', 'qe', 'qmkqa', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-90, 'r', 'hxxjelwl', 'biiai', NULL, '2009-09-21');
-- * Number of objective function evaluations: 296
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[id]" on table "db_order"
-- * Success: true
-- * Time: 126ms 
INSERT INTO db_role(name) VALUES('ugjbgdob');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(67, 'ww', 'c', '', 'doyklv', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-49, '', 'anndy', 'eidmhiac', 'mdpyucvvp', '2014-11-01');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(1, 0, 49, '2004-11-12 01:32:35');
-- * Number of objective function evaluations: 347
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[customer_id]" on table "db_order"
-- * Success: true
-- * Time: 128ms 
INSERT INTO db_role(name) VALUES('jjkuvypv');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-17, '', 'rbfeb', 'cqshd', 'qmkqa', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(66, '', 'tgryd', 'n', 'h', '2014-10-14');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-62, 95, 49, '1997-01-25 14:18:20');
-- * Number of objective function evaluations: 281
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "db_order"
-- * Success: true
-- * Time: 67ms 
INSERT INTO db_role(name) VALUES('yltqi');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-36, '', 'ru', 'onj', 'lw', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(67, 'i', 'lgciogq', '', 'qdrmemn', '2019-08-14');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(NULL, 67, 8, '2018-03-13 05:23:10');
-- * Number of objective function evaluations: 262
-- * Number of restarts: 1

-- Negating "NOT NULL(customer_id)" on table "db_order"
-- * Success: true
-- * Time: 71ms 
INSERT INTO db_role(name) VALUES('qy');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-25, 'jfkvhk', 'xiprump', 'jmvys', '', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-36, 'm', 'hctneh', 'gklldkmh', 'ptvprup', '1995-04-28');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(8, NULL, -84, '1990-11-16 08:17:31');
-- * Number of objective function evaluations: 269
-- * Number of restarts: 1

-- Negating "NOT NULL(total_price)" on table "db_order"
-- * Success: true
-- * Time: 248ms 
INSERT INTO db_role(name) VALUES('olxox');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-82, 'xkuqvvi', '', 'rx', 'yltqi', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-82, 'f', '', 'sexgf', 'rdg', '2004-07-02');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(47, 0, NULL, '1999-12-20 06:56:44');
-- * Number of objective function evaluations: 750
-- * Number of restarts: 2

-- Negating "NOT NULL(created_at)" on table "db_order"
-- * Success: true
-- * Time: 411ms 
INSERT INTO db_role(name) VALUES('cesc');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(40, 'fi', 'olmtldatd', 'wnqosnbt', 'huqcx', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(40, 'd', 'ofpc', 'ajvmsupdd', 'twtdgd', '2002-02-08');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(4, -36, 20, NULL);
-- * Number of objective function evaluations: 1414
-- * Number of restarts: 4

-- Negating "PRIMARY KEY[id]" on table "db_order_item"
-- * Success: true
-- * Time: 1732ms 
INSERT INTO db_role(name) VALUES('rrfuop');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(65, 'oxjdpdwso', 'ggysber', 'pnfkotlyk', 'uunqoj', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-45, '', '', 'uvvcxiq', 'u', '1996-08-29');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(44, 66, -21, '2008-07-25 18:20:13');
INSERT INTO db_category(id, name, parent_id) VALUES('vvnoa', 'lfwudbbag', 'vvnoa');
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('vwjfsyqi', 'cfoxo', 'jvtywey', -72, 'oqqo', 'ake', 'emettwrpi');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(0, 0, -59, 'vwjfsyqi', -41);
-- * Number of objective function evaluations: 3748
-- * Number of restarts: 5

-- Negating "FOREIGN KEY[order_id]" on table "db_order_item"
-- * Success: true
-- * Time: 1178ms 
INSERT INTO db_role(name) VALUES('awit');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(17, 'qdqq', 'a', 'wyidxp', 'yltqi', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(17, 'v', 'ggmh', 'eqxnkrc', 'qbq', '2018-05-15');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(8, -82, 38, '2007-07-07 23:55:14');
INSERT INTO db_category(id, name, parent_id) VALUES('gskw', 'xqi', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('fosvbv', 'dyk', 'aa', -10, 'rq', 'vsoht', 'cokrfit');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(44, -21, 3, '', -78);
-- * Number of objective function evaluations: 2207
-- * Number of restarts: 3

-- Negating "FOREIGN KEY[product_ean_code]" on table "db_order_item"
-- * Success: true
-- * Time: 1579ms 
INSERT INTO db_role(name) VALUES('jrefyo');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(78, 'st', 'pcitffs', 'siip', 'tdyqhsh', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(78, 'g', 'idvxmxqgj', 'kbvyqepql', 'hshr', '2000-07-24');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-19, -82, 18, '2009-01-04 15:17:16');
INSERT INTO db_category(id, name, parent_id) VALUES('aekwy', 'tvnirfyjt', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('k', 'xbpxwpbq', '', 27, 'reyjsikv', '', 'cx');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(-92, 0, -44, 'vxp', -98);
-- * Number of objective function evaluations: 3122
-- * Number of restarts: 5

-- Negating "NOT NULL(id)" on table "db_order_item"
-- * Success: true
-- * Time: 1275ms 
INSERT INTO db_role(name) VALUES('dt');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(36, 'rtmdeoj', 'jhsygfsdu', 'nu', 'huqcx', 0);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(65, 'i', 'nuykse', 'vtiisxbvs', 'qijyxgjhv', '2002-11-21');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(23, 78, -32, '1992-09-17 20:56:34');
INSERT INTO db_category(id, name, parent_id) VALUES('pr', '', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('kfklxjfk', 'pgnguy', 'a', -12, 'irymr', 'jcb', 'my');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(NULL, 8, 72, 'kfklxjfk', 1);
-- * Number of objective function evaluations: 2213
-- * Number of restarts: 3

-- Negating "NOT NULL(order_id)" on table "db_order_item"
-- * Success: true
-- * Time: 3242ms 
INSERT INTO db_role(name) VALUES('ioaolrvph');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(64, 'oxfm', 'vdnkm', 'jdm', 'awit', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-90, 'g', 'llcfrocfp', 'yrlvfujx', 'wnhcp', '2014-10-04');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(17, -36, -28, '1992-02-08 04:34:58');
INSERT INTO db_category(id, name, parent_id) VALUES('vwice', 'uhb', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('svcshu', 'jthrbk', '', 48, 'qv', 'chvblttf', 'vfoy');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(9, NULL, -38, 'a', 69);
-- * Number of objective function evaluations: 5990
-- * Number of restarts: 10

-- Negating "NOT NULL(number_of_items)" on table "db_order_item"
-- * Success: true
-- * Time: 3516ms 
INSERT INTO db_role(name) VALUES('doekgq');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(68, 'ewmre', 'wusohd', 'nqqtlo', '', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-24, 'k', 'oqyefe', 'jhyfmjo', 'xob', '1997-03-23');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(18, -90, 54, '1997-03-20 03:02:14');
INSERT INTO db_category(id, name, parent_id) VALUES('oqxbb', '', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('iywhfhwi', 'qbnilhc', 'vwice', 82, 'r', 'mafhj', '');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(97, 23, NULL, 'fosvbv', 19);
-- * Number of objective function evaluations: 5497
-- * Number of restarts: 8

-- Negating "NOT NULL(product_ean_code)" on table "db_order_item"
-- * Success: true
-- * Time: 260ms 
INSERT INTO db_role(name) VALUES('xqkac');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(-98, 'jihcev', 'jbpb', 'e', '', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(68, 'y', 'whsjhm', 'xbhuxpo', 'ojllekpo', '2012-06-14');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(-54, -36, 64, '1992-07-12 17:35:40');
INSERT INTO db_category(id, name, parent_id) VALUES('tjjtge', 'bkdiltjre', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('dpts', 'c', 'a', -36, 'ajpnxsret', 'mvhdyysdk', 'tdoc');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(94, 44, -81, NULL, 37);
-- * Number of objective function evaluations: 538
-- * Number of restarts: 1

-- Negating "NOT NULL(total_price)" on table "db_order_item"
-- * Success: true
-- * Time: 3279ms 
INSERT INTO db_role(name) VALUES('elipnpwg');
INSERT INTO db_user(id, name, email, password, role_id, active) VALUES(87, 'ao', 'abclttfs', 'svlu', '', 1);
INSERT INTO db_customer(id, category, salutation, first_name, last_name, birth_date) VALUES(-98, 'r', 'druibaise', 'klpwdwwn', 'ldbngjiy', '2017-09-20');
INSERT INTO db_order(id, customer_id, total_price, created_at) VALUES(19, -49, -4, '1996-10-27 16:01:09');
INSERT INTO db_category(id, name, parent_id) VALUES('fgiqocldl', 'ifusqkd', NULL);
INSERT INTO db_product(ean_code, name, category_id, price, manufacturer, notes, description) VALUES('gytco', '', 'vvnoa', -33, 'y', 'joxmsca', 'giljsq');
INSERT INTO db_order_item(id, order_id, number_of_items, product_ean_code, total_price) VALUES(-11, -19, 7, 'k', NULL);
-- * Number of objective function evaluations: 5134
-- * Number of restarts: 8

