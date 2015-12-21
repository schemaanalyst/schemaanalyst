-- 2
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added country_id

CREATE TABLE "country" (
	"name"	VARCHAR(100)	NOT NULL,
	"two_letter"	VARCHAR(100),
	"country_id"	INT	NOT NULL,
	PRIMARY KEY ("two_letter", "country_id")
)

