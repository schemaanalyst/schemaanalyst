-- 32
-- PKCColumnR
-- ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed product_no

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
	"product_no"	INT	 REFERENCES "products" ("product_no"),
	"order_id"	INT	PRIMARY KEY	 REFERENCES "orders" ("order_id"),
	"quantity"	INT	NOT NULL,
	CHECK ("quantity" > 0)
)

