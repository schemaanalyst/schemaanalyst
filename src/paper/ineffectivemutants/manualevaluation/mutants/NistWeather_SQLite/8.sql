-- 8
-- FKCColumnPairA
-- ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(MONTH, LAT_N)

CREATE TABLE "Station" (
	"ID"	INT	PRIMARY KEY,
	"CITY"	CHAR(20),
	"STATE"	CHAR(2),
	"LAT_N"	INT	NOT NULL,
	"LONG_W"	INT	NOT NULL,
	CHECK ("LAT_N" BETWEEN 0 AND 90),
	CHECK ("LONG_W" BETWEEN -180 AND 180)
)

CREATE TABLE "Stats" (
	"ID"	INT,
	"MONTH"	INT	NOT NULL,
	"TEMP_F"	INT	NOT NULL,
	"RAIN_I"	INT	NOT NULL,
	PRIMARY KEY ("ID", "MONTH"),
	FOREIGN KEY ("ID", "MONTH") REFERENCES "Station" ("ID", "LAT_N"),
	CHECK ("MONTH" BETWEEN 1 AND 12),
	CHECK ("TEMP_F" BETWEEN 80 AND 150),
	CHECK ("RAIN_I" BETWEEN 0 AND 100)
)

