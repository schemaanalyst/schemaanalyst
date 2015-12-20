-- 44
-- UCColumnA
-- Added UNIQUE to column capacity in table Residence

CREATE TABLE "Residence" (
	"name"	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	"capacity"	INT	UNIQUE	NOT NULL,
	CHECK ("capacity" > 1),
	CHECK ("capacity" <= 10)
)

CREATE TABLE "Student" (
	"id"	INT	PRIMARY KEY,
	"firstName"	VARCHAR(50),
	"lastName"	VARCHAR(50),
	"residence"	VARCHAR(50)	 REFERENCES "Residence" ("name"),
	CHECK ("id" >= 0)
)

