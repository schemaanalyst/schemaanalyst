-- 19
-- UCColumnA
-- Added UNIQUE to column Y in table S

CREATE TABLE "T" (
	"A"	CHAR,
	"B"	CHAR,
	"C"	CHAR,
	CONSTRAINT "UniqueOnColsAandB" UNIQUE ("A", "B")
)

CREATE TABLE "S" (
	"X"	CHAR,
	"Y"	CHAR	UNIQUE,
	"Z"	CHAR,
	CONSTRAINT "RefToColsAandB" FOREIGN KEY ("X", "Y") REFERENCES "T" ("A", "B")
)

