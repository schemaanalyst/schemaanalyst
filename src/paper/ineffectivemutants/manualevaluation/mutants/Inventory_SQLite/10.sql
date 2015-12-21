-- 10
-- NNCA
-- Added NOT NULL to column quantity in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT	NOT NULL,
	"price"	DECIMAL(18, 2)
)

