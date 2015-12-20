-- 21
-- FKCColumnPairA
-- ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(lastName, capacity)

CREATE TABLE "Residence" (
	"name"	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	"capacity"	INT	NOT NULL,
	CHECK ("capacity" > 1),
	CHECK ("capacity" <= 10)
)

CREATE TABLE "Student" (
	"id"	INT	PRIMARY KEY,
	"firstName"	VARCHAR(50),
	"lastName"	VARCHAR(50),
	"residence"	VARCHAR(50),
	FOREIGN KEY ("residence", "lastName") REFERENCES "Residence" ("name", "capacity"),
	CHECK ("id" >= 0)
)

