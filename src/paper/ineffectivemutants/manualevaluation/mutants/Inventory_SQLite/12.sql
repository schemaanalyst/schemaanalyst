-- 12
-- UCColumnA
-- Added UNIQUE to column id in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY	UNIQUE,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

