-- 5
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added target

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT	PRIMARY KEY	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	 REFERENCES "artists" ("artist_id")
)

