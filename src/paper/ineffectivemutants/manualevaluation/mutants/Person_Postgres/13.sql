-- 13
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with date_of_birth

CREATE TABLE "person" (
	"id"	INT	NOT NULL,
	"last_name"	VARCHAR(45)	NOT NULL,
	"first_name"	VARCHAR(45)	NOT NULL,
	"gender"	VARCHAR(6)	NOT NULL,
	"date_of_birth"	DATE	PRIMARY KEY	NOT NULL,
	CHECK ("gender" IN ('Male', 'Female', 'Uknown'))
)

