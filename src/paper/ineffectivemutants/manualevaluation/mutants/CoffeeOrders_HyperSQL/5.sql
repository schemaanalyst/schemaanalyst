-- 5
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(customer_id, id) with Pair(id, id)

CREATE TABLE "coffees" (
	"id"	INT	PRIMARY KEY,
	"coffee_name"	VARCHAR(50)	NOT NULL,
	"price"	INT	NOT NULL
)

CREATE TABLE "salespeople" (
	"id"	INT	PRIMARY KEY,
	"first_name"	VARCHAR(50)	NOT NULL,
	"last_name"	VARCHAR(50)	NOT NULL,
	"commission_rate"	INT	NOT NULL
)

CREATE TABLE "customers" (
	"id"	INT	PRIMARY KEY,
	"company_name"	VARCHAR(50)	NOT NULL,
	"street_address"	VARCHAR(50)	NOT NULL,
	"city"	VARCHAR(50)	NOT NULL,
	"state"	VARCHAR(50)	NOT NULL,
	"zip"	VARCHAR(50)	NOT NULL
)

CREATE TABLE "orders" (
	"id"	INT	PRIMARY KEY	 REFERENCES "customers" ("id"),
	"customer_id"	INT,
	"salesperson_id"	INT	 REFERENCES "salespeople" ("id")
)

CREATE TABLE "order_items" (
	"id"	INT	PRIMARY KEY,
	"order_id"	INT	 REFERENCES "orders" ("id"),
	"product_id"	INT	 REFERENCES "coffees" ("id"),
	"product_quantity"	INT
)

