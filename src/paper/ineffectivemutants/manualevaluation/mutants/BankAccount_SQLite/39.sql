-- 39
-- NNCA
-- Added NOT NULL to column id in table Account

CREATE TABLE "UserInfo" (
	"card_number"	INT	PRIMARY KEY,
	"pin_number"	INT	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"acct_lock"	INT
)

CREATE TABLE "Account" (
	"id"	INT	PRIMARY KEY	NOT NULL,
	"account_name"	VARCHAR(50)	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"balance"	INT,
	"card_number"	INT	 REFERENCES "UserInfo" ("card_number")	NOT NULL
)

