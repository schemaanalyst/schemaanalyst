-- 3
-- CCRelationalExpressionOperatorE
-- RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with !=

CREATE TABLE "TEST12549" (
	"TNUM1"	NUMERIC(5, 0)	CONSTRAINT "CND12549A" NOT NULL,
	"TNUM2"	NUMERIC(5, 0)	CONSTRAINT "CND12549B" UNIQUE,
	"TNUM3"	NUMERIC(5, 0),
	CONSTRAINT "CND12549C" CHECK ("TNUM3" != 0)
)

