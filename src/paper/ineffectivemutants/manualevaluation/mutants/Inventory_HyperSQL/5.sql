-- 5
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with quantity

CREATE TABLE "Inventory" (
	"id"	INT,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT	PRIMARY KEY,
	"price"	DECIMAL(18, 2)
)

