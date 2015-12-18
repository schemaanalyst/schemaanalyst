-- 27
-- UCColumnA
-- Added UNIQUE to column expireType in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT	UNIQUE,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

