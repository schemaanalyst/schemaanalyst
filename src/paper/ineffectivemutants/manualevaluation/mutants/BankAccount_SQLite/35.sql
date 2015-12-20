-- 35
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with balance

CREATE TABLE "UserInfo" (
	"card_number"	INT	PRIMARY KEY,
	"pin_number"	INT	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"acct_lock"	INT
)

CREATE TABLE "Account" (
	"id"	INT,
	"account_name"	VARCHAR(50)	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"balance"	INT	PRIMARY KEY,
	"card_number"	INT	 REFERENCES "UserInfo" ("card_number")	NOT NULL
)

