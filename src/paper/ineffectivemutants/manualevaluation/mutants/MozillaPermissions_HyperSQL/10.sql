-- 10
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with type

CREATE TABLE "moz_hosts" (
	"id"	INT,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR	CONSTRAINT "null" PRIMARY KEY,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

