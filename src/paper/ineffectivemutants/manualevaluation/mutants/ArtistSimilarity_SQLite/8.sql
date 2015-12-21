-- 8
-- NNCA
-- Added NOT NULL to column artist_id in table artists

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY	NOT NULL
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

