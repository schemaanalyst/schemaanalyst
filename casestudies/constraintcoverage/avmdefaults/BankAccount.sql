/***************************************
 * Constraint coverage for BankAccount *
 ***************************************/
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS UserInfo;
CREATE TABLE UserInfo (
	card_number	INT	CONSTRAINT null PRIMARY KEY,
	pin_number	INT	CONSTRAINT null NOT NULL,
	user_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	acct_lock	INT
);
CREATE TABLE Account (
	id	INT	CONSTRAINT null PRIMARY KEY,
	account_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	user_name	VARCHAR(50)	CONSTRAINT null NOT NULL,
	balance	INT,
	card_number	INT	CONSTRAINT null  REFERENCES UserInfo (card_number)	CONSTRAINT null NOT NULL
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 401ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 72ms 
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{card_number}" on table "UserInfo"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "Account"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{card_number}" on table "Account"
-- * Success: true
-- * Time: 66ms 
-- * Number of objective function evaluations: 47
-- * Number of restarts: 1

-- Negating "NOT NULL(pin_number)" on table "UserInfo"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 1

-- Negating "NOT NULL(user_name)" on table "UserInfo"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 1

-- Negating "NOT NULL(account_name)" on table "Account"
-- * Success: true
-- * Time: 127ms 
-- * Number of objective function evaluations: 101
-- * Number of restarts: 1

-- Negating "NOT NULL(user_name)" on table "Account"
-- * Success: true
-- * Time: 93ms 
-- * Number of objective function evaluations: 80
-- * Number of restarts: 1

-- Negating "NOT NULL(card_number)" on table "Account"
-- * Success: true
-- * Time: 19ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

