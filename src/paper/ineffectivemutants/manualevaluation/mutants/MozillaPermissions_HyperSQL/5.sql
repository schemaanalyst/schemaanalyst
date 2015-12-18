-- 5
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added expireTime

CREATE TABLE "moz_hosts" (
	"id"	INT,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT,
	CONSTRAINT "null" PRIMARY KEY ("id", "expireTime")
)

