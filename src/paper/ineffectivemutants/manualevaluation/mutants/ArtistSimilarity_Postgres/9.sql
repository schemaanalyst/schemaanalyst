-- 9
-- NNCA
-- Added NOT NULL to column similar in table similarity

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")	NOT NULL
)

