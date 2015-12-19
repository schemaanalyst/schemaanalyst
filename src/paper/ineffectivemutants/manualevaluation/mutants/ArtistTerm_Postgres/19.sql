-- 19
-- NNCA
-- Added NOT NULL to column term in table artist_term

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "mbtags" (
	"mbtag"	TEXT	PRIMARY KEY
)

CREATE TABLE "terms" (
	"term"	TEXT	PRIMARY KEY
)

CREATE TABLE "artist_mbtag" (
	"artist_id"	TEXT	 REFERENCES "artists" ("artist_id"),
	"mbtag"	TEXT	 REFERENCES "mbtags" ("mbtag")
)

CREATE TABLE "artist_term" (
	"artist_id"	TEXT	 REFERENCES "artists" ("artist_id"),
	"term"	TEXT	 REFERENCES "terms" ("term")	NOT NULL
)

