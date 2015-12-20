-- 11
-- UCColumnA
-- Added UNIQUE to column country_id in table country

CREATE TABLE "country" (
	"name"	VARCHAR(100)	NOT NULL,
	"two_letter"	VARCHAR(100)	PRIMARY KEY,
	"country_id"	INT	UNIQUE	NOT NULL
)

