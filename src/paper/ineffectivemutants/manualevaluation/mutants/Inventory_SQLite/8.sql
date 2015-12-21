-- 8
-- NNCA
-- Added NOT NULL to column id in table Inventory

CREATE TABLE "Inventory" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"product"	VARCHAR(50)	UNIQUE,
	"quantity"	INT,
	"price"	DECIMAL(18, 2)
)

