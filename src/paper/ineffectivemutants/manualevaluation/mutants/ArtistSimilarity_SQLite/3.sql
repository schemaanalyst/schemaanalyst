-- 3
-- FKCColumnPairE
-- ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(target, artist_id) with Pair(similar, artist_id)

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT,
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

