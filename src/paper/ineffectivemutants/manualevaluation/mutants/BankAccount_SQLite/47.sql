-- 47
-- UCColumnA
-- Added UNIQUE to column pin_number in table UserInfo

CREATE TABLE "UserInfo" (
	"card_number"	INT	PRIMARY KEY,
	"pin_number"	INT	UNIQUE	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"acct_lock"	INT
)

CREATE TABLE "Account" (
	"id"	INT	PRIMARY KEY,
	"account_name"	VARCHAR(50)	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"balance"	INT,
	"card_number"	INT	 REFERENCES "UserInfo" ("card_number")	NOT NULL
)

