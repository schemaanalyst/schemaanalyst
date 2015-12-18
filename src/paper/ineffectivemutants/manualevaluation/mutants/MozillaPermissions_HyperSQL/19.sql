-- 19
-- NNCA
-- Added NOT NULL to column permission in table moz_hosts

CREATE TABLE "moz_hosts" (
	"id"	INT	CONSTRAINT "null" PRIMARY KEY,
	"host"	LONGVARCHAR,
	"type"	LONGVARCHAR,
	"permission"	INT	NOT NULL,
	"expireType"	INT,
	"expireTime"	INT,
	"appId"	INT,
	"isInBrowserElement"	INT
)

