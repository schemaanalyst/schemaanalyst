/***************************************
 * Constraint coverage for BankAccount *
 ***************************************/
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS UserInfo;
CREATE TABLE UserInfo (
	card_number	INT	PRIMARY KEY,
	pin_number	INT	NOT NULL,
	user_name	VARCHAR(50)	NOT NULL,
	acct_lock	INT
);
CREATE TABLE Account (
	id	INT	PRIMARY KEY,
	account_name	VARCHAR(50)	NOT NULL,
	user_name	VARCHAR(50)	NOT NULL,
	balance	INT,
	card_number	INT	 REFERENCES UserInfo (card_number)	NOT NULL
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 43ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 13ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(0, 0, '', 0);
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-52, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(0, '', '', 0, 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(10, '', '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[card_number]" on table "UserInfo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(0, 0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(pin_number)" on table "UserInfo"
-- * Success: true
-- * Time: 1ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-34, NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(user_name)" on table "UserInfo"
-- * Success: true
-- * Time: 2ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(96, 0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Account"
-- * Success: true
-- * Time: 5ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(88, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(0, '', '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[card_number]" on table "Account"
-- * Success: true
-- * Time: 5ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-75, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(-96, '', '', 0, 92);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(account_name)" on table "Account"
-- * Success: true
-- * Time: 5ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(25, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(55, NULL, '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(user_name)" on table "Account"
-- * Success: true
-- * Time: 6ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-3, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(46, '', NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(card_number)" on table "Account"
-- * Success: true
-- * Time: 6ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(67, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(79, '', '', 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

