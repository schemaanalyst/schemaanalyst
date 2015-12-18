-- 11
-- UCColumnA
-- Added UNIQUE to column target in table similarity

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id")	UNIQUE,
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

