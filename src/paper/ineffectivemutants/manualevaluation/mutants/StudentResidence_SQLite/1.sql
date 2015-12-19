-- 1
-- CCNullifier
-- ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set capacity > 1 to null

CREATE TABLE "Residence" (
	"name"	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	"capacity"	INT	NOT NULL,
	CHECK ("capacity" <= 10)
)

CREATE TABLE "Student" (
	"id"	INT	PRIMARY KEY,
	"firstName"	VARCHAR(50),
	"lastName"	VARCHAR(50),
	"residence"	VARCHAR(50)	 REFERENCES "Residence" ("name"),
	CHECK ("id" >= 0)
)

