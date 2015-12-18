-- 9
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with host

CREATE TABLE "moz_hosts" (
	"id"	INT,
	"host"	LONGVARCHAR	CONSTRAINT "null" PRIMARY KEY,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

