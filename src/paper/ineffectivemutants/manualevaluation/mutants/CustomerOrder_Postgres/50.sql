-- 50
-- NNCR
-- Removed NOT NULL to column role_id in table db_user

CREATE TABLE "db_category" (
	"id"	VARCHAR(9)	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(30)	NOT NULL,
	"parent_id"	VARCHAR(9)	CONSTRAINT "db_category_parent_fk"  REFERENCES "db_category" ("id")
)

CREATE TABLE "db_product" (
	"ean_code"	VARCHAR(13)	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(30)	NOT NULL,
	"category_id"	VARCHAR(9)	CONSTRAINT "db_product_category_fk"  REFERENCES "db_category" ("id")	NOT NULL,
	"price"	DECIMAL(8, 2)	NOT NULL,
	"manufacturer"	VARCHAR(30)	NOT NULL,
	"notes"	VARCHAR(256),
	"description"	VARCHAR(256)
)

CREATE TABLE "db_role" (
	"name"	VARCHAR(16)	PRIMARY KEY	NOT NULL
)

CREATE TABLE "db_user" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(30)	NOT NULL,
	"email"	VARCHAR(50)	NOT NULL,
	"password"	VARCHAR(16)	NOT NULL,
	"role_id"	VARCHAR(16)	CONSTRAINT "db_user_role_fk"  REFERENCES "db_role" ("name"),
	"active"	SMALLINT	NOT NULL,
	CONSTRAINT "active_flag" CHECK ("active" IN (0, 1))
)

CREATE TABLE "db_customer" (
	"id"	INT	PRIMARY KEY	CONSTRAINT "db_customer_user_fk"  REFERENCES "db_user" ("id")	NOT NULL,
	"category"	CHAR(1)	NOT NULL,
	"salutation"	VARCHAR(10),
	"first_name"	VARCHAR(30)	NOT NULL,
	"last_name"	VARCHAR(30)	NOT NULL,
	"birth_date"	DATE
)

CREATE TABLE "db_order" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"customer_id"	INT	CONSTRAINT "db_order_customer_fk"  REFERENCES "db_customer" ("id")	NOT NULL,
	"total_price"	DECIMAL(8, 2)	NOT NULL,
	"created_at"	TIMESTAMP	NOT NULL
)

CREATE TABLE "db_order_item" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"order_id"	INT	CONSTRAINT "db_order_item_order_fk"  REFERENCES "db_order" ("id")	NOT NULL,
	"number_of_items"	INT	NOT NULL,
	"product_ean_code"	VARCHAR(13)	CONSTRAINT "db_order_item_product_fk"  REFERENCES "db_product" ("ean_code")	NOT NULL,
	"total_price"	DECIMAL(8, 2)	NOT NULL
)

