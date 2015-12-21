-- 7
-- NNCR
-- Removed NOT NULL to column name in table country

CREATE TABLE "country" (
	"name"	VARCHAR(100),
	"two_letter"	VARCHAR(100)	PRIMARY KEY,
	"country_id"	INT	NOT NULL
)

