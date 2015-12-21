-- 16
-- UCColumnR
-- ListElementRemover with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnSupplier - Removed product

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50),
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

