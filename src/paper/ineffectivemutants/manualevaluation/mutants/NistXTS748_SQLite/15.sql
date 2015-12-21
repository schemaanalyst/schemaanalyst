-- 15
-- UCColumnA
-- ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added TNUM1

CREATE TABLE "TEST12549" (
	"TNUM1"	NUMERIC(5, 0)	CONSTRAINT "CND12549A" NOT NULL,
	"TNUM2"	NUMERIC(5, 0),
	"TNUM3"	NUMERIC(5, 0),
	CONSTRAINT "CND12549B" UNIQUE ("TNUM2", "TNUM1"),
	CONSTRAINT "CND12549C" CHECK ("TNUM3" > 0)
)

