-- 26
-- UCColumnA
-- Added UNIQUE to column File in table t

CREATE TABLE "metadata" (
	"timestamp"	VARCHAR(50),
	"Project"	VARCHAR(50),
	"elapsed_s"	INT
)

CREATE TABLE "t" (
	"Project"	VARCHAR(50),
	"Language"	VARCHAR(50),
	"File"	VARCHAR(50)	UNIQUE,
	"nBlank"	INT,
	"nComment"	INT,
	"nCode"	INT,
	"nScaled"	INT
)

