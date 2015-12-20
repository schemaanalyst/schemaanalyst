-- 11
-- NNCA
-- Added NOT NULL to column C in table T

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR,
	"C"	CHAR	NOT NULL,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR,
	"Z"	CHAR,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("X", "Y") REFERENCES "T" ("A", "B")
)

