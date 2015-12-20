-- 30
-- PKCColumnE
-- ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged card_number with pin_number

CREATE TABLE "UserInfo" (
	"card_number"	INT,
	"pin_number"	INT	PRIMARY KEY	NOT NULL,
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

