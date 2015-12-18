-- 10
-- UCColumnA
-- Added UNIQUE to column artist_id in table artists

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY	UNIQUE
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

