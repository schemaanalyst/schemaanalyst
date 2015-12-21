-- 3
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added price

CREATE TABLE "Inventory" (
	"id"	INT,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2),
	PRIMARY KEY ("id", "price")
)

