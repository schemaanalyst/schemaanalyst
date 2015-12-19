-- 33
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged name with capacity

CREATE TABLE "Residence" (
	"name"	VARCHAR(50)	NOT NULL,
	"capacity"	INT	PRIMARY KEY	NOT NULL,
	CHECK ("capacity" > 1),
	CHECK ("capacity" <= 10)
)

CREATE TABLE "Student" (
	"id"	INT	PRIMARY KEY,
	"firstName"	VARCHAR(50),
	"lastName"	VARCHAR(50),
	"residence"	VARCHAR(50)	 REFERENCES "Residence" ("name"),
	CHECK ("id" >= 0)
)

