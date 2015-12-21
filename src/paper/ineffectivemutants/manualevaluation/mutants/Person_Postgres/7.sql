-- 7
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added gender

CREATE TABLE "person" (
	"id"	INT	NOT NULL,
	"last_name"	VARCHAR(45)	NOT NULL,
	"first_name"	VARCHAR(45)	NOT NULL,
	"gender"	VARCHAR(6)	NOT NULL,
	"date_of_birth"	DATE	NOT NULL,
	PRIMARY KEY ("id", "gender"),
	CHECK ("gender" IN ('Male', 'Female', 'Uknown'))
)

