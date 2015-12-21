-- 17
-- NNCR
-- Removed NOT NULL to column gender in table person

CREATE TABLE "person" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"last_name"	VARCHAR(45)	NOT NULL,
	"first_name"	VARCHAR(45)	NOT NULL,
	"gender"	VARCHAR(6),
	"date_of_birth"	DATE	NOT NULL,
	CHECK ("gender" IN ('Male', 'Female', 'Uknown'))
)

