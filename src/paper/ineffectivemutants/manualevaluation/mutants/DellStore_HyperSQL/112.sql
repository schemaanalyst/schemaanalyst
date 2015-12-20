-- 112
-- UCColumnA
-- Added UNIQUE to column lastname in table customers

CREATE TABLE "categories" (
	"category"	INT	NOT NULL,
	"categoryname"	VARCHAR(50)	NOT NULL
)

CREATE TABLE "cust_hist" (
	"customerid"	INT	NOT NULL,
	"orderid"	INT	NOT NULL,
	"prod_id"	INT	NOT NULL
)

CREATE TABLE "customers" (
	"customerid"	INT	NOT NULL,
	"firstname"	VARCHAR(50)	NOT NULL,
	"lastname"	VARCHAR(50)	UNIQUE	NOT NULL,
	"address1"	VARCHAR(50)	NOT NULL,
	"address2"	VARCHAR(50),
	"city"	VARCHAR(50)	NOT NULL,
	"state"	VARCHAR(50),
	"zip"	INT,
	"country"	VARCHAR(50)	NOT NULL,
	"region"	SMALLINT	NOT NULL,
	"email"	VARCHAR(50),
	"phone"	VARCHAR(50),
	"creditcardtype"	INT	NOT NULL,
	"creditcard"	VARCHAR(50)	NOT NULL,
	"creditcardexpiration"	VARCHAR(50)	NOT NULL,
	"username"	VARCHAR(50)	NOT NULL,
	"password"	VARCHAR(50)	NOT NULL,
	"age"	SMALLINT,
	"income"	INT,
	"gender"	VARCHAR(1)
)

CREATE TABLE "inventory" (
	"prod_id"	INT	NOT NULL,
	"quan_in_stock"	INT	NOT NULL,
	"sales"	INT	NOT NULL
)

CREATE TABLE "orderlines" (
	"orderlineid"	INT	NOT NULL,
	"orderid"	INT	NOT NULL,
	"prod_id"	INT	NOT NULL,
	"quantity"	SMALLINT	NOT NULL,
	"orderdate"	DATE	NOT NULL
)

CREATE TABLE "orders" (
	"orderid"	INT	NOT NULL,
	"orderdate"	DATE	NOT NULL,
	"customerid"	INT,
	"netamount"	NUMERIC(12, 2)	NOT NULL,
	"tax"	NUMERIC(12, 2)	NOT NULL,
	"totalamount"	NUMERIC(12, 2)	NOT NULL
)

CREATE TABLE "products" (
	"prod_id"	INT	NOT NULL,
	"category"	INT	NOT NULL,
	"title"	VARCHAR(50)	NOT NULL,
	"actor"	VARCHAR(50)	NOT NULL,
	"price"	NUMERIC(12, 2)	NOT NULL,
	"special"	SMALLINT,
	"common_prod_id"	INT	NOT NULL
)

CREATE TABLE "reorder" (
	"prod_id"	INT	NOT NULL,
	"date_low"	DATE	NOT NULL,
	"quan_low"	INT	NOT NULL,
	"date_reordered"	DATE,
	"quan_reordered"	INT,
	"date_expected"	DATE
)

