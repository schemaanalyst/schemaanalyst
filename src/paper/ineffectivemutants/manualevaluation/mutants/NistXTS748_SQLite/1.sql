-- 1
-- CCNullifier
-- ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set TNUM3 > 0 to null

CREATE TABLE "TEST12549" (
	"TNUM1"	NUMERIC(5, 0)	CONSTRAINT "CND12549A" NOT NULL,
	"TNUM2"	NUMERIC(5, 0)	CONSTRAINT "CND12549B" UNIQUE,
	"TNUM3"	NUMERIC(5, 0)
)

