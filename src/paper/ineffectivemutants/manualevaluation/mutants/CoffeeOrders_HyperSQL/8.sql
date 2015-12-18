-- 8
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(salesperson_id, id) with Pair(customer_id, id)

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
	"id"	INT	PRIMARY KEY,
	"customer_id"	INT	 REFERENCES "customers" ("id")	 REFERENCES "salespeople" ("id"),
	"salesperson_id"	INT
)

CREATE TABLE "order_items" (
	"id"	INT	PRIMARY KEY,
	"order_id"	INT	 REFERENCES "orders" ("id"),
	"product_id"	INT	 REFERENCES "coffees" ("id"),
	"product_quantity"	INT
)

