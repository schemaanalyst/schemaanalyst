-- 12
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with expireType

CREATE TABLE "moz_hosts" (
	"id"	INT,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT	CONSTRAINT "null" PRIMARY KEY,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

