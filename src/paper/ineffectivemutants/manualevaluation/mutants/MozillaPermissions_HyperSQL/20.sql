-- 20
-- NNCA
-- Added NOT NULL to column expireType in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT	NOT NULL,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

