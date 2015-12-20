-- 7
-- PKCColumnR
-- ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed artist_id

CREATE TABLE "artists" (
	"artist_id"	TEXT
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

