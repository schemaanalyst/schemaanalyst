-- 3
-- FKCColumnPairR
-- ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(artist_id, artist_id)

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
	"artist_id"	TEXT,
	"term"	TEXT	 REFERENCES "terms" ("term")
)

