-- 27
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added capacity

CREATE TABLE "Residence" (
	"name"	VARCHAR(50)	NOT NULL,
	"capacity"	INT	NOT NULL,
	PRIMARY KEY ("name", "capacity"),
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

