-- 14
-- CCRelationalExpressionOperatorE
-- RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - <= replaced with >

CREATE TABLE "Exam" (
	"ekey"	INT	PRIMARY KEY,
	"fn"	VARCHAR(15),
	"ln"	VARCHAR(30),
	"exam"	INT,
	"score"	INT,
	"timeEnter"	DATE,
	CHECK ("score" >= 0),
	CHECK ("score" > 100)
)

CREATE TABLE "Examlog" (
	"lkey"	INT	PRIMARY KEY,
	"ekey"	INT	 REFERENCES "Exam" ("ekey"),
	"ekeyOLD"	INT,
	"fnNEW"	VARCHAR(15),
	"fnOLD"	VARCHAR(15),
	"lnNEW"	VARCHAR(30),
	"lnOLD"	VARCHAR(30),
	"examNEW"	INT,
	"examOLD"	INT,
	"scoreNEW"	INT,
	"scoreOLD"	INT,
	"sqlAction"	VARCHAR(15),
	"examtimeEnter"	DATE,
	"examtimeUpdate"	DATE,
	"timeEnter"	DATE,
	CHECK ("scoreNEW" >= 0),
	CHECK ("scoreNEW" <= 100),
	CHECK ("scoreOLD" >= 0),
	CHECK ("scoreOLD" <= 100)
)

