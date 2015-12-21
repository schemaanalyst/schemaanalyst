-- 2
-- CCInExpressionRHSListExpressionElementR
-- ListElementRemover with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with InExpressionRHSListExpressionSupplier and InExpressionRHSListExpressionSubexpressionsSupplier - Removed 'Male'

CREATE TABLE "person" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(45)	NOT NULL,
	"first_name"	VARCHAR(45)	NOT NULL,
	"gender"	VARCHAR(6)	NOT NULL,
	"date_of_birth"	DATE	NOT NULL,
	CHECK ("gender" IN ('Female', 'Uknown'))
)

