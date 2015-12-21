-- 9
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added TNUM3

CREATE TABLE "TEST12549" (
	"TNUM1"	NUMERIC(5, 0)	CONSTRAINT "CND12549A" NOT NULL,
	"TNUM2"	NUMERIC(5, 0)	CONSTRAINT "CND12549B" UNIQUE,
	"TNUM3"	NUMERIC(5, 0)	PRIMARY KEY,
	CONSTRAINT "CND12549C" CHECK ("TNUM3" > 0)
)

