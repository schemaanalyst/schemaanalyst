-- 21
-- NNCA
-- Added NOT NULL to column expireTime in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT	NOT NULL,
	"appId"	INT,
	"isInBrowserElement"	INT
)

