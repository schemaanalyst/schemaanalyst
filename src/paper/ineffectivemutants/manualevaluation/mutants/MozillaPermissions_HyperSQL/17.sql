-- 17
-- NNCA
-- Added NOT NULL to column host in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR	NOT NULL,
	"type"	LONGVARCHAR,
	"permission"	INT,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

