-- 3
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added permission

CREATE TABLE "moz_hosts" (
	"id"	INT,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT,
	CONSTRAINT "null" PRIMARY KEY ("id", "permission")
)

