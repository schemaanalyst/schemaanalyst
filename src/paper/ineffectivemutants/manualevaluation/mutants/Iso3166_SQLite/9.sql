-- 9
-- UCColumnA
-- Added UNIQUE to column name in table country

CREATE TABLE "country" (
	"name"	VARCHAR(100)	UNIQUE	NOT NULL,
	"two_letter"	VARCHAR(100)	PRIMARY KEY,
	"country_id"	INT	NOT NULL
)

