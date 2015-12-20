-- 1
-- CCNullifier
-- ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set id >= 0 to null

CREATE TABLE "Employee" (
	"id"	INT	PRIMARY KEY,
	"first"	VARCHAR(15),
	"last"	VARCHAR(20),
	"age"	INT,
	"address"	VARCHAR(30),
	"city"	VARCHAR(20),
	"state"	VARCHAR(20),
	CHECK ("age" > 0),
	CHECK ("age" <= 150)
)

