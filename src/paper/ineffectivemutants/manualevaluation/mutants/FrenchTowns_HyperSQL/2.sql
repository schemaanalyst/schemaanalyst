-- 2
-- FKCColumnPairR
-- ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(department, code)

CREATE TABLE "Regions" (
	"id"	INT	UNIQUE	NOT NULL,
	"code"	VARCHAR(4)	UNIQUE	NOT NULL,
	"capital"	VARCHAR(10)	NOT NULL,
	"name"	VARCHAR(100)	UNIQUE	NOT NULL
)

CREATE TABLE "Departments" (
	"id"	INT	UNIQUE	NOT NULL,
	"code"	VARCHAR(4)	UNIQUE	NOT NULL,
	"capital"	VARCHAR(10)	UNIQUE	NOT NULL,
	"region"	VARCHAR(4)	 REFERENCES "Regions" ("code")	NOT NULL,
	"name"	VARCHAR(100)	UNIQUE	NOT NULL
)

CREATE TABLE "Towns" (
	"id"	INT	UNIQUE	NOT NULL,
	"code"	VARCHAR(10)	NOT NULL,
	"article"	VARCHAR(100),
	"name"	VARCHAR(100)	NOT NULL,
	"department"	VARCHAR(4)	NOT NULL,
	UNIQUE ("code", "department")
)

