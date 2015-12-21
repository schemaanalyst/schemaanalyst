-- 11
-- UCColumnA
-- Added UNIQUE to column quantity in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT	UNIQUE,
	"price"	DECIMAL(18, 2)
)

