-- 8
-- NNCA
-- Added NOT NULL to column product in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY,
	"product"	VARCHAR(50)	UNIQUE	NOT NULL,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

