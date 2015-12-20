-- 20
-- UCColumnA
-- Added UNIQUE to column Z in table S

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR,
	"C"	CHAR,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR,
	"Z"	CHAR	UNIQUE,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("X", "Y") REFERENCES "T" ("A", "B")
)

