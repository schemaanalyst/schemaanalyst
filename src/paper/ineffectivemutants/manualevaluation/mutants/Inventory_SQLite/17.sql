-- 17
-- UCColumnA
-- ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added price

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50),
	"quantity"	INT,
	"price"	DECIMAL(18, 2),
	UNIQUE ("product", "price")
)

