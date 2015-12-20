-- 5
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added C

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR,
	"C"	CHAR	PRIMARY KEY,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR,
	"Z"	CHAR,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("X", "Y") REFERENCES "T" ("A", "B")
)

