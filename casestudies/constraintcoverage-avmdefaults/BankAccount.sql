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
-- Time to generate: 496ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 38ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(1, 0, '', 0);
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(0, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(1, '', '', 0, 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(0, '', '', 0, 0);
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[card_number]" on table "UserInfo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(0, 0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(pin_number)" on table "UserInfo"
-- * Success: true
-- * Time: 3ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, NULL, '', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(user_name)" on table "UserInfo"
-- * Success: true
-- * Time: 4ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, NULL, 0);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Account"
-- * Success: true
-- * Time: 4ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(0, '', '', 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[card_number]" on table "Account"
-- * Success: true
-- * Time: 45ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(46, -52, 'hctgp', 96);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(-96, '', 'hjr', -100, -99);
-- * Number of objective function evaluations: 47
-- * Number of restarts: 1

-- Negating "NOT NULL(account_name)" on table "Account"
-- * Success: true
-- * Time: 136ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(12, -50, 'kt', 98);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(-3, NULL, 'srdlabii', 71, 46);
-- * Number of objective function evaluations: 98
-- * Number of restarts: 1

-- Negating "NOT NULL(user_name)" on table "Account"
-- * Success: true
-- * Time: 141ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(73, 22, 'hfckgxrbj', 53);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(23, 'vty', NULL, -36, 46);
-- * Number of objective function evaluations: 99
-- * Number of restarts: 1

-- Negating "NOT NULL(card_number)" on table "Account"
-- * Success: true
-- * Time: 125ms 
INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-6, 42, 'yi', -91);
INSERT INTO Account(id, account_name, user_name, balance, card_number) VALUES(-86, 'camirp', 'hvbai', -83, NULL);
-- * Number of objective function evaluations: 100
-- * Number of restarts: 1
