-- 21
-- UCColumnA
-- Added UNIQUE to column timestamp in table metadata

CREATE TABLE "metadata" (
	"timestamp"	VARCHAR(50)	UNIQUE,
	"Project"	VARCHAR(50),
	"elapsed_s"	INT
)

CREATE TABLE "t" (
	"Project"	VARCHAR(50),
	"Language"	VARCHAR(50),
	"File"	VARCHAR(50),
	"nBlank"	INT,
	"nComment"	INT,
	"nCode"	INT,
	"nScaled"	INT
)

