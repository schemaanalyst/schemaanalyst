-- 1
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(X, A) with Pair(Z, A)

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR,
	"C"	CHAR,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR,
	"Z"	CHAR,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("Y", "Z") REFERENCES "T" ("B", "A")
)

