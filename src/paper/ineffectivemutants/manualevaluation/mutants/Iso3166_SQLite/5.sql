-- 5
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged two_letter with country_id

CREATE TABLE "country" (
	"name"	VARCHAR(100)	NOT NULL,
	"two_letter"	VARCHAR(100),
	"country_id"	INT	PRIMARY KEY	NOT NULL
)

