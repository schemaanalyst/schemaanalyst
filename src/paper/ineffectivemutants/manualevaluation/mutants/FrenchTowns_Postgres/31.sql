-- 31
-- NNCR
-- Removed NOT NULL to column id in table Departments

CREATE TABLE "Regions" (
	"id"	INT	UNIQUE	NOT NULL,
	"code"	VARCHAR(4)	UNIQUE	NOT NULL,
	"capital"	VARCHAR(10)	NOT NULL,
	"name"	VARCHAR(100)	UNIQUE	NOT NULL
)

CREATE TABLE "Departments" (
	"id"	INT	UNIQUE,
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
	"department"	VARCHAR(4)	 REFERENCES "Departments" ("code")	NOT NULL,
	UNIQUE ("code", "department")
)

