-- 19
-- UCColumnE
-- ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged TNUM2 with TNUM3

CREATE TABLE "TEST12549" (
	"TNUM1"	NUMERIC(5, 0)	CONSTRAINT "CND12549A" NOT NULL,
	"TNUM2"	NUMERIC(5, 0),
	"TNUM3"	NUMERIC(5, 0)	CONSTRAINT "CND12549B" UNIQUE,
	CONSTRAINT "CND12549C" CHECK ("TNUM3" > 0)
)

