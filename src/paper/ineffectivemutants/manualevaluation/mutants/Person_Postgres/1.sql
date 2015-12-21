-- 1
-- CCNullifier
-- ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set gender IN ('Male', 'Female', 'Uknown') to null

CREATE TABLE "person" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(45)	NOT NULL,
	"first_name"	VARCHAR(45)	NOT NULL,
	"gender"	VARCHAR(6)	NOT NULL,
	"date_of_birth"	DATE	NOT NULL
)

