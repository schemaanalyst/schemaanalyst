-- 10
-- UCColumnA
-- Added UNIQUE to column two_letter in table country

CREATE TABLE "country" (
	"name"	VARCHAR(100)	NOT NULL,
	"two_letter"	VARCHAR(100)	PRIMARY KEY	UNIQUE,
	"country_id"	INT	NOT NULL
)

