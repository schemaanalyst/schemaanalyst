-- 27
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(product_no, product_no) with Pair(order_id, product_no)

CREATE TABLE "products" (
	"product_no"	INT	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(100)	NOT NULL,
	"price"	NUMERIC	NOT NULL,
	"discounted_price"	NUMERIC	NOT NULL,
	CHECK ("price" > 0),
	CHECK ("discounted_price" > 0),
	CHECK ("price" > "discounted_price")
)

CREATE TABLE "orders" (
	"order_id"	INT	PRIMARY KEY,
	"shipping_address"	VARCHAR(100)
)

CREATE TABLE "order_items" (
	"product_no"	INT,
	"order_id"	INT	 REFERENCES "orders" ("order_id")	 REFERENCES "products" ("product_no"),
	"quantity"	INT	NOT NULL,
	PRIMARY KEY ("product_no", "order_id"),
	CHECK ("quantity" > 0)
)

