-- 6
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added similar

CREATE TABLE "artists" (
	"artist_id"	TEXT	PRIMARY KEY
)

CREATE TABLE "similarity" (
	"target"	TEXT	 REFERENCES "artists" ("artist_id"),
	"similar"	TEXT	PRIMARY KEY	 REFERENCES "artists" ("artist_id")
)

