-- 29
-- UCColumnA
-- Added UNIQUE to column appId in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT	UNIQUE,
	"isInBrowserElement"	INT
)

