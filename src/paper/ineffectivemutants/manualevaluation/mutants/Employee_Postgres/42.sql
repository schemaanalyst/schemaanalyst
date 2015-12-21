-- 42
-- UCColumnA
-- Added UNIQUE to column age in table Employee

CREATE TABLE "Employee" (
	"id"	INT	PRIMARY KEY,
	"first"	VARCHAR(15),
	"last"	VARCHAR(20),
	"age"	INT	UNIQUE,
	"address"	VARCHAR(30),
	"city"	VARCHAR(20),
	"state"	VARCHAR(20),
	CHECK ("id" >= 0),
	CHECK ("age" > 0),
	CHECK ("age" <= 150)
)

