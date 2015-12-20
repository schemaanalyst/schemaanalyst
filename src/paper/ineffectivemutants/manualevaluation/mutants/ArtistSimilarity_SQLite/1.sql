-- 1
-- FKCColumnPairR
-- ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(target, artist_id)

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT,
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

