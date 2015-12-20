-- 10
-- NNCA
-- Added NOT NULL to column B in table T

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR	NOT NULL,
	"C"	CHAR,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR,
	"Z"	CHAR,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("X", "Y") REFERENCES "T" ("A", "B")
)

