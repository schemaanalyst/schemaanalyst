-- 5
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(artist_id, artist_id) with Pair(mbtag, artist_id)

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
	"artist_id"	TEXT,
	"mbtag"	TEXT	 REFERENCES "mbtags" ("mbtag")	 REFERENCES "artists" ("artist_id")
)

CREATE TABLE "artist_term" (
	"artist_id"	TEXT	 REFERENCES "artists" ("artist_id"),
	"term"	TEXT	 REFERENCES "terms" ("term")
)

