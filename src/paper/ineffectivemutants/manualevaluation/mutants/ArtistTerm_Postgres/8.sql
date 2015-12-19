-- 8
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(term, term) with Pair(artist_id, term)

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
	"artist_id"	TEXT	 REFERENCES "artists" ("artist_id")	 REFERENCES "terms" ("term"),
	"term"	TEXT
)

