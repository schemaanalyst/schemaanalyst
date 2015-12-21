-- 6
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with price

CREATE TABLE "Inventory" (
	"id"	INT,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)	PRIMARY KEY
)

