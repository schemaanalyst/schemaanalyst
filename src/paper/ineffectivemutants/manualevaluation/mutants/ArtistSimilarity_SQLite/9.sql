-- 9
-- NNCA
-- Added NOT NULL to column target in table similarity

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id")	NOT NULL,
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

