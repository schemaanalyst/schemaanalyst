-- 24
-- UCColumnA
-- Added UNIQUE to column host in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR	UNIQUE,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

