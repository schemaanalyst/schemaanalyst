-- 2
-- FKCColumnPairR
-- ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(mbtag, mbtag)

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
	"mbtag"	TEXT
)

CREATE TABLE "artist_term" (
	"artist_id"	TEXT	 REFERENCES "artists" ("artist_id"),
	"term"	TEXT	 REFERENCES "terms" ("term")
)

