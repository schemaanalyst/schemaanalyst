-- 4
-- PKCColumnR
-- ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed id

CREATE TABLE "Inventory" (
	"id"	INT,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

