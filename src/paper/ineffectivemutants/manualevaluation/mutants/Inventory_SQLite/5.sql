-- 5
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with product

CREATE TABLE "Inventory" (
	"id"	INT,
	"product"	VARCHAR(50)	PRIMARY KEY	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

