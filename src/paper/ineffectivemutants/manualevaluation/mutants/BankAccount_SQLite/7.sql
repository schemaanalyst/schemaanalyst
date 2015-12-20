-- 7
-- FKCColumnPairA
-- ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(user_name, pin_number)

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
	"card_number"	INT	NOT NULL,
	FOREIGN KEY ("card_number", "user_name") REFERENCES "UserInfo" ("card_number", "pin_number")
)

