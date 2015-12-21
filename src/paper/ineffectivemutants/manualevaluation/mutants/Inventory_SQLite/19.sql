-- 19
-- UCColumnE
-- ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged product with id

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY	UNIQUE,
	"product"	VARCHAR(50),
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

