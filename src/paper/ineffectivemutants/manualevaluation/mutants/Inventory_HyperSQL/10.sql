-- 10
-- NNCA
-- Added NOT NULL to column price in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)	NOT NULL
)

