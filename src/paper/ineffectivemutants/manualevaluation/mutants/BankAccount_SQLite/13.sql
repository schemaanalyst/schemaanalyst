-- 13
-- FKCColumnPairR
-- ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(card_number, card_number)

CREATE TABLE "UserInfo" (
	"card_number"	INT	PRIMARY KEY,
	"pin_number"	INT	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"acct_lock"	INT
)

CREATE TABLE "Account" (
	"id"	INT	PRIMARY KEY,
	"account_name"	VARCHAR(50)	NOT NULL,
	"user_name"	VARCHAR(50)	NOT NULL,
	"balance"	INT,
	"card_number"	INT	NOT NULL
)

