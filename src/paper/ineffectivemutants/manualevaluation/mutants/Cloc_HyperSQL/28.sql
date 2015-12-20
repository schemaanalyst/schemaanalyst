-- 28
-- UCColumnA
-- Added UNIQUE to column nComment in table t

CREATE TABLE "metadata" (
	"timestamp"	VARCHAR(50),
	"Project"	VARCHAR(50),
	"elapsed_s"	INT
)

CREATE TABLE "t" (
	"Project"	VARCHAR(50),
	"Language"	VARCHAR(50),
	"File"	VARCHAR(50),
	"nBlank"	INT,
	"nComment"	INT	UNIQUE,
	"nCode"	INT,
	"nScaled"	INT
)

