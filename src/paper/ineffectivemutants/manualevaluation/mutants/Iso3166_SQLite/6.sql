-- 6
-- NNCA
-- Added NOT NULL to column two_letter in table country

CREATE TABLE "country" (
	"name"	VARCHAR(100)	NOT NULL,
	"two_letter"	VARCHAR(100)	PRIMARY KEY	NOT NULL,
	"country_id"	INT	NOT NULL
)

